package com.sime.gui.controls;

import static org.lwjgl.glfw.GLFW.*;

import com.sime.gui.camera.Camera2D;
import com.sime.gui.shapes.BasicMesh2D;

public class DefaultCallbackMappings implements CallbackMappings {
    private long window;

    public DefaultCallbackMappings(long window) {
        this.window = window;
    }

    public void mouseCallback() {
        glfwSetMouseButtonCallback(window, (long win, int button, int action, int mods) -> {
            /* We also don't do anything here. */
        });
    }


    
    public void keyCallback(BasicMesh2D entity) {
        glfwSetKeyCallback(window, (long window, int key, int scancode, int action, int mods) -> {

            switch (key) {
                case GLFW_KEY_W -> {
                    entity.translate(0, 0.01f);
                    break;
                    }
                case GLFW_KEY_A -> {
                    entity.translate(-0.01f, 0);
                    break;
                }
                    
                case GLFW_KEY_S -> {
                    entity.translate(0, -0.01f);
                    break;
                }
                    
                case GLFW_KEY_D -> {
                    entity.translate(0.01f, 0);
                    break;
                }
                    
                default -> {break;}
            }});
    }

    @Override
    public void mouseCallback(Camera2D camera) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseCallback'");
    }

    @Override
    public void keyCallback(Camera2D camera) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyCallback'");
    }

    @Override
    public void init(Camera2D camera) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    @Override
    public double timeScale() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'timeScale'");
    }

    @Override
    public int follow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'follow'");
    }

}
