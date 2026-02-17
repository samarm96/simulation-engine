package com.sime.gui.render;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import com.sime.gui.camera.Camera2D;
import com.sime.gui.shaders.ShaderProgram;
import com.sime.gui.shapes.SquareMesh2D;
import com.sime.gui.utils.Mat4;

public final class SquareRenderer2D {

    private final ShaderProgram shader;
    private final SquareMesh2D mesh;

    public SquareRenderer2D(ShaderProgram shader) {
        this.shader = shader;
        this.mesh = new SquareMesh2D();
    }

    public void begin(Camera2D camera) {
        glClear(GL_COLOR_BUFFER_BIT);
        shader.use();
        shader.setMat4("uWorldToClip", camera.worldToClipMatrix());
    }

    public void color(float r, float g, float b, float a) {
        shader.setVec4("uColor", r, g, b, a);
    }

    public void drawSquare(float centerX, float centerY, float size) {
        shader.setMat4("uModel", Mat4.translateScale(centerX, centerY, size, size));
        //mesh.upload();
        mesh.draw();
    }

    public void drawRect(float centerX, float centerY, float width, float height) {
        shader.setMat4("uModel", Mat4.translateScale(centerX, centerY, width, height));
        //mesh.upload();
        mesh.draw();
    }

    public void delete() {
        mesh.delete();
        shader.delete();
    }
}
