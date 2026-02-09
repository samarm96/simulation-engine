package com.sime.gui.render;

import static org.lwjgl.opengl.GL11.*;

import com.sime.gui.camera.Camera2D;
import com.sime.gui.shaders.ShaderProgram;
import com.sime.gui.shapes.CircleMesh2D;
import com.sime.gui.utils.Mat4;

public final class CircleRenderer2D implements ShapeRenderer {
    
    private final ShaderProgram shader;
    private final CircleMesh2D mesh;

    public CircleRenderer2D(ShaderProgram shader) {
        this.shader = shader;
        this.mesh = new CircleMesh2D(64);
    }

    public void begin(Camera2D camera) {
        glClear(GL_COLOR_BUFFER_BIT);

        shader.use();
        shader.setMat4("uWorldToClip", camera.worldToClipMatrix());
    }

    public void positionRadius(float x, float y, float radius) {
        shader.setMat4("uModel", Mat4.translateScale(x, y, radius, radius));
    }

    public void color(float r, float g, float b, float a) {
        shader.setVec4("uColor", r, g, b, a);
    }

    public void draw(DrawableParameters params) {
        Colors c = params.colors();
        var values = params.values();

        color(c.r(), c.g(), c.b(), c.a());
        positionRadius((float) values[0], (float) values[1], params.radius());
        mesh.upload();
        mesh.draw();
    }

    public void draw() {
        mesh.upload();
        mesh.draw();
    }

    public void end() {
        // Nothing needed yet
    }

    public void delete() {
        mesh.delete();
        shader.delete();
    }
}
