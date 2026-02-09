package com.sime.commands;

import com.sime.world.World;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public abstract class WorldCommand {
    
    protected int id;

    public WorldCommand(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    abstract void apply(World w);
}
