package com.sime.gui.render;

import static org.lwjgl.opengl.GL11.*;

import com.sime.gui.camera.Camera2D;
import com.sime.gui.shaders.ShaderProgram;
import com.sime.gui.shapes.LineStripMesh2D;
import com.sime.gui.utils.Mat4;

public final class LineRenderer2D implements ShapeRenderer {

    private final ShaderProgram shader;
    private final LineStripMesh2D mesh;

    public LineRenderer2D(ShaderProgram program) {
        this.shader = program;
        this.mesh = new LineStripMesh2D(2);
    }

    public void begin(Camera2D camera) {
        shader.use();
        shader.setMat4("uWorldToClip", camera.worldToClipMatrix());
    }

    public void color(float r, float g, float b, float a) {
        shader.setVec4("uColor", r, g, b, a);
    }

    public void draw(DrawableParameters params) {
        Colors c = params.colors();
        color(c.r(), c.g(), c.b(), c.a());
        shader.setMat4("uModel", Mat4.identity());

        mesh.upload(params.values(), params.vertices());
        mesh.draw(params.vertices());
    }

    public void draw(double[] values, int vertices) {
        shader.setMat4("uModel", Mat4.identity());
        
        mesh.upload(values, vertices);
        mesh.draw(vertices);
    }

    /** Optional: thickness (works on many drivers, not guaranteed in core profile) */
    public void lineWidth(float px) {
        glLineWidth(px);
    }

    public void delete() {
        mesh.delete();
        shader.delete();
    }
}
