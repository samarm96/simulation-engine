package com.sime.commands;

import com.sime.components.Velocity;
import com.sime.world.World;

public class ChangeVelocityCommand extends WorldCommand {
    
    private final Velocity velocity;

    public ChangeVelocityCommand(int id, Velocity velocity) {
        super(id);
        this.velocity = velocity;
    }

    @Override
    void apply(World w) {
        w.getComponentRegistry().put(getId(), velocity);
    }
    
}
