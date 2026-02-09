package com.sime;

import java.util.List;

import com.sime.components.Age;
import com.sime.components.Component;
import com.sime.components.Energy;
import com.sime.components.LocomotionTraits;
import com.sime.components.MetabolicTraits;
import com.sime.components.Position;
import com.sime.components.ReproductiveTraits;
import com.sime.components.SurvivalTraits;
import com.sime.scenario.EvolutionScenario;

public class Main {
    
    public static void main(String[] args) {


        var scenario = new EvolutionScenario(
            2, 
            founderComponents(),
            8);

        var sim = scenario.sim();

        sim.nSteps(10);
    }

    private static List<Component> founderComponents() {
        var founderEnergy = new Energy(10.0);
        var founderAge = new Age(0);
        var founderMetabolicTraits = new MetabolicTraits(20, 0.5, 0.2);
        var founderReproductiveTraits = new ReproductiveTraits(0.8, 0.2, 0.5);
        var founderSurvivalTraits = new SurvivalTraits(0.1, 20);
        var founderLocomotionTraits = new LocomotionTraits(0.5);
        var founderPosition = new Position(1, 1);

        return List.of(
            founderEnergy,
            founderAge,
            founderPosition,
            founderMetabolicTraits,
            founderLocomotionTraits,
            founderReproductiveTraits,
            founderSurvivalTraits);
    }
}
