package com.sime.commands;

import java.util.List;

import com.sime.components.Component;
import com.sime.world.World;

public class CreateEntityCommand extends WorldCommand {

  private final List<Component> components;

  public CreateEntityCommand(List<Component> components) { 
    super(-1); 
    this.components = components; 
  }

  public CreateEntityCommand(Component component) { 
    super(-1); 
    this.components = List.of(component); 
  }

  @Override 
  public void apply(World w) {
    int childId = w.getEntityManager().create();
    
    components.forEach(component -> w.getComponentRegistry().put(childId, component));
  }
}
