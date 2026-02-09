package com.sime.entities;

import com.sime.world.World;

public class EntityDestroyer {

    private final World world;
    private final int entity;

    EntityDestroyer(World world, int entity) {
        this.world = world;
        this.entity = entity;
    }

    public void destroy() {
        world.getComponentRegistry().destroy(entity);
        world.getEntityManager().destroy(entity);
    }
}
