package com.sime.scenario;

import java.util.List;
import java.util.Random;

import com.sime.Simulation;
import com.sime.commands.CommandBuffer;
import com.sime.commands.CreateEntityCommand;
import com.sime.components.Component;
import com.sime.models.DefaultBioModel;
import com.sime.reporting.EntityDumpObserver;
import com.sime.reporting.Observer;
import com.sime.spatial.DefaultOutOfBoundsRule;
import com.sime.systems.DiffusionSystem;
import com.sime.systems.SoftMaxForagingSystem;
import com.sime.systems.OrganismLifeCycleSystem;
import com.sime.systems.ReactionSystem;
import com.sime.systems.SimSystem;
import com.sime.systems.diffusion.Stencils;
import com.sime.systems.reaction.Reactions;
import com.sime.temporal.FixedStepSimulationClock;
import com.sime.world.World;

public class EvolutionScenario implements Scenario {

  private final int founderCount;
  private final List<Component> components;
  private final long seed;

  public EvolutionScenario(int founderCount, List<Component> components, long seed) {
    this.founderCount = founderCount;
    this.components = components;
    this.seed = seed;
  }

  @Override
  public void initialize(CommandBuffer out) {
    for (int i = 0; i < founderCount; i++) {
      out.add(new CreateEntityCommand(components));
    }
  }

  public Simulation sim() {
    var model = new DefaultBioModel();
    var world = new World(model);
    initializeNutrientField(world);
    var buffer = new CommandBuffer();
    var rng = new Random(seed);
    var systems = systems(rng);
    var observers = observers();
    
    var sim = new Simulation(
      world, 
      systems, 
      buffer, 
      observers,
      new FixedStepSimulationClock());
    
    sim.initialize(this);
    return sim;
  }
    

  private List<? extends SimSystem> systems(Random rng) {
    return List.of(
      reactionSystem(),
      diffusionSystem(),
      new SoftMaxForagingSystem(rng),
      new OrganismLifeCycleSystem(rng));
  }

  private DiffusionSystem diffusionSystem() {
    return new DiffusionSystem(
      "nutrients",
      0.05,
      Stencils.laplacian5(),
      DefaultOutOfBoundsRule.clamp());
  }

  private ReactionSystem reactionSystem() {
    var reactions = Reactions.sum(
      Reactions.regrowToMax(0.01, 9),
      Reactions.decay(0.005));

    return new ReactionSystem("nutrients", reactions);
  }

  private void initializeNutrientField(World world) {
        var fieldName = "nutrients";
        world.getFieldStore().createScalar2D(fieldName, 3, 3, DefaultOutOfBoundsRule.clamp());
        world.getFieldStore().scalar2D(fieldName).fill(2);
        world.getFieldStore().scalar2D(fieldName).swap();
        world.getFieldStore().scalar2D(fieldName).copyReadToWrite();
  }

  private List<? extends Observer> observers() {
    return List.of(
      new EntityDumpObserver(1));
  }

}
