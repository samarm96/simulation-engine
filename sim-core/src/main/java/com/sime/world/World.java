package com.sime.world;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.sime.components.ComponentRegistry;
import com.sime.entities.EntityManager;
import com.sime.model.SimulationModel;
import com.sime.resources.ResourceKey;
import com.sime.spatial.FieldStore;

/**
 * A class representing the world that is being simulated
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class World {
    
    private EntityManager entityManager;
    private ComponentRegistry componentRegistry;
    private FieldStore fieldStore;
    private SimulationModel model;
    private Map<ResourceKey<?>, Object> resources;

    /**
     * Constructor. 
     * 
     * @param model the simulation model to use.
     */
    public World(SimulationModel model) {
        this.entityManager = new EntityManager();
        this.componentRegistry = new ComponentRegistry();
        this.fieldStore = new FieldStore();
        this.resources = new HashMap<>();
        this.model = model;
    }

    public ComponentRegistry getComponentRegistry() {
        return this.componentRegistry;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public FieldStore getFieldStore() {
        return this.fieldStore;
    }

    public SimulationModel getSimulationModel() {
        return this.model;
    }

    public Map<ResourceKey<?>, Object> getResources() {
        return this.resources;
    }
}
