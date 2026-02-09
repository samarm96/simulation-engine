package com.sime.gui.controls;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWScrollCallback;

import com.sime.gui.camera.Camera2D;

public class GravityCallbackMappings implements CallbackMappings {

    private long window;
    private boolean panning;
    private int follow;

    private double lastMx = 0;
    private double lastMy = 0;
    private double timeScale = 86400.0; 

    public GravityCallbackMappings(long window) {
        this.window = window;
    }

    public void init(Camera2D camera) {
        keyCallback(camera);
        scrollCallback(camera);
        mouseCallback(camera);
    }

    @Override
    public void mouseCallback(Camera2D camera) {
        glfwSetMouseButtonCallback(window, (long win, int button, int action, int mods) -> {
            // Pan with right mouse button drag
            if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
                double[] mx = new double[1];
                double[] my = new double[1];
                glfwGetCursorPos(window, mx, my);

                if (!panning) {
                    this.panning = true;
                    this.lastMx = mx[0];
                    this.lastMy = my[0];
                } else {
                    float dx = (float)(mx[0] - this.lastMx);
                    float dy = (float)(my[0] - this.lastMy);
                    camera.panPixels(dx, dy);
                    this.lastMx = mx[0];
                    this.lastMy = my[0];
                }
            } else {
                this.panning = false;
            }
        });
    }

    @Override
    public void keyCallback(Camera2D camera) {
        glfwSetKeyCallback(window, (long window, int key, int scancode, int action, int mods) -> {
                    
            var delta = 30f;

            switch (key) {
                // ------ CAMERA PANNING -------
                case GLFW_KEY_W -> {
                    camera.panPixels(0, delta);
                    break;
                }
                case GLFW_KEY_A -> {
                    camera.panPixels(delta, 0);
                    break;
                }
                case GLFW_KEY_S -> {
                    camera.panPixels(0, -delta);
                    break;
                }  
                case GLFW_KEY_D -> {
                    camera.panPixels(-delta, 0);
                    break;
                }
                // ------ CAMERA CENTERED ON: -------
                case GLFW_KEY_F -> {
                    follow = 1;
                    System.out.println("Follow: " + "BODY 1");
                }
                case GLFW_KEY_G -> {
                    follow = 2;
                    System.out.println("Follow: " + "BODY 2");
                }
                case GLFW_KEY_H -> {
                    follow = 2;
                    System.out.println("Follow: " + "MIDWAY POINT");
                }
                // ------ TIMESCALE WARP -------
                case GLFW_KEY_EQUAL -> {
                    timeScale *= 1.02;
                    System.out.println("Timescale: " + timeScale);
                }
                case GLFW_KEY_MINUS -> {
                    timeScale /= 1.02;
                    System.out.println("Timescale: " + timeScale);
                }
                case GLFW_KEY_0 -> {
                    timeScale = 1.0;
                    System.out.println("Timescale: " + timeScale);
                }
            }
        });
    }

    public void scrollCallback(Camera2D camera) {
        glfwSetScrollCallback(window, new GLFWScrollCallback() {
            @Override public void invoke(long win, double xoff, double yoff) {
                if (yoff > 0) camera.zoomBy(1.1f);
                else if (yoff < 0) camera.zoomBy(0.9f);
            }
        });
    }

    public int follow() {
        return this.follow;
    }

    public double timeScale() {
        return this.timeScale;
    }

    public boolean panning() {
        return this.panning;
    }

    public double lastMx() {
        return this.lastMx;
    }

    public double lastMy() {
        return this.lastMy;
    }
}
