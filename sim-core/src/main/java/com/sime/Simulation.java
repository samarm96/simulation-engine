package com.sime;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.sime.commands.CommandBuffer;
import com.sime.reporting.Observer;
import com.sime.save.SaveUtility;
import com.sime.scenario.Scenario;
import com.sime.systems.SimSystem;
import com.sime.temporal.SimulationClock;
import com.sime.world.World;


/**
 * A class used to run simulations.
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Simulation {

    private final World world;
    private final SimulationClock clock;
    private final CommandBuffer buffer;
    private final List<? extends SimSystem> systems;
    private final List<? extends Observer> observers;

    private int stepCount = 0;
    
    /**
     * Constructor. 
     * 
     * @param world the world to use
     * @param systems the systems to use 
     * @param buffer the command buffer to use
     * @param observers the observers to use
     */
    public Simulation(
        World world, 
        List<? extends SimSystem> systems, 
        CommandBuffer buffer, 
        List<? extends Observer> observers,
        SimulationClock clock) {

        this.world = world;
        this.systems = systems;
        this.buffer = buffer;
        this.observers = observers;
        this.clock = clock;
    }

    /**
     * Method used to initialize a simulation from a scenario.
     * 
     * @param scenario the scenario to use
     */
    public void initialize(Scenario scenario) {
        buffer.clear();
        scenario.initialize(buffer);
        buffer.applyAll(world);
    }

    /**
     * Step the simulation forward N number of steps
     * 
     * @param steps the number of steps 
     */
    public void nSteps(int steps) {
        for(int i = 0; i < steps; i++) {
            step();
        }
    }

    /**
     * Step forward one step
     * @param dt the time to move between each step
     */
    public void step() {

        buffer.clear();

        for (SimSystem s : systems){
            s.apply(world, buffer, clock.dt());    
        } 

        buffer.applyAll(world);
        stepCount++;

        for (var obs : observers) {
            obs.onStep(world, stepCount);
        } 
        clock.advance();
    }

    public SimulationClock getClock() {
        return this.clock;
    }

    public void save(String directoryName, Simulation sim) throws IOException {
        var save = new SaveUtility(directoryName);
        save.save(sim);
    }
}