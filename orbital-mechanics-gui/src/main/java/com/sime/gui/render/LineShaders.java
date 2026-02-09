package com.sime.gui.render;

public final class LineShaders {

    private LineShaders(){}

    public static final String VERTEX = """
        #version 330 core
        layout (location = 0) in vec2 aPos;
        layout(location = 1) in float aT;

        out float vT;

        uniform mat4 uWorldToClip;
        uniform mat4 uModel;

        void main() {
            vT = aT;
            gl_Position = uWorldToClip * uModel * vec4(aPos, 0.0, 1.0);
        }
        """;

    public static final String FRAGMENT = """
        #version 330 core
        out vec4 FragColor;

        in float vT;

        uniform vec4 uColor;
        uniform float uTailPower;

        void main() {
            float a = pow(vT, uTailPower);
            vec3 rgb = uColor.rgb;
            FragColor = vec4(rgb, uColor.a * a);
        }
        """;
}        
