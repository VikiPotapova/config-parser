package org.example.interfaces;

public interface EnvResolver {

  Object resolveValue(String propertyValue, Class<?> fieldType);
  Object convertValue(String propertyValue, Class<?> fieldType);
}
