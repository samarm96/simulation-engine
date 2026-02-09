package com.sime.models;

import com.sime.components.Age;
import com.sime.components.Energy;
import com.sime.components.MetabolicTraits;
import com.sime.components.ReproductiveTraits;
import com.sime.components.SurvivalTraits;

public class DefaultBioModel implements BioModel {

    @Override
    public double deathProbability(SurvivalTraits s, Age a, Energy e, MetabolicTraits m, double dt) {
        double ageFrac = clamp01(a.age() / (s.maxAge() + 1e-9));
        double ageFactor = Math.pow(ageFrac, 3.0);      // shape knob
        double starvation = 1.0 - clamp01(e.amount() / (m.maximumEnergy() + 1e-9));

        double p = s.deathChance() * (0.2 + 0.8 * ageFactor) * starvation;
        return clamp01(p * dt);
    }

    @Override
    public boolean canReproduce(ReproductiveTraits r, Energy e, MetabolicTraits m) {
        return e.amount() >= reproductionEnergyCost(r, m);
    }

    @Override
    public double reproductionEnergyCost(ReproductiveTraits r, MetabolicTraits m) {
        return r.reproductionCost();
    }

    @Override
    public double metabolicBurn(MetabolicTraits m, Age a, double dt) {
        // example tradeoff: burn scales with max energy and move/intake efficiency if you want
        double burnPerTick = m.consumptionRate();
        return burnPerTick * dt;
    }

    @Override
    public double energyFromIntake(MetabolicTraits m, double nutrientIntake) {
        return nutrientIntake * m.intakeEfficiency();
    }

    private static double clamp01(double x) { return Math.max(0.0, Math.min(1.0, x)); }
    
}
