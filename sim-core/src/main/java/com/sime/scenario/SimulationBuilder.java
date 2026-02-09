package com.sime.scenario;

import java.util.List;

import com.sime.Simulation;
import com.sime.commands.CommandBuffer;
import com.sime.reporting.Observer;
import com.sime.systems.SimSystem;
import com.sime.temporal.SimulationClock;
import com.sime.world.World;

public class SimulationBuilder {
    
    private World world;
    private SimulationClock clock;
    private CommandBuffer buffer;
    private List<? extends SimSystem> systems;
    private List<? extends Observer> observers;

    public SimulationBuilder(){};

    public Simulation build() {
        return new Simulation(world, systems, buffer, observers, clock);
    }

    public SimulationBuilder withWorld(World value) {
        this.world = value;
        return this;
    }

    public SimulationBuilder withClock(SimulationClock value) {
        this.clock = value;
        return this;
    }

    public SimulationBuilder withCommandBuffer(CommandBuffer value) {
        this.buffer = value;
        return this;
    }

    public SimulationBuilder withSystems(List<? extends SimSystem> value) {
        this.systems = value;
        return this;
    }

    public SimulationBuilder withObservers(List<? extends Observer> value) {
        this.observers = value;
        return this;
    }
}
