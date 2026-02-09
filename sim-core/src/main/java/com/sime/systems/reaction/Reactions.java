package com.sime.systems.reaction;

public final class Reactions {

    /** Logistic / KPP reaction: r*u*(1 - u/K) */
    public static Reaction logistic(double r, double K) {
        return u -> r * u * (1.0 - (u / K));
    }

    /** Regrowth-to-capacity: r*(Umax - u) */
    public static Reaction regrowToMax(double r, double Umax) {
        return u -> r * (Umax - u);
    }

    /** Linear decay: -lambda*u */
    public static Reaction decay(double lambda) {
        return u -> -lambda * u;
    }

    /** Combine multiple reactions by summing du/dt (stacking). */
    public static Reaction sum(Reaction... terms) {
        return u -> {
            double s = 0.0;
            for (Reaction t : terms) s += t.duDt(u);
            return s;
        };
    }
}
