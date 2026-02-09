package com.sime.systems;

import java.util.List;

import com.sime.commands.ChangePositionCommand;
import com.sime.commands.ChangeVelocityCommand;
import com.sime.commands.CommandBuffer;
import com.sime.components.Component;
import com.sime.components.Mass;
import com.sime.components.Position;
import com.sime.components.Vector2;
import com.sime.components.Velocity;
import com.sime.model.ForceModel;
import com.sime.model.MultiBodyAstroModel;
import com.sime.world.World;

public class MultiBodyNewtonianGravitySystem extends SimSystem {

    @Override
    public void apply(World w, CommandBuffer queue, double dt) {

        var entities = w.getEntityManager().activeEntities().stream().toList();
        int n = entities.size();
        if (n < 2) return;

        var cr = w.getComponentRegistry();
        var fm = ((MultiBodyAstroModel) w.getSimulationModel()).forceModel(); 

        // Snapshot current state into arrays so we don't read/write mid-step
        Position[] p = new Position[n];
        Velocity[] v = new Velocity[n];
        double[] m = new double[n];

        for (int i = 0; i < n; i++) {
            var e = entities.get(i);
            p[i] = cr.get(e, Position.class);
            v[i] = cr.get(e, Velocity.class);
            m[i] = cr.get(e, Mass.class).mass();
        }

        // a(t)
        Vector2[] a = computeAccelerations(p, m, fm);

        // positions at t + dt
        Position[] pNew = new Position[n];
        for (int i = 0; i < n; i++) {
            var p_i = p[i];
            var v_i = v[i];
            var a_i = a[i];

            var pNext = p_i.add(v_i.scalarMultiply(dt)).add(a_i.scalarMultiply(0.5 * dt * dt));

            pNew[i] = new Position(pNext.toArray());
        }

        // a(t + dt)
        Vector2[] aNew = computeAccelerations(pNew, m, fm);

        // velocities at t + dt
        Velocity[] vNew = new Velocity[n];
        for (int i = 0; i < n; i++) {
            var vNext = v[i].add(a[i].add(aNew[i]).scalarMultiply(0.5 * dt));
            vNew[i] = new Velocity(vNext.toArray());
        }

        // enqueue commands (commit after system finishes)
        for (int i = 0; i < n; i++) {
            var e = entities.get(i);
            queue.add(new ChangePositionCommand(e, pNew[i]));
            queue.add(new ChangeVelocityCommand(e, vNew[i]));
        }
    }

    /** Computes acceleration on each body from all others. O(N^2). */
    private Vector2[] computeAccelerations(Position[] p, double[] m, ForceModel fm) {
        int n = p.length;
        Vector2[] a = new Vector2[n];
        for (int i = 0; i < n; i++) {
            a[i] = new Vector2(0, 0);
         } // or Vector2.zero()

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }

                a[i] = a[i].add(fm.gravitationalAcceleration(p[i], p[j], m[j]));
            }
        }
        return a;
    }

    @Override
    protected List<Class<? extends Component>> requiredComponents() {
        return List.of(
            Position.class,
            Velocity.class,
            Mass.class);
    }

}
