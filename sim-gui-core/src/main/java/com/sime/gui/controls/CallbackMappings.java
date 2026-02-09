package com.sime.gui.controls;

import com.sime.gui.camera.Camera2D;

public interface CallbackMappings {

    public void mouseCallback(Camera2D camera);

    public void keyCallback(Camera2D camera);

    public void init(Camera2D camera);

    public double timeScale(); 

    public int follow();
}
