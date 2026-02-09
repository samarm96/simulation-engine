package com.sime.gui.shapes;

public class tmep {
    private static final double G = 6.674e-11;
    private static final double mu = 1.32712440041279419e20;
    private static final double M_PER_AU = 149597870700D;

    public static void main(String[] args) {
        var sma = 778.57e9;


        var v = Math.sqrt(mu/sma);

        System.out.println("Semi-Major Axis: " + sma);
        System.out.println("Velocity: " + v);
    }
}
