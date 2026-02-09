package com.sime.gui.render;

import java.util.ArrayList;
import java.util.List;

import com.sime.viewer.GravitationalBodyView;
import com.sime.gui.shaders.ShaderProgram;
import com.sime.gui.shapes.Trail2D;
import com.sime.viewer.BodyView;

public final class OrbitTrailRenderer implements Renderer {
    
    private final double renderScale;

    private LineRenderer2D renderer;
    private List<Trail2D> trails;
    private int trailMaxLength;

    public OrbitTrailRenderer(ShaderProgram program, int trailMaxLength, double renderScale) {
        this.renderer = new LineRenderer2D(program);
        this.trailMaxLength = trailMaxLength;
        this.trails = new ArrayList<>();
        this.renderScale = renderScale;
    }

    @Override
    public void init() {}

    @Override
    public void render(FrameContext fc) {
        BodyView[] bodies = fc.snapshot().bodies();

        if(trails.size() < bodies.length) {
            for(int i = 0; i < bodies.length; i++) {
                trails.add(new Trail2D(trailMaxLength));
            }
        }

        for(int i = 0; i < trails.size(); i++) {
            var body = (GravitationalBodyView) bodies[i];
            var bodyTrail = trails.get(i);

            bodyTrail.add(body.x() * renderScale, body.y() * renderScale, fc.now());
            
            renderer.begin(fc.camera());
            renderer.color(1f, 0.8f, 0.2f, 1f);
            renderer.lineWidth(2f);
            renderer.draw(bodyTrail.getTrail(), bodyTrail.count());
        }
    }

    @Override
    public void delete() {
        renderer.delete();
    }
}
