package com.sime.gui.render;

import com.sime.gui.shaders.ShaderProgram;
import com.sime.viewer.BodyView;
import com.sime.viewer.GravitationalBodyView;

public final class PlanetRenderer implements Renderer {

    private CircleRenderer2D renderer;
    private double distanceScale;
    private double bigSize;

    public PlanetRenderer(ShaderProgram program, double distanceScale, double bigSize) {
        this.renderer = new CircleRenderer2D(program);
        this.distanceScale = distanceScale;
        this.bigSize = bigSize;
    }
    
    @Override
    public void init() {}

    @Override
    public void render(FrameContext fc) {
        BodyView[] bodies = fc.snapshot().bodies();
        var camera = fc.camera();

        // Render
        renderer.begin(camera);

        for (var bv : bodies) {
            var b = (GravitationalBodyView) bv;

            // radius is arbitrary visualization size in world units
            // adjust based on your world scale
            var big = b.mass() >= bigSize;
            var radiusScale = (float)  (b.mass() / bigSize);
            // color: big body brighter
            var radius = 10f * radiusScale;

            var scaledX = (float)(b.x() * distanceScale);
            var scaledY = (float) (b.y() * distanceScale);
            if (big) {
                radius = radius*5; 
                renderer.positionRadius(scaledX, scaledY, radius);
                renderer.color(0.9f, 0.9f, 0.2f, 1.0f);
                renderer.draw();
            } else {
                renderer.positionRadius(scaledX, scaledY, radius);
                renderer.color(0.4f, 0.8f, 1.0f, 1.0f);
                renderer.draw();
            }
        }
    }

    @Override
    public void delete() {
        renderer.delete();
    }
}
