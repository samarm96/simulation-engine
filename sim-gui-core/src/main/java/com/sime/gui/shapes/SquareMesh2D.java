package com.sime.gui.shapes;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.GL_DOUBLE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public final class SquareMesh2D extends BasicMesh2D {

    private static final int VERTEX_COUNT = 6;

    public SquareMesh2D() {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(VERTEX_COUNT * 2);
        
        // Two triangles for a centered unit square (-0.5,-0.5) to (0.5,0.5)
        buffer
            .put(-0.5).put(-0.5)
            .put(0.5).put(-0.5)
            .put(0.5).put(0.5)
            .put(-0.5).put(-0.5)
            .put(0.5).put(0.5)
            .put(-0.5).put(0.5)
            .flip();

        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_DOUBLE, false, 2 * Double.BYTES, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
    }

    public void upload() {


        upload(buffer);
    }


    public void draw() {
        draw(GL_TRIANGLES, VERTEX_COUNT);
    }
}
