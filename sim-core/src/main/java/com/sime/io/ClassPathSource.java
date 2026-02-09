package com.sime.io;

public final class ClassPathSource<T> implements DataSource<T> {

    private final SimIO io;
    private final String resourceName;
    private final Class<T> classType;

    public ClassPathSource(SimIO io, String resourceName, Class<T> classType) {
        this.io = io;
        this.resourceName = resourceName;
        this.classType = classType;
    }

    @Override
    public T load() {
        return io.read(resourceName, classType);
    }
}

