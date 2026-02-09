package com.sime.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vector2 implements Component {

    @JsonProperty
    private final double x;

    @JsonProperty
    private final double y;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Vector2(
        @JsonProperty("x") double x, 
        @JsonProperty("y") double y) {
        this.x = x;
        this.y = y;
    }

    
    public Vector2(double[] values) {
        this.x = values[0];
        this.y = values[1];
    }

    @JsonGetter
    public double x() {
        return this.x;
    }

    @JsonGetter
    public double y() {
        return this.y;
    }
    
    public double[] toArray() {
        return new double[]{this.x, this.y};
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector2 unitVector() {
        var mag = magnitude();
        return new Vector2(x/mag, y/mag);
    }

    public Vector2 scalarMultiply(double s) {
        return new Vector2(s*x, s*y);
    }

    public Vector2 add(Vector2 other) {
        var newX = x() + other.x();
        var newY = y() + other.y();
        return new Vector2(newX, newY);
    }


    public double dot(Vector2 other) {
        return x() * other.x() + y() * other.y();
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(
            x() - other.x(), 
            y() - other.y());
    }
}
