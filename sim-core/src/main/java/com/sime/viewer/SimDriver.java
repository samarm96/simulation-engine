package com.sime.viewer;

import com.sime.commands.WorldCommand;

public interface SimDriver {
    void init();
    void reset();
    void step();          // advances sim by dt (simulation time)
    void stepMultiple(int steps);
    SimSnapshot snapshot();        // read-only render data
    void submit(WorldCommand cmd);      // optional: input -> sim commands
    double simStepSize();
}

