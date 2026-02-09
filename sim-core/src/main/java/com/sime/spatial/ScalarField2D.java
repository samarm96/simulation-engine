package com.sime.spatial;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ScalarField2D {

    private final int rows;
    private final int columns;
    private final OutOfBoundsRule boundaryCondition;

    private double[][] read;
    private double[][] write;

    public ScalarField2D(int rows, int columns, OutOfBoundsRule boundaryCondition) {
        this.rows = rows;
        this.columns = columns;
        this.boundaryCondition = boundaryCondition;

        this.read = new double[rows][columns];
        this.write = new double[rows][columns];
    }

    public int rows() { return rows; }
    public int columns() { return columns; }

    public double sample(int x, int y) {
        return boundaryCondition.sample(this, x, y);
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
        for (int y = 0; y < rows; y++) {
            System.arraycopy(read[y], 0, write[y], 0, columns);
        }
    }

    /** Commit a full dense update. */
    public void swap() {
        var tmp = read;
        read = write;
        write = tmp;
    }

    public void fill(double value) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                read[y][x] = value;
                write[y][x] = value;
            }
        }
    }

    private void checkInput(int x, int y) {
        if (x < 0 || x >= columns || y < 0 || y >= rows) {
            throw new IndexOutOfBoundsException("x=" + x + ", y=" + y + " out of bounds");
        }
    }
}
