package com.sime.viewer;

public record SimSnapshot(
        double time,
        long tick,
        BodyView[] bodies
) {}
