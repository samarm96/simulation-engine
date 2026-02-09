package com.sime.systems;

import java.util.List;
import java.util.Random;

import com.sime.commands.AddEnergyCommand;
import com.sime.commands.CommandBuffer;
import com.sime.commands.ConsumeNutrientCommand;
import com.sime.commands.MoveCommand;
import com.sime.components.Component;
import com.sime.components.Energy;
import com.sime.components.LocomotionTraits;
import com.sime.components.MetabolicTraits;
import com.sime.components.Position;
import com.sime.models.DefaultBioModel;
import com.sime.world.World;

public class SoftMaxForagingSystem extends SimSystem {

    private static final double beta = 0.1;
    private final Random rng;

    public SoftMaxForagingSystem(Random rng) {
        this.rng = rng;
    }

    @Override
    public void apply(World w, CommandBuffer queue, double dt) {
        // check food at current space, adn food in adjacent spaces
        // attempt to move to next space
        var nutrientField = w.getFieldStore().scalar2D("nutrients");
        var model = (DefaultBioModel) w.getSimulationModel();

        var ids = w.getComponentRegistry().entitiesWith(requiredComponents());

        for (var id : ids) {
            var position = w.getComponentRegistry().get(id, Position.class);
            var energy = w.getComponentRegistry().get(id, Energy.class);
            var locomotionTraits = w.getComponentRegistry().get(id, LocomotionTraits.class);
            var metabolicTraits = w.getComponentRegistry().get(id, MetabolicTraits.class);

            if (energy.amount() >= metabolicTraits.maximumEnergy() - 1e-9) {
                continue; // no need to eat
            }

            var x = position.x();
            var y = position.y();
            int[] dx = {0, 1, -1, 0, 0};
            int[] dy = {0, 0, 0, -1, 1};

            double[] N = new double[5];
            double[] movementCost = new double[5];
            for (int i = 0; i < 5; i++) {
                N[i] = nutrientField.sample(x + dx[i], y + dy[i]);
                movementCost[i] =  Math.sqrt((x + dx[i] - position.x())^2 + (y + dy[i] - position.y())^2) / locomotionTraits.movementEfficiency();
            }

            int choice = sampleSoftMax(N, movementCost);
            int nx = clamp(x + dx[choice], 0, nutrientField.columns() - 1);
            int ny = clamp(y + dy[choice], 0, nutrientField.rows() - 1);

            double need = metabolicTraits.maximumEnergy() - energy.amount();
            var distanceTraveled = Math.sqrt((nx - position.x())^2 + (ny - position.y())^2);
            var travelCost = distanceTraveled / locomotionTraits.movementEfficiency();

            if (need > 0) {
                double available = nutrientField.get(nx, ny);
                double intake = Math.min(metabolicTraits.consumptionRate(), available);

                // don’t eat more than needed (after efficiency)
                double intakeNeeded = need / metabolicTraits.intakeEfficiency();
                intake = Math.min(intake, intakeNeeded);

                if (intake > 0) {
                    // commands:
                    queue.add(new MoveCommand(id, nx, ny));
                    queue.add(new AddEnergyCommand(id, -travelCost));
                    queue.add(new AddEnergyCommand(id, model.energyFromIntake(metabolicTraits, intake)));
                    queue.add(new ConsumeNutrientCommand(id, intake));
                }
            }
        }
    }

    @Override
    protected List<Class<? extends Component>> requiredComponents() {
        return List.of(
            LocomotionTraits.class, 
            MetabolicTraits.class, 
            Energy.class, 
            Position.class);
    }
 
    private int sampleSoftMax(double[] nutrients, double[] movementCost) {

        var max = nutrients[0];
        for(var nutrient : nutrients) {
            max = Math.max(nutrient, max);
        }

        var sum = 0.0;
        var weight = new double[nutrients.length];
        for(int i = 0; i < nutrients.length; i++) {
        // exp ( beta * Ni)
            weight[i] = Math.exp(beta * (nutrients[i] - max) - rng.nextGaussian() * movementCost[i]);
            sum += weight[i];
        }

        double r = rng.nextDouble() * sum;
        double acc = 0.0;
        for (int i = 0; i < weight.length; i++) {
            acc += weight[i];
            if (r <= acc) {
                return i;
            }
        }

        return weight.length - 1;   
    }

    private int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(hi, v));
    }

}
