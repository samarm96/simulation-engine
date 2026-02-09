package com.sime.gui.shapes;

import java.nio.DoubleBuffer;

public interface Mesh2D {
    
    public void upload(DoubleBuffer buffer);

    public void draw(int glPrimitive, int vertexCount);

    public void clear();

    public void delete();


}
