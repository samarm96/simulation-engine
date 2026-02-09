package com.sime.model;

import com.sime.components.Vector2;

public class TwoBodyForceModel implements ForceModel {
    
    private static final double GRAV_CONSTANT = 6.674e-11;

    public Vector2 gravitationalAcceleration(Vector2 pos1, Vector2 pos2, double m2) {
        Vector2 delta = pos2.subtract(pos1);  // vector from 1 -> 2

        double r2 = delta.dot(delta);
        double r = Math.sqrt(r2);

        Vector2 rHat = delta.scalarMultiply(1.0 / r);

        // a1 = G * m2 / r^2 * rHat
        double aMag = GRAV_CONSTANT * m2 / r2;
        return rHat.scalarMultiply(aMag);
    }

}
