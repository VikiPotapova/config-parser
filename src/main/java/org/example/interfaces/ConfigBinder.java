package org.example.interfaces;

import java.util.Map;

public interface ConfigBinder {

  <T> T createConfigInstance(Class<T> configClass);

  void setProperties(Object configInstance, Map<String, Object> configData);

  Object getNestedPropertyValue(Map<String, Object> configData, String[] propertyParts);

  void setNestedConfigFields(Object nestedConfigInstance, Map<String, Object> nestedMap);
}
