package com.sime.viewer;

import java.util.List;

import com.sime.io.DataSource;
import com.sime.model.MultiBodyAstroModel;
import com.sime.systems.MultiBodyNewtonianGravitySystem;
import com.sime.temporal.FixedStepSimulationClock;

public final class DefaultMultiBodyDriver extends OrbitalMechanicsSimDriver {

    public DefaultMultiBodyDriver(DataSource<OrbitSimSpec> source) {
        super(new SimulationContext<OrbitSimSpec>(
                new MultiBodyAstroModel(),
                List.of(new MultiBodyNewtonianGravitySystem()),
                List.of(),
                new FixedStepSimulationClock(),
                source)
            );
        
    }

    public DefaultMultiBodyDriver(DataSource<OrbitSimSpec> source, double dt) {
        super(new SimulationContext<OrbitSimSpec>(
                new MultiBodyAstroModel(),
                List.of(new MultiBodyNewtonianGravitySystem()),
                List.of(),
                new FixedStepSimulationClock(dt),
                source)
            );
        
    }
}
