package com.sime.commands;

import java.util.ArrayList;
import java.util.List;

import com.sime.world.World;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CommandBuffer {
    
    List<WorldCommand> queue;

    public CommandBuffer() {
        this.queue = new ArrayList<>();
    }

    public boolean add(WorldCommand command) {
        return queue.add(command);
    }

    public boolean remove(WorldCommand command) {
        return queue.remove(command);
    }

    public void destroy(int id) {
        var entriesToRemove = queue.stream().filter(x -> Integer.valueOf(x.getId()).equals(id)).toList();
        queue.removeAll(entriesToRemove);
    }

    public void applyAll(World w) {
        queue.forEach(c -> c.apply(w));
    }

    public void clear() {
        queue.clear();
    }
}
