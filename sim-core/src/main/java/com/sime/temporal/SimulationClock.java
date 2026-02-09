package com.sime.temporal;

public abstract class SimulationClock {

    protected long tick = 0L;
    protected double dt = 1.0;

    public void advance() {
        tick++;
    }

    public long tick() {
        return tick;
    }

    public double dt() {
        return dt;
    }

    public double time() {
        // avoids cumulative floating drift
        return tick * dt;
    }

    public void setDt(double dt) {
        if (dt <= 0.0 || Double.isNaN(dt) || Double.isInfinite(dt)) {
            throw new IllegalArgumentException("dt must be finite and > 0");
        }
        this.dt = dt;
    }
}
