package com.sime.reporting;

import com.sime.components.Position;
import com.sime.world.World;

public class BodyObserver implements Observer {

    @Override
    public void onInit(World world) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onInit'");
    }

    @Override
    public void onStep(World world, long step) {
        var entities = world.getEntityManager().activeEntities().stream().toList();

        for(var entity : entities) {
            var pos = world.getComponentRegistry().get(entity, Position.class);
            System.out.println("Entity: " + entity + " (" + pos.x() + ", " + pos.y() + ")");
        }
    }

    @Override
    public void onFinish(World world) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onFinish'");
    }
    
}
