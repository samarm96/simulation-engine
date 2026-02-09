package com.sime.gui;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.sime.gui.shaders.ShaderHelper;

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL20.*;

public class WindowBuilder {

    private int program;

    private int width;
    private int height;
    private String title;
    private int viewPortScale;

    public WindowBuilder() {
        withDefaults();
    }

    public int program() {
        return program;
    }

    public WindowBuilder withDefaults() {
        withTitle("New Window");
        withWidth(800);
        withHeight(600);
        withViewportScale(1);

        return this;
    }

    public WindowBuilder withWidth(int value) {
        this.width = value;
        return this;
    }

    public WindowBuilder withHeight(int value) {
        this.height = value;
        return this;
    }

    public WindowBuilder withViewportScale(int value) {
        this.viewPortScale = value;
        return this;
    }

    public WindowBuilder withTitle(String value) {
        this.title = value;
        return this;
    }

    public long build() {
        return createWindow(width, height, title, viewPortScale);
    }


    private long createWindow(int width, int height, String title, int viewPortScale) {
        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Request an OpenGL 3.2 Core profile context (macOS-friendly baseline)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE); // required on macOS

        long window = glfwCreateWindow(width, height, title, NULL, NULL);

        if (window == NULL) {
            throw new IllegalStateException("Failed to create window");
        }

        // set viewport size!
        glfwSetFramebufferSizeCallback(window, (win, w, h) -> glViewport(0, 0, w * viewPortScale, h * viewPortScale));
        
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // vsync (optional)
        createCapabilities();
        System.out.println("GL Vendor  : " + glGetString(GL_VENDOR));
        System.out.println("GL Renderer: " + glGetString(GL_RENDERER));
        System.out.println("GL Version : " + glGetString(GL_VERSION));
        program = ShaderHelper.createProgram();
        glUseProgram(program);

        glfwShowWindow(window);
        return window;
    }
}
