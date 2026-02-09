package com.sime.gui.shapes;

import java.util.ArrayList;
import java.util.List;

public final class Trail2D {

    //private final double[] xs;
    private final List<Double> ys;
    private final List<Double> ts;
    private final List<Double> xs;
    private final int capacity;
    private int count = 0;

    public Trail2D(int capacity) {
        this.capacity = capacity;
        this.xs = new ArrayList<>();//new double[capacity];
        this.ys = new ArrayList<>();//new double[capacity];
        this.ts = new ArrayList<>();//new double[capacity];
    }

    public void add(double x, double y, double t) {

        if(xs.size() == capacity) {
            xs.remove(0);
            ys.remove(0);
            ts.remove(0);
        }

        xs.add(x);
        ys.add(y);
        ts.add(t);

        if (count < xs.size()) {
            count++;
        } 
    }

    public double[] getTrail() {
        double[] combined = new double[capacity*3];
        var n = 0;
        for(int i = 0; i < xs.size(); i++) {
            combined[n] = xs.get(i);
            n++;
            combined[n] = ys.get(i);
            n++;
            combined[n] = ts.get(i);
            n++;
        }
        return combined;
    }


    public int count() { return count; }
}

