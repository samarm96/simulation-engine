package com.sime.reporting;

import com.sime.world.World;

public interface Observer {
  void onInit(World world);
  void onStep(World world, long step);   
  void onFinish(World world);
}
