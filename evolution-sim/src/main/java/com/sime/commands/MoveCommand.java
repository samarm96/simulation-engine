package com.sime.commands;

import com.sime.components.Position;
import com.sime.world.World;

public class MoveCommand extends WorldCommand {

    private final int newX;
    private final int newY;
    
    public MoveCommand(int id, int newX, int newY) {
        super(id);
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    void apply(World w) {
        var newPosition = new Position(newX, newY);
        w.getComponentRegistry().put(getId(), newPosition);
    }
    
}
