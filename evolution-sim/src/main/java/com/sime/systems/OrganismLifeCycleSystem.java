package com.sime.systems;

import java.util.List;
import java.util.Random;

import com.sime.commands.CommandBuffer;
import com.sime.commands.CreateEntityCommand;
import com.sime.commands.DeleteEntityCommand;
import com.sime.components.Age;
import com.sime.components.Component;
import com.sime.components.Energy;
import com.sime.components.LocomotionTraits;
import com.sime.components.MetabolicTraits;
import com.sime.components.Position;
import com.sime.components.ReproductiveTraits;
import com.sime.components.SurvivalTraits;
import com.sime.models.DefaultBioModel;
import com.sime.world.World;

public class OrganismLifeCycleSystem extends SimSystem {

    private final Random rng;

    public OrganismLifeCycleSystem(Random rng) {
        this.rng = rng;
    }

    @Override
    public void apply(World w, CommandBuffer queue, double dt) {        
        var ids = w.getComponentRegistry().entitiesWith(requiredComponents());
        var model = (DefaultBioModel) w.getSimulationModel();

        for (var id : ids) {
            var reproductiveTraits = w.getComponentRegistry().get(id, ReproductiveTraits.class);
            var locomotionTraits = w.getComponentRegistry().get(id, LocomotionTraits.class);
            var metabolicTraits = w.getComponentRegistry().get(id, MetabolicTraits.class);
            var survivalTraits = w.getComponentRegistry().get(id, SurvivalTraits.class);

            var age = w.getComponentRegistry().get(id, Age.class);
            var energy = w.getComponentRegistry().get(id, Energy.class);
            var position = w.getComponentRegistry().get(id, Position.class);
        
            // add age
            age.addAge();

            // add metabolic burn
            var metabolicBurn = model.metabolicBurn(metabolicTraits, age, dt);
            energy.addEnergy(-metabolicBurn);

            if(energy.amount() <= 1e-6) {
                queue.add(new DeleteEntityCommand(id));
                continue; 
            }

            // Check if the organism died
            var deathProbability = model.deathProbability(survivalTraits, age, energy, metabolicTraits, dt);
            if(deathProbability > rng.nextDouble()) {
                queue.add(new DeleteEntityCommand(id));
                continue;
            }

            // Check if the organism reproduced
            var canReproduce = model.canReproduce(reproductiveTraits, energy, metabolicTraits);
            if(!canReproduce) {
                continue;
            }

            if(reproductiveTraits.replicationChance() > rng.nextDouble()) {             
                var childTraits = createChild(locomotionTraits, reproductiveTraits, metabolicTraits, survivalTraits, energy, position);

                var reproductionEnergyCost = model.reproductionEnergyCost(reproductiveTraits, metabolicTraits);
                energy.addEnergy(-reproductionEnergyCost);
                queue.add(new CreateEntityCommand(childTraits));
            }
        }
    }

    private List<Component> createChild(
        LocomotionTraits locomotionTraits, 
        ReproductiveTraits reproductiveTraits,
        MetabolicTraits metabolicTraits,
        SurvivalTraits survivalTraits,
        Energy energy, 
        Position position) {
            
        // Reproductive Traits
        var newReplicationChance = reproductiveTraits.replicationChance();
        var newReproductionCost = reproductiveTraits.reproductionCost();

        // Locomotion Traits
        var newMovementEfficiency = locomotionTraits.movementEfficiency();

        // Survival Traits
        var newMaxAge = survivalTraits.maxAge();
        var newDea = survivalTraits.deathChance();

        // Metabolic Traits
        var newIntakeEfficiency = metabolicTraits.intakeEfficiency();
        var newConsumptionRate = metabolicTraits.consumptionRate();
        double newMaxEnergy = metabolicTraits.maximumEnergy();
                
        // Check if the offspring mutated  
        if(reproductiveTraits.mutationChance() > rng.nextDouble()) {
            // Reproductive Mutations
            newReplicationChance = clamp(reproductiveTraits.replicationChance() + rng.nextGaussian() * 0.05);            
            newReproductionCost = reproductiveTraits.reproductionCost() + rng.nextGaussian() * 0.05;;

            // Locomotion Mutations
            newMovementEfficiency = clamp(locomotionTraits.movementEfficiency() + rng.nextGaussian() * 0.05);

            // Survival Mutations
            newMaxAge = (int) clamp(survivalTraits.maxAge() + rng.nextGaussian() * 0.05);
            newDea = clamp(survivalTraits.deathChance() + rng.nextGaussian() * 0.05);

            // Metabolic Mutations
            newIntakeEfficiency = clamp(metabolicTraits.intakeEfficiency() + rng.nextGaussian() * 0.05);
            newConsumptionRate = clamp(metabolicTraits.consumptionRate() + rng.nextGaussian() * 0.05);
            newMaxEnergy = metabolicTraits.maximumEnergy() + rng.nextGaussian() * 0.05;
        }

        var newReproductiveTraits = new ReproductiveTraits(newReplicationChance, newReproductionCost, reproductiveTraits.mutationChance());
        var newLocomotionTraits = new LocomotionTraits(newMovementEfficiency);
        var newSurvivalTraits = new SurvivalTraits(newDea, newMaxAge);
        var newMetabolicTraits = new MetabolicTraits(newMaxEnergy, newIntakeEfficiency, newConsumptionRate);

        var newAge = new Age(0);
        var newEnergyTraits = new Energy(newMaxEnergy/2);

        return List.of(newReproductiveTraits, newLocomotionTraits, newSurvivalTraits, newMetabolicTraits, newAge, newEnergyTraits, position);
    }

    private double clamp(double x) {
        return Math.max(1e-6, Math.min(0.99, x));
    }

    @Override
    protected List<Class<? extends Component>> requiredComponents() {
        return List.of(
            LocomotionTraits.class, 
            ReproductiveTraits.class,
            MetabolicTraits.class, 
            SurvivalTraits.class, 
            Age.class, 
            Energy.class, 
            Position.class);
    }

}
