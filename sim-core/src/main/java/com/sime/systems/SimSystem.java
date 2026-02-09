package com.sime.systems;

import java.util.List;

import com.sime.commands.CommandBuffer;
import com.sime.components.Component;
import com.sime.world.World;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public abstract class SimSystem {
    
    public abstract void apply(World w, CommandBuffer queue, double dt);

    protected abstract List<Class<? extends Component>> requiredComponents();
}
