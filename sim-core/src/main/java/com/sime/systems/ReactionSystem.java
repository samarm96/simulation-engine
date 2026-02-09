package com.sime.systems;

import java.util.List;

import com.sime.commands.CommandBuffer;
import com.sime.components.Component;
import com.sime.spatial.ScalarField2D;
import com.sime.systems.reaction.Reaction;
import com.sime.world.World;

public class ReactionSystem extends SimSystem {

  private final String fieldName;
  private final Reaction reaction;

  public ReactionSystem(String fieldName, Reaction reaction) {
    this.fieldName = fieldName;
    this.reaction = reaction;
  }

  @Override
  public void apply(World w, CommandBuffer queue, double dt) {
    ScalarField2D field = w.getFieldStore().scalar2D(fieldName);

    field.copyReadToWrite();

    for (int y = 0; y < field.rows(); y++) {
      for (int x = 0; x < field.columns(); x++) {
          double u = field.get(x, y);
          double du = reaction.duDt(u);
          field.set(x, y, u + dt * du);
      }
    }

    field.swap();
  }
    
  @Override
  protected List<Class<? extends Component>> requiredComponents() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'requiredComponents'");
  }
    
}
