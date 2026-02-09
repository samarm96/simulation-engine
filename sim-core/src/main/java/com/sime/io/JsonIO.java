package com.sime.io;

import java.io.InputStream;

import tools.jackson.databind.ObjectMapper;

public final class JsonIO implements SimIO {

    private final ObjectMapper mapper;

    public JsonIO() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public <T> T read(String resourceName, Class<T> type) {
        ClassLoader cl = JsonIO.class.getClassLoader();
        try (InputStream in = cl.getResourceAsStream(resourceName)) {
            if (in == null) throw new IllegalArgumentException("Resource not found: " + resourceName);
            return mapper.readValue(in, type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read resource JSON: " + resourceName, e);
        }
    }

    @Override
    public void write(java.nio.file.Path path, Object value) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write JSON: " + path, e);
        }
    }
}
