package com.sime.systems.reaction;

@FunctionalInterface
public interface Reaction {
    /** Returns dU/dt at this cell, given current U. */
    double duDt(double u);
}
