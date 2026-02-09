package com.sime;

import java.io.IOException;
import java.util.List;

import com.sime.commands.CommandBuffer;
import com.sime.reporting.FieldObserver;
import com.sime.spatial.DefaultOutOfBoundsRule;
import com.sime.systems.DiffusionSystem;
import com.sime.systems.ReactionSystem;
import com.sime.systems.diffusion.Stencils;
import com.sime.systems.reaction.Reactions;
import com.sime.temporal.FixedStepSimulationClock;
import com.sime.world.World;

public class Main {

    public static void main(String[] args) throws IOException {

        var world = new World(null);
        var fieldName = "nutrients";
        world.getFieldStore().createScalar2D(fieldName, 3, 3, DefaultOutOfBoundsRule.clamp());
        world.getFieldStore().scalar2D(fieldName).set(1, 1, 9);
        world.getFieldStore().scalar2D(fieldName).swap();
        world.getFieldStore().scalar2D(fieldName).copyReadToWrite();

        var buffer = new CommandBuffer();
        var diffusionSystem = new DiffusionSystem(
            fieldName,
            0.05,
            Stencils.laplacian5(),
            DefaultOutOfBoundsRule.clamp());

        var reactions = Reactions.sum(
            Reactions.regrowToMax(0.01, 9),
            Reactions.decay(0.005));

        var reactionSystem = new ReactionSystem(fieldName, reactions);
        var observers = new FieldObserver(fieldName);

        
        var sim = new Simulation(
            world, 
            List.of(reactionSystem, diffusionSystem), 
            buffer, 
            List.of(observers),
            new FixedStepSimulationClock());
        
        sim.nSteps(2);
    }
}
