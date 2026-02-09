package com.sime.viewer;

import com.sime.components.Mass;
import com.sime.components.Vector2;

public record Planet(Vector2 position, Vector2 velocity, Mass mass) {
    
}
