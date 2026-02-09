package com.sime.spatial;

@FunctionalInterface
public interface OutOfBoundsRule {
  
  double sample(ScalarField2D f, int x, int y);
}
