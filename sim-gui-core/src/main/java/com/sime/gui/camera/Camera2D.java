package com.sime.gui.camera;

public final class Camera2D {
    private float centerX = 0f;
    private float centerY = 0f;

    // zoom = pixels per world unit (bigger = closer)
    private float zoom = 50f;

    private int viewportWidth = 800;
    private int viewportHeight = 600;

    public Camera2D() {}

    public float centerX() {
        return this.centerX;
    }

    public float centerY() {
        return this.centerY;
    }

    public float zoom() {
        return this.zoom;
    }

    public int viewPortWidth() {
        return this.viewportWidth;
    }

    public int viewPortHeight() {
        return this.viewportHeight;
    }

    public void setViewport(int w, int h) {
        this.viewportWidth = w;
        this.viewportHeight = h;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public void centerValues(float x, float y) {
        this.centerX = x;
        this.centerY = y;
    }

    /** Pan camera by screen pixels (dx, dy). dx right, dy down from mouse. */
    public void panPixels(float dxPixels, float dyPixels) {
        float pixelsToWorld = 1.0f / zoom;
        centerX -= dxPixels * pixelsToWorld;
        centerY += dyPixels * pixelsToWorld; // screen down => world up
    }

    /** Zoom in/out around the center. Factor >1 zooms in, <1 zooms out. */
    public void zoomBy(float factor) {
        zoom *= factor;
        zoom = clamp(zoom, 1e-3f, 1e9f);
    }

    /** Column-major 4x4 matrix mapping world coords to OpenGL clip space. */
    public float[] worldToClipMatrix() {
        float sx = 2.0f * zoom / viewportWidth;
        float sy = 2.0f * zoom / viewportHeight;

        // Column-major matrix:
        // [ sx  0  0  0 ]
        // [ 0  sy  0  0 ]
        // [ 0   0  1  0 ]
        // [ -sx*cx  -sy*cy  0  1 ]
        return new float[] {
                sx,  0,  0,  0,
                0,  sy,  0,  0,
                0,   0,  1,  0,
                -sx * centerX, -sy * centerY, 0, 1
        };
    }

    private static float clamp(float v, float lo, float hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}
