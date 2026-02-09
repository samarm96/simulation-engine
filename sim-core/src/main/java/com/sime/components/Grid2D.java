package com.sime.components;

public class Grid2D implements Component {
    
    protected int width;
    protected int height;

    public Grid2D(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public int area() {
        return width*height;
    }

    public void setWidth(int newValue) {
        this.width = newValue;
    }

    public void setHeight(int newValue) {
        this.height = newValue;
    }

}
