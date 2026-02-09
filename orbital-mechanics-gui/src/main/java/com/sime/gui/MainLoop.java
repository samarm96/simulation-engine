package com.sime.gui;

import com.sime.gui.camera.Camera2D;
import com.sime.gui.controls.GravityCallbackMappings;
import com.sime.gui.render.LineShaders;
import com.sime.gui.render.OrbitTrailRenderer;
import com.sime.gui.render.PlanetRenderer;
import com.sime.gui.render.Renderer;
import com.sime.gui.shaders.ShaderProgram;
import com.sime.gui.shaders.Shaders;
import com.sime.io.ClassPathSource;
import com.sime.io.JsonIO;
import com.sime.viewer.DefaultMultiBodyDriver;
import com.sime.viewer.DefaultTwoBodyDriver;
import com.sime.viewer.OrbitSimSpec;
import com.sime.viewer.OrbitalMechanicsSimDriver;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.List;

public final class MainLoop {
    public static void main(String[] args) {
        glfwInit();

        // Build window
        var viewportScale = 100;
        var windowBuilder = new WindowBuilder();
        var window = windowBuilder
            .withHeight(1000)
            .withWidth(1000)
            .withTitle("Orbital Mechanics GUI")
            .withViewportScale(viewportScale)
            .build();

        windowBuilder.program();

        var camera = new Camera2D();

        // Initial viewport size
        var fbW = new int[1];
        var fbH = new int[1];
        glfwGetFramebufferSize(window, fbW, fbH);
        glViewport(0, 0, fbW[0], fbH[0]);
        camera.setViewport(fbW[0]*viewportScale, fbH[0]*viewportScale);

        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            glViewport(0, 0, w, h);
            camera.setViewport(w*viewportScale, h*viewportScale);
        });

        new GuiDriverBuilder()
            .withWindow(window)
            .withCallbackMappings(new GravityCallbackMappings(window))
            .withCamera(camera)
            .withSimDriver(simDriver())
            .withRenderers(renderers())
            .build()
            .run();

    }

    private static OrbitalMechanicsSimDriver simDriver() {
        
        var source = new ClassPathSource<OrbitSimSpec>(
            new JsonIO(),
            "Spec.json", 
            OrbitSimSpec.class);

        var driver = new DefaultMultiBodyDriver(source, 1800);
        driver.init();
        return driver;
    }

    private static List<Renderer> renderers() {
        var renderScale = 1/1.495979e8;
        var bigMass = 1.989e30; // sun's mass
        return List.of(
                new PlanetRenderer(
                    new ShaderProgram(Shaders.VERTEX, Shaders.FRAGMENT),
                    renderScale,
                    bigMass),
                new OrbitTrailRenderer(
                    new ShaderProgram(LineShaders.VERTEX, LineShaders.FRAGMENT),
                    50000,
                    renderScale));
    }
}
