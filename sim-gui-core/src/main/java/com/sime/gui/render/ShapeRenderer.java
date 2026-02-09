package com.sime.gui.render;

import com.sime.gui.camera.Camera2D;

public interface ShapeRenderer {
    
    public void begin(Camera2D camera);

    public void color(float r, float g, float b, float a);

    public void draw(DrawableParameters parameters);

    public void delete();

}
