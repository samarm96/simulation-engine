package com.sime.systems;

import java.util.List;

import com.sime.commands.ChangePositionCommand;
import com.sime.commands.ChangeVelocityCommand;
import com.sime.commands.CommandBuffer;
import com.sime.components.Component;
import com.sime.components.Mass;
import com.sime.components.Position;
import com.sime.components.Velocity;
import com.sime.model.TwoBodyAstroModel;
import com.sime.world.World;

public class TwoBodyNewtonianGravitySystem extends SimSystem {

    @Override
    public void apply(World w, CommandBuffer queue, double dt) {

        var entities = w.getEntityManager().activeEntities().stream().toList();
        var e1 = entities.get(0);
        var e2 = entities.get(1);

        var cr = w.getComponentRegistry();

        var p1 = cr.get(e1, Position.class); 
        var v1 = cr.get(e1, Velocity.class);
        var m1 = cr.get(e1, Mass.class);

        var p2 = cr.get(e2, Position.class);
        var v2 = cr.get(e2, Velocity.class);
        var m2 = cr.get(e2, Mass.class);

        var fm = ((TwoBodyAstroModel) w.getSimulationModel()).forceModel();

        // a(t)
        var a1 = fm.gravitationalAcceleration(p1, p2, m2.mass());
        var a2 = fm.gravitationalAcceleration(p2, p1, m1.mass());

        // positions at t+dt
        var p1New = p1.add(v1.scalarMultiply(dt)).add(a1.scalarMultiply(0.5 * dt * dt));
        var p2New = p2.add(v2.scalarMultiply(dt)).add(a2.scalarMultiply(0.5 * dt * dt));

        // a(t+dt)
        var a1New = fm.gravitationalAcceleration(p1New, p2New, m2.mass());
        var a2New = fm.gravitationalAcceleration(p2New, p1New, m1.mass());


        // velocities at t+dt
        var v1New = v1.add(a1.add(a1New).scalarMultiply(0.5 * dt));
        var v2New = v2.add(a2.add(a2New).scalarMultiply(0.5 * dt));

        queue.add(new ChangePositionCommand(e1, new Position(p1New.toArray())));
        queue.add(new ChangePositionCommand(e2, new Position(p2New.toArray())));

        queue.add(new ChangeVelocityCommand(e1, new Velocity(v1New.toArray())));
        queue.add(new ChangeVelocityCommand(e2, new Velocity(v2New.toArray())));
    }

    @Override
    protected List<Class<? extends Component>> requiredComponents() {
        return List.of(
            Position.class,
            Velocity.class,
            Mass.class);
    }
}
