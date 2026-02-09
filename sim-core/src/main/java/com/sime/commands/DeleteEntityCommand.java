package com.sime.commands;

import com.sime.world.World;

public class DeleteEntityCommand extends WorldCommand {

    public DeleteEntityCommand(int id) {
        super(id);
    }

    @Override
    void apply(World w) {
        w.getComponentRegistry().destroy(id);
        w.getEntityManager().destroy(id);
    }
    
}
