package com.sime.reporting;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.sime.components.Component;
import com.sime.world.World;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

public final class EntityDumpObserver implements Observer {

  private final int everyNSteps;

  private final ObjectMapper mapper;
  private final ObjectWriter writer; 

  private final Path runDir;

  public EntityDumpObserver(int everyNSteps) {
    this.everyNSteps = Math.max(1, everyNSteps);
    this.mapper = new ObjectMapper();
    this.writer = mapper.writerWithDefaultPrettyPrinter();
    this.runDir = Paths.get("evolution-sim", "logs", "run");
  }

  @Override
  public void onInit(World world) {
    try {
      deleteDirectoryContents(runDir);   // deletes previous run on startup
      Files.createDirectories(runDir);
    } catch (IOException e) {
      throw new RuntimeException("Failed to initialize log directory: " + runDir, e);
    }
  }

  @Override
  public void onStep(World world, long step) {
    if (step % everyNSteps != 0) {
      return;
    }

    try {

      Path outFile = runDir.resolve(String.format("Step%06d.json", step));
      Files.createFile(outFile);

      var output = organismTraits(world);

      writer.writeValue(outFile.toFile(), output);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onFinish(World world) {}

  private List<AllOrganismTraits> organismTraits(World world) {
    var em = world.getEntityManager();
    var cr = world.getComponentRegistry();

    List<AllOrganismTraits> entityTraits = new ArrayList<>();
    for (int id : em.activeEntities()) {

      // Build component state list
      var componentTypes = cr.listEntityComponents(id);

      List<Component> components = new ArrayList<>(componentTypes.size());
      for (var type : componentTypes) {
        components.add(cr.get(id, type));
      }
        
      entityTraits.add(new AllOrganismTraits(id, components)); 
    }

    return entityTraits;
  }

  private static void deleteDirectoryContents(Path dir) throws IOException {
    if (!Files.exists(dir)) return;

    // Delete everything under dir, but keep dir itself
    try (var walk = Files.walk(dir)) {
      walk
        .sorted(Comparator.reverseOrder()) // children first
        .filter(p -> !p.equals(dir))
        .forEach(p -> {
          try {
            Files.deleteIfExists(p);
          } catch (IOException e) {
            throw new RuntimeException("Failed to delete: " + p, e);
          }
        });
    }
  }
}
