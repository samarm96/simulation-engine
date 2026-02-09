package com.sime.components;

public class ScalarField2DComponent extends Grid2D {
    
    private double[][] read;
    private double[][] write;

    public ScalarField2DComponent(int w, int h) {
        super(w, h);

        this.read = new double[width][height];
        this.write = new double[width][height];
    }

    // READ
    public double get(int x, int y) {
        checkInput(x, y);
        
        return read[y][x];
    }

    // WRITE
    public void set(int x, int y, double value) {
        checkInput(x, y);
        write[y][x] = value;
    }

    public void add(int x, int y, double delta) {
        checkInput(x, y);
        write[y][x] += delta;
    }

    /** Usually called at the start of a dense update step. */
    public void copyReadToWrite() {
        for (int y = 0; y < height; y++) {
            System.arraycopy(read[y], 0, write[y], 0, width);
        }
    }

    /** Commit a full dense update. */
    public void swap() {
        var tmp = read;
        read = write;
        write = tmp;
    }

    public void fill(double value) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                read[y][x] = value;
                write[y][x] = value;
            }
        }
    }

    private void checkInput(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException("x=" + x + ", y=" + y + " out of bounds");
        }
    }
}
