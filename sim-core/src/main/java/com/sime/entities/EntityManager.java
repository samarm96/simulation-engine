package com.sime.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class EntityManager {

    private Set<Integer> activeEntities;
    private int nextId;

    public EntityManager() {
        this.activeEntities = new HashSet<Integer>();
        this.nextId = 0;
    }

    public Set<Integer> activeEntities() {
        return Set.copyOf(activeEntities);
    }

    public int create() {
        var newEntityId = nextId;
        if(!activeEntities.contains(newEntityId)) {
            activeEntities.add(nextId);
            nextId++;
            return newEntityId;
        }

        return -1;
    }

    public int destroy(int id) {
        if(activeEntities.contains(id)) {
            activeEntities.remove(id);
            return id;
        }

        return -1;
    }

    public int get(int id) {
        var entityId = activeEntities.stream().filter(x -> x.equals(id)).findFirst();

        if (entityId.isPresent()) {
            return entityId.get();
        }
        
        return -1;
    }
    
}
