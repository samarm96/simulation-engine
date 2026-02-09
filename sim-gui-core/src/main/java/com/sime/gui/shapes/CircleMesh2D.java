package com.sime.gui.shapes;

import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public final class CircleMesh2D extends BasicMesh2D {

    private int segments;

    public CircleMesh2D(int segments) {
        this.segments = Math.max(8, segments);

        vertexCount = 1 + (this.segments + 1);

        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        // Allocate storage once (STATIC since geometry never changes)
        glBufferData(GL_ARRAY_BUFFER, (long)vertexCount * 2L * Double.BYTES, GL_STATIC_DRAW);

        // location 0: vec2 position
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_DOUBLE, false, 2 * Double.BYTES, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void draw() {
        draw(GL_TRIANGLE_FAN, vertexCount);
    }

    public void upload() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(vertexCount * 2);

        buffer.put(0).put(0); // center

        for (int i = 0; i <= segments; i++) {
            double a = (2.0 * Math.PI * i) / segments;
            buffer.put(Math.cos(a));
            buffer.put(Math.sin(a));
        }

        upload(buffer);
    }

    public double[] circle(double cx, double cy, double r) {
        double[] xy = new double[vertexCount * 2];
        for (int i = 0; i < vertexCount; i++) {
            double a = (2.0 * Math.PI * i) / vertexCount;
            xy[2*i]     = cx + r * Math.cos(a);
            xy[2*i + 1] = cy + r * Math.sin(a);
        }
        return xy;
    }
}
