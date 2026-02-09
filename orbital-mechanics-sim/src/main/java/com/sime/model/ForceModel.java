package com.sime.model;

import com.sime.components.Vector2;

public interface ForceModel {
    
    public Vector2 gravitationalAcceleration(Vector2 pos1, Vector2 pos2, double mass2);

}
