package com.sime.commands;

import com.sime.components.Position;
import com.sime.world.World;

public class ConsumeNutrientCommand extends WorldCommand {

    private final double amount;
    
    public ConsumeNutrientCommand(int id, double amount) {
        super(id);
        this.amount = amount;
    }

    @Override
    void apply(World w) {
        var position = w.getComponentRegistry().get(getId(), Position.class);
        var field = w.getFieldStore().scalar2D("nutrients");

        field.add(position.x(), position.y(), -amount);
        field.swap();
        field.copyReadToWrite();
    }
    
}
