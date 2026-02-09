package com.sime.commands;

import com.sime.components.Position;
import com.sime.world.World;

public class ChangePositionCommand extends WorldCommand {
    
    private final Position position;

    public ChangePositionCommand(int id, Position position) {
        super(id);
        this.position = position;
    }

    @Override
    void apply(World w) {
        w.getComponentRegistry().put(getId(), position);
    }
    
}
