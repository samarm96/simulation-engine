package com.sime.entities;

import java.util.ArrayList;
import java.util.List;

import com.sime.components.Component;
import com.sime.world.World;

public final class EntityBuilder {
    
    private final World world;
    private final int entity;
    private final List<Component> components = new ArrayList<>();

    public EntityBuilder(World world) {
        this.world = world;
        this.entity = world.getEntityManager().create();
    }

    public EntityBuilder with(Component component) {
        components.add(component);
        return this;
    }

    public int build() {
        var reg = world.getComponentRegistry();
        for (var c : components) {
            reg.put(entity, c);
        } 
        return entity;
    }
}
