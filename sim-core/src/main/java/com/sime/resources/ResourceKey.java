package com.sime.resources;

public final class ResourceKey<T> {

  private final String name;
  private final Class<T> type;

  public static <T> ResourceKey<T> of(String name, Class<T> type) { 
    return new ResourceKey<>(name, type);
   }

  private ResourceKey(String name, Class<T> type) { 
    this.name = name;
    this.type = type;
   }

  public Class<T> type() { return type; }
  public String name() { return name; }
}
