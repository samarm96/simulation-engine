package com.sime.viewer;

import java.util.List;

import com.sime.io.DataSource;
import com.sime.model.TwoBodyAstroModel;
import com.sime.systems.TwoBodyNewtonianGravitySystem;
import com.sime.temporal.FixedStepSimulationClock;

public final class DefaultTwoBodyDriver extends OrbitalMechanicsSimDriver {

    public DefaultTwoBodyDriver(DataSource<OrbitSimSpec> source) {
        super(new SimulationContext<OrbitSimSpec>(
            new TwoBodyAstroModel(),
            List.of(new TwoBodyNewtonianGravitySystem()),
            List.of(),
            new FixedStepSimulationClock(),
            source));
        
    }

    public DefaultTwoBodyDriver(DataSource<OrbitSimSpec> source, double dt) {
        super(new SimulationContext<OrbitSimSpec>(
            new TwoBodyAstroModel(),
            List.of(new TwoBodyNewtonianGravitySystem()),
            List.of(),
            new FixedStepSimulationClock(dt),
            source));
        
    }

}
