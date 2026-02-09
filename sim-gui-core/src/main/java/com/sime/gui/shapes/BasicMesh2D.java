package com.sime.gui.shapes;

import java.nio.DoubleBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public abstract class BasicMesh2D implements Mesh2D {

    protected static final int DIMENSION = 2;

    protected int vertexCount;
    protected DoubleBuffer buffer;
    protected int vbo;
    protected int vao;

    protected double[] values;

    public void put(double[] arr) {
        values = arr;
        buffer.put(arr);
    }

    public void translate(double x, double y) {

        var curr = Arrays.copyOf(values, values.length);

        var translated = new double[curr.length];

        for(int i = 0; i < curr.length; i++) {
            if(i % 2 == 0) {
                translated[i] = curr[i] + x;
            } else {
                translated[i] = curr[i] + y;
            }
        }

        put(translated);
    }

    public void upload(DoubleBuffer buffer) {
        buffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void draw(int glPrimitive, int vertexCount) {
        glBindVertexArray(vao);
        glDrawArrays(glPrimitive, 0, vertexCount);
        glBindVertexArray(0);
    }

    public void clear() {
        buffer.clear();
    }

    public void delete() {
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
    }
}
