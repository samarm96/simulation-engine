package com.sime.gui.utils;

public final class Mat4 {
    private Mat4() {}

    public static float[] identity() {
        return new float[] {
                1,0,0,0,
                0,1,0,0,
                0,0,1,0,
                0,0,0,1
        };
    }

    /** Model = Translate(x,y) * Scale(sx,sy) */
    public static float[] translateScale(float x, float y, float sx, float sy) {
        // Column-major
        return new float[] {
                sx, 0,  0, 0,
                0,  sy, 0, 0,
                0,  0,  1, 0,
                x,  y,  0, 1
        };
    }
}
