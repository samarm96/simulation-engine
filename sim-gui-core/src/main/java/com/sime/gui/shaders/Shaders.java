package com.sime.gui.shaders;

public final class Shaders {
    private Shaders() {}

    public static final String VERTEX = """
        #version 330 core
        layout (location = 0) in vec2 aPos;

        uniform mat4 uWorldToClip;
        uniform mat4 uModel;

        void main() {
            gl_Position = uWorldToClip * uModel * vec4(aPos, 0.0, 1.0);
        }
        """;

    public static final String FRAGMENT = """
        #version 330 core
        out vec4 FragColor;

        uniform vec4 uColor;

        void main() {
            FragColor = uColor;
        }
        """;
}
