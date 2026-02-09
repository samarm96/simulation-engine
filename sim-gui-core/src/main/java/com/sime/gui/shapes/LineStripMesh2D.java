package com.sime.gui.shapes;

import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public final class LineStripMesh2D extends BasicMesh2D {

    private int maxVertices;

    public LineStripMesh2D(int initialMaxVertices) {
        this.maxVertices = Math.max(2, initialMaxVertices);

        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        // allocate initial size (2 doubles per vertex)
        glBufferData(GL_ARRAY_BUFFER, (long)maxVertices * 3L * Double.BYTES, GL_DYNAMIC_DRAW);

        int stride = 3 * Double.BYTES;

        // location 0: vec2 position
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_DOUBLE, false, stride, 0);

        // location 1: double t
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 1, GL_DOUBLE, false, stride, 2L * Double.BYTES);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    /** Uploads XY points. vertexCount is number of points (not double). */
    public void upload(double[] xyt, int vertexCount) {
        ensureCapacity(vertexCount);

        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(vertexCount * 3);
        buffer.put(xyt, 0, vertexCount * 3);

        upload(buffer);
    }

    public void draw(int vertexCount) {
        draw(GL_LINE_STRIP, vertexCount);
    }

    private void ensureCapacity(int vertexCount) {
        if (vertexCount <= maxVertices) {
            return;
        }
        maxVertices = Math.max(vertexCount, maxVertices * 2);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (long)maxVertices * 3L * Double.BYTES, GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
