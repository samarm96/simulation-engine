package com.sime.model;

import com.sime.components.Vector2;

public class MultiBodyForceModel implements ForceModel {

    private static final double GRAV_CONSTANT = 6.674e-11; //m^3/kg*s

    // accel on "attracted" due to "attractor"
    public Vector2 gravitationalAcceleration(Vector2 pos, Vector2 otherPos, double otherMass) {
        Vector2 delta = otherPos.subtract(pos);  // pos -> otherPos

        double eps2 = 1e-4; 
        double r2 = delta.dot(delta);
        r2 = Math.max(r2, eps2);
        double invR = 1.0 / Math.sqrt(r2);
        double invR3 = invR * invR * invR;


        // a = G * m_other * r_hat / r^2 = G * m_other * delta / r^3
        return delta.scalarMultiply(GRAV_CONSTANT * otherMass * invR3);
    }
}
