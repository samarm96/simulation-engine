package com.sime.viewer;

public record GravitationalBodyView(
        double id,
        double x,
        double y,
        double vx,
        double vy,
        double mass) implements BodyView {
    
}
