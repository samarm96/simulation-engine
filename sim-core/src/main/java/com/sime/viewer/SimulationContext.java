package com.sime.viewer;

import java.util.List;

import com.sime.io.DataSource;
import com.sime.model.SimulationModel;
import com.sime.reporting.Observer;
import com.sime.systems.SimSystem;
import com.sime.temporal.SimulationClock;

public record SimulationContext<T> (
    SimulationModel model, 
    List<SimSystem> systems, 
    List<Observer> observers, 
    SimulationClock clock,
    DataSource<T> dataSource) {
    
}
