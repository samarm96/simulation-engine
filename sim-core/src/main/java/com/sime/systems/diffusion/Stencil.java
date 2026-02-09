package com.sime.systems.diffusion;

import com.sime.spatial.OutOfBoundsRule;
import com.sime.spatial.ScalarField2D;

@FunctionalInterface
public interface Stencil {
  double apply(ScalarField2D f, int x, int y, OutOfBoundsRule b);
}

