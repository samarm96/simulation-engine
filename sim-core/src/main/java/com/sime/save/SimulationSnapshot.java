package com.sime.save;

import java.util.Map;
import java.util.Set;

import com.sime.components.Component;
import com.sime.spatial.ScalarField2D;

public record SimulationSnapshot(
    long step,
    long seed,
    Set<Integer> activeEntities,
    int nextEntityId,
    Map<Class<? extends Component>, Map<Integer, Component>> registry,
    Map<String, ScalarField2D> scalar2DFields) {
    
}
