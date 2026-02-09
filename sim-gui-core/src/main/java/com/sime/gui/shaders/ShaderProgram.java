package com.sime.gui.shaders;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public final class ShaderProgram {

    private final int programId;
    private final Map<String, Integer> uniformCache = new HashMap<>();

    public ShaderProgram(String vertexSrc, String fragmentSrc) {
        
        int vs = compile(GL_VERTEX_SHADER, vertexSrc);
        int fs = compile(GL_FRAGMENT_SHADER, fragmentSrc);

        programId = glCreateProgram();
        glAttachShader(programId, vs);
        glAttachShader(programId, fs);
        glLinkProgram(programId);

        int linked = glGetProgrami(programId, GL_LINK_STATUS);
        if (linked == 0) {
            String log = glGetProgramInfoLog(programId);
            glDeleteProgram(programId);
            glDeleteShader(vs);
            glDeleteShader(fs);
            throw new IllegalStateException("Shader link failed:\n" + log);
        }

        // Shaders can be detached/deleted after linking
        glDetachShader(programId, vs);
        glDetachShader(programId, fs);
        glDeleteShader(vs);
        glDeleteShader(fs);
    }

    public void use() {
        glUseProgram(programId);
    }

    public int id() {
        return programId;
    }

    public void delete() {
        glDeleteProgram(programId);
    }

    public void setMat4(String name, float[] colMajor16) {
        int loc = uniformLocation(name);
        glUniformMatrix4fv(loc, false, colMajor16);
    }

    public void setVec4(String name, float r, float g, float b, float a) {
        int loc = uniformLocation(name);
        glUniform4f(loc, r, g, b, a);
    }

    private int uniformLocation(String name) {
        return uniformCache.computeIfAbsent(name, n -> {
            int loc = glGetUniformLocation(programId, n);
            if (loc < 0) throw new IllegalStateException("Uniform not found: " + n);
            return loc;
        });
    }

    private static int compile(int type, String src) {
        int shader = glCreateShader(type);
        glShaderSource(shader, src);
        glCompileShader(shader);

        int ok = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (ok == 0) {
            String log = glGetShaderInfoLog(shader);
            glDeleteShader(shader);
            throw new IllegalStateException("Shader compile failed:\n" + log);
        }
        return shader;
    }
}
