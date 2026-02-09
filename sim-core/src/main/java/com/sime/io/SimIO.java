package com.sime.io;

public interface SimIO {
    <T> T read(String resourceName, Class<T> type);

    void write(java.nio.file.Path path, Object value);
}
