package com.sime.reporting;

import com.sime.world.World;

public class FieldObserver implements Observer {
    
    private final String fieldName;

    public FieldObserver(String fieldName) {
        this.fieldName = fieldName;
    }


    @Override
    public void onInit(World world) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onInit'");
    }

    @Override
    public void onStep(World world, long step) {
        var field = world.getFieldStore().scalar2D(fieldName);
        var rows = field.rows();
        var columns = field.columns();
        
        System.out.println("----------------------");
        for(int y = 0; y < rows; y++) {
            for(int x = 0; x < columns; x++) {
                var value = field.get(x, y);
                System.out.println("(" + x + ", " + y + "): " + value);
            }
        }

    }

    @Override
    public void onFinish(World world) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onFinish'");
    }
    
}
