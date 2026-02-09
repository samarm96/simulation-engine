package com.sime.gui.render;

import com.sime.gui.shaders.ShaderProgram;
import com.sime.gui.shaders.Shaders;

public class DefaultRenderers implements Renderers {

    @Override
    public LineRenderer2D lineRenderer() {
        var shaders = new ShaderProgram(LineShaders.VERTEX, LineShaders.FRAGMENT);
        return new LineRenderer2D(shaders);
    }

    @Override
    public CircleRenderer2D circleRenderer2D() {
        var shaders = new ShaderProgram(Shaders.VERTEX, Shaders.FRAGMENT);
        return new CircleRenderer2D(shaders);
    }
    
}
