package com.sime.spatial;

public final class DefaultOutOfBoundsRule {

  public static OutOfBoundsRule clamp() {
    return (f, x, y) -> f.get(
      clamp(x, 0, f.columns()-1), 
      clamp(y, 0, f.rows()-1));
  }

  public static OutOfBoundsRule wrap() {
    return (f, x, y) -> f.get(
      mod(x, f.columns()), 
      mod(y, f.rows()));
  }

  private static int clamp(int v, int lo, int hi) { 
    return Math.max(lo, Math.min(hi, v)); 
  }

  private static int mod(int v, int m) { 
    int r = v % m; 
    
    return r < 0 ? r + m : r; 
  }
}
