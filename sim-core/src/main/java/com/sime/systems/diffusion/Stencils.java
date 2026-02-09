package com.sime.systems.diffusion;

public final class Stencils {
    
  public static Stencil laplacian5() {
    return (f, x, y, b) -> {
      double c = b.sample(f, x, y);
      double r = b.sample(f, x + 1, y);
      double l = b.sample(f, x - 1, y);
      double u = b.sample(f, x, y - 1);
      double d = b.sample(f, x, y + 1);
      return (r + l + u + d - 4.0 * c);
    };
  }
}

