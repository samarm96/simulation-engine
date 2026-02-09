package com.sime.viewer;

import java.util.ArrayList;
import java.util.List;

import com.sime.Simulation;
import com.sime.commands.CommandBuffer;
import com.sime.commands.WorldCommand;
import com.sime.components.Mass;
import com.sime.components.Position;
import com.sime.components.Velocity;
import com.sime.entities.EntityBuilder;
import com.sime.scenario.SimulationBuilder;
import com.sime.world.World;

public abstract class OrbitalMechanicsSimDriver implements SimDriver {

    protected final SimulationContext<OrbitSimSpec> context;

    protected Simulation sim;
    protected World world;

    protected boolean isInitialized;
    protected final List<Integer> bodies = new ArrayList<>();

    public OrbitalMechanicsSimDriver(SimulationContext<OrbitSimSpec> context) {
        this.context = context;
    }

    @Override
    public void init() {
        rebuild();
        isInitialized = true;
    }

    @Override
    public void reset() {
        rebuild();
    }

    @Override 
    public void step() { 
        if(isInitialized) { 
            sim.step(); 
        } 
    }

    @Override 
    public void stepMultiple(int steps) { 
        if(isInitialized) { 
            sim.nSteps(steps); 
        } 
    }

    @Override 
    public SimSnapshot snapshot() { 
        var cr = world.getComponentRegistry(); 
        var bodyViews = new BodyView[bodies.size()]; 
        for(int i = 0; i < bodies.size(); i++) { 
            var body = bodies.get(i); 
            var p = cr.get(body, Position.class); 
            var v = cr.get(body, Velocity.class); 
            var m = cr.get(body, Mass.class); 
            bodyViews[i] = new GravitationalBodyView(
                body, 
                p.x(), p.y(), 
                v.x(), v.y(), 
                m.mass()); 
        } 

        return new SimSnapshot(sim.getClock().time(), sim.getClock().tick(), bodyViews); 
    }

    @Override 
    public void submit(WorldCommand cmd) { 
        // TODO Auto-generated method stub 
        throw new UnsupportedOperationException("Unimplemented method 'submit'"); 
    }

    @Override
    public double simStepSize() {
        return this.sim.getClock().dt();
    }

    private void rebuild() {
        bodies.clear();

        this.world = new World(context.model());

        var spec = context.dataSource().load();
        spawnBodies(spec.planets());

        this.sim = new SimulationBuilder()
            .withWorld(world)
            .withCommandBuffer(new CommandBuffer())
            .withClock(context.clock())
            .withSystems(context.systems())
            .withObservers(context.observers())
            .build();
    }


    protected void spawnBodies(List<Planet> planets) {
        for (var planet : planets) {
            var body = new EntityBuilder(world)
                .with(new Position(planet.position().toArray()))
                .with(new Velocity(planet.velocity().toArray()))
                .with(planet.mass())
                .build();

            bodies.add(body);
        }
    }

}
