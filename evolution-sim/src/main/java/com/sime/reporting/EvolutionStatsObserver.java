package com.sime.reporting;

import java.util.ArrayList;
import java.util.List;

import com.sime.components.ReproductiveTraits;
import com.sime.world.World;

public final class EvolutionStatsObserver implements Observer {

  private final int everyNSteps;
  private final List<EvolutionStats> history = new ArrayList<>();
  private final long seed;

  public EvolutionStatsObserver(int everyNSteps, long seed) {
    this.everyNSteps = Math.max(1, everyNSteps);
    this.seed = seed;
  }

  @Override
  public void onInit(World world) {}

  @Override
  public void onStep(World world, long step) {
    if (step % everyNSteps != 0) return;

    var em = world.getEntityManager();
    var cr = world.getComponentRegistry();

    int n = 0;
    double sumRep = 0, sumDeath = 0;
    double sumRep2 = 0, sumDeath2 = 0;

    for (int id : em.activeEntities()) {
      var t = cr.get(id, ReproductiveTraits.class);
      if (t == null) continue;

      double r = t.replicationChance();

      n++;
      sumRep += r; 
      sumRep2 += r * r; 
    }

    double meanRep = n == 0 ? 0 : sumRep / n;
    double meanDeath = n == 0 ? 0 : sumDeath / n;
    double varRep = n == 0 ? 0 : (sumRep2 / n) - (meanRep * meanRep);
    double varDeath = n == 0 ? 0 : (sumDeath2 / n) - (meanDeath * meanDeath);

    history.add(new EvolutionStats(step, n, meanRep, meanDeath, varRep, varDeath, this.seed));
  }

  @Override
  public void onFinish(World world) {
  }

  public List<EvolutionStats> history() {
    return List.copyOf(history);
  }

  public record EvolutionStats(
    long step,
    int population,
    double meanRep,
    double meanDeath,
    double varRep,
    double varDeath,
    long seed) {}

}
