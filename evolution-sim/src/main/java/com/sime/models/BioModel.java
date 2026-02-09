package com.sime.models;

import com.sime.components.Age;
import com.sime.components.Energy;
import com.sime.components.MetabolicTraits;
import com.sime.components.ReproductiveTraits;
import com.sime.components.SurvivalTraits;
import com.sime.model.SimulationModel;

public interface BioModel extends SimulationModel {
    
    double deathProbability(SurvivalTraits s, Age a, Energy e, MetabolicTraits m, double dt);

    boolean canReproduce(ReproductiveTraits r, Energy e, MetabolicTraits m);

    double reproductionEnergyCost(ReproductiveTraits r, MetabolicTraits m);

    double metabolicBurn(MetabolicTraits m, Age a, double dt);

    double energyFromIntake(MetabolicTraits m, double nutrientIntake);
}
