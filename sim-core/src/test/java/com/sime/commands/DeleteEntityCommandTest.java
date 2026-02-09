package com.sime.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.sime.components.Component;
import com.sime.components.TestComponent;
import com.sime.world.World;

public class DeleteEntityCommandTest {
    
    private static int id;    

    @Test
    public void test_delete() {
        var world = world();
        var cmd = new DeleteEntityCommand(id);

        cmd.apply(world);

        var remainingIds = world.getEntityManager().activeEntities();
        assertEquals(remainingIds.size(), 0);
        assertFalse(world.getComponentRegistry().has(id, TestComponent.class));
    }

    @Test
    public void test_delete_multiple_components() {
        var world = world();
        world.getComponentRegistry().put(id, new TestComponent1());
        var cmd = new DeleteEntityCommand(id);

        cmd.apply(world);

        var remainingIds = world.getEntityManager().activeEntities();
        assertEquals(remainingIds.size(), 0);
        assertFalse(world.getComponentRegistry().has(id, TestComponent.class));
        assertFalse(world.getComponentRegistry().has(id, TestComponent1.class));
    }

    private static World world() {
        var w = new World(null);
        id =  w.getEntityManager().create();
        w.getComponentRegistry().put(id, new TestComponent("Test1"));

        return w;
    }

    private record TestComponent1() implements Component {}
}
