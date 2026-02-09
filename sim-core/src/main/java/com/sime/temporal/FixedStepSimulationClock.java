package com.sime.temporal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public final class FixedStepSimulationClock extends SimulationClock {

    public FixedStepSimulationClock() {}

    public FixedStepSimulationClock(double dt) {
        setDt(dt);
    }
}
