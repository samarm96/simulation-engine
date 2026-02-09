package com.sime.viewer;

import java.util.List;

public record OrbitSimSpec(
    List<Planet> planets) {
    


    // later:
    // Optional<IntegratorSpec> integrator();
    // Optional<Double> dt();
    // Optional<String> name();
}
