package com.sime.commands;

import com.sime.components.Energy;
import com.sime.world.World;

public class AddEnergyCommand extends WorldCommand {

    private final double amount;
    
    public AddEnergyCommand(int id, double amount) {
        super(id);
        this.amount = amount;
    }

    @Override
    void apply(World w) {
        var energy = w.getComponentRegistry().get(getId(), Energy.class);
        energy.addEnergy(amount);

    }
}
