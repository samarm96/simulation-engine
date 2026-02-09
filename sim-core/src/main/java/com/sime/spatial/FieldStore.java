package com.sime.spatial;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public final class FieldStore {

    private final Map<String, ScalarField2D> scalar2D = new HashMap<>();

    public ScalarField2D createScalar2D(String name, int rows, int cols, OutOfBoundsRule oobr) {
        if (scalar2D.containsKey(name)) {
            throw new IllegalArgumentException("Field already exists: " + name);
        }

        ScalarField2D f = new ScalarField2D(rows, cols, oobr);
        scalar2D.put(name, f);

        return f;
    }

    public ScalarField2D scalar2D(String name) {
        ScalarField2D f = scalar2D.get(name);

        if (f == null) {
            throw new IllegalArgumentException("No such field: " + name);
        }
        
        return f;
    }

    public boolean hasScalar2D(String name) {
        return scalar2D.containsKey(name);
    }
}
