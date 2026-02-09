package com.sime.gui;

import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import java.util.List;

import com.sime.gui.camera.Camera2D;
import com.sime.gui.controls.CallbackMappings;
import com.sime.gui.render.FrameContext;
import com.sime.viewer.SimDriver;

import com.sime.gui.render.Renderer;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

public class GuiDriver {
    
    private Camera2D camera;
    private long window;
    private SimDriver simDriver;
    private CallbackMappings mappings;
    private List<Renderer> renderers;

    public GuiDriver(
        long window, 
        Camera2D camera, 
        SimDriver simDriver,
        CallbackMappings mappings,
        List<Renderer> renderers) {
        
        this.window = window;
        this.camera = camera;
        this.simDriver = simDriver;
        this.mappings = mappings;
        this.renderers = renderers;
    }

    public void run() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        // basic clear color
        glClearColor(0.08f, 0.08f, 0.10f, 1.0f);

        // Set key mappings
        mappings.init(camera);        

        // set up time
        var accumulator = 0.0;
        var lastTime = glfwGetTime();
        var maxStepsPerFrame = 20_000; // safety cap to avoid freezing

        while (!glfwWindowShouldClose(window)) {
            var now = glfwGetTime();
            var realDt = now - lastTime;
            lastTime = now;

            // avoid spiral of death when debugging / dragging windows
            realDt = Math.min(realDt, 0.25);

            // accumulate SIMULATED time
            accumulator += realDt * mappings.timeScale();

            glfwPollEvents();

            // drive the sim
            double simDt = simDriver.simStepSize(); // e.g. 1800 seconds
            int steps = (int) Math.floor(accumulator / simDt);
            steps = Math.min(steps, maxStepsPerFrame);

            if (steps > 0) {
                simDriver.stepMultiple(steps);
                accumulator -= steps * simDt;
            }

            // render bodies
            var snapshot = simDriver.snapshot();

            var fc = new FrameContext(camera, now, realDt, snapshot, mappings);
            renderers.forEach(r -> r.render(fc));
            
            glfwSwapBuffers(window);
        }

		// Free the window callbacks and destroy the window        
        renderers.forEach(r -> r.delete());
        glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
    }



}
