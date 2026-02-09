package com.sime.systems;

import java.util.List;

import com.sime.commands.CommandBuffer;
import com.sime.components.Component;
import com.sime.spatial.OutOfBoundsRule;
import com.sime.spatial.ScalarField2D;
import com.sime.systems.diffusion.Stencil;
import com.sime.world.World;

public class DiffusionSystem extends SimSystem {

  private final String fieldName;
  private final double D;
  private final Stencil stencil;
  private final OutOfBoundsRule boundary;

  public DiffusionSystem(String fieldName, double D, Stencil stencil, OutOfBoundsRule boundary) {
    this.fieldName = fieldName;
    this.D = D;
    this.stencil = stencil;
    this.boundary = boundary;
  }

    @Override
    public void apply(World w, CommandBuffer queue, double dt) {
        ScalarField2D field = w.getFieldStore().scalar2D(fieldName);

        field.copyReadToWrite();

        for (int y = 0; y < field.rows(); y++) {
            for (int x = 0; x < field.columns(); x++) {
                double c = field.sample(x, y);
                double lap = stencil.apply(field, x, y, boundary);
                field.set(x, y, c + D * lap);
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
