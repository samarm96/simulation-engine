package com.sime;

import java.io.IOException;
import java.util.List;

import com.sime.commands.CommandBuffer;
import com.sime.components.Mass;
import com.sime.components.Position;
import com.sime.components.Velocity;
import com.sime.entities.EntityBuilder;
import com.sime.model.TwoBodyAstroModel;
import com.sime.reporting.BodyObserver;
import com.sime.scenario.SimulationBuilder;
import com.sime.systems.TwoBodyNewtonianGravitySystem;
import com.sime.temporal.FixedStepSimulationClock;
import com.sime.world.World;

public class Main {

    public static void main(String[] args) throws IOException {
        var world = new World(new TwoBodyAstroModel());

        new InitBody(
            new Position(1e4, 1e4), 
            new Velocity(0,0), 
            new Mass(1e12))
            .build(world);

        new InitBody(
            new Position(0, 0), 
            new Velocity(0,0), 
            new Mass(1e12))
            .build(world);


        var sim = new SimulationBuilder()
            .withWorld(world)
            .withCommandBuffer(new CommandBuffer())
            .withClock(new FixedStepSimulationClock())
            .withSystems(List.of(new TwoBodyNewtonianGravitySystem()))
            .withObservers(List.of(new BodyObserver()))
            .build();
        
        sim.nSteps(20);
        sim.save("orbital-mechanics-sim", sim);
    }

    private static record InitBody(Position pos, Velocity vel, Mass mass) {
        
        public int build(World world) {
            return new EntityBuilder(world)
            .with(pos)
            .with(vel)
            .with(mass)
            .build();
        }
    }
}
