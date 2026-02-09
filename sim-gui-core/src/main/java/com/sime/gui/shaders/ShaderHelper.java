package com.sime.gui.shaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderHelper {

public static int compileShader(int type, String src) {
    int id = glCreateShader(type);
    glShaderSource(id, src);
    glCompileShader(id);
    if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
        throw new IllegalStateException(glGetShaderInfoLog(id));
    }
    return id;
}

public static int createProgram() {
    String vs = """
        #version 150 core
        in vec2 aPos;
        void main() {
            gl_Position = vec4(aPos, 0.0, 1.0);
        }
        """;
    String fs = """
        #version 150 core
        out vec4 color;
        void main() {
            color = vec4(1.0, 1.0, 1.0, 1.0);
        }
        """;

    int v = compileShader(GL_VERTEX_SHADER, vs);
    int f = compileShader(GL_FRAGMENT_SHADER, fs);

    int p = glCreateProgram();
    glAttachShader(p, v);
    glAttachShader(p, f);

    // Bind attribute location 0 to "aPos" (must match how we set vertex attrib pointers)
    glBindAttribLocation(p, 0, "aPos");

    glLinkProgram(p);
    if (glGetProgrami(p, GL_LINK_STATUS) == GL_FALSE) {
        throw new IllegalStateException(glGetProgramInfoLog(p));
    }

    glDeleteShader(v);
    glDeleteShader(f);
    return p;
}

}
