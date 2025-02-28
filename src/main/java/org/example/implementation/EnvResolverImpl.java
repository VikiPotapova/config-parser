package org.example.implementation;

import org.apache.commons.lang3.StringUtils;
import org.example.interfaces.EnvResolver;

public class EnvResolverImpl implements EnvResolver {

  @Override
  public Object resolveValue(String propertyValue, Class<?> fieldType) {

    if (StringUtils.isEmpty(propertyValue)) {
      throw new RuntimeException("Property value " + propertyValue + " is empty");
    }
    if (propertyValue.startsWith("${") && propertyValue.endsWith("}")) {
      String envVar = propertyValue.substring(2, propertyValue.length() - 1);
      String envValue = System.getenv(envVar);
      if (envValue == null) {
        throw new RuntimeException("Environment variable " + envVar + " is not set");
      }
      return convertValue(envValue, fieldType);
    }
    return convertValue(propertyValue, fieldType);
  }

  @Override
  public Object convertValue(String propertyValue, Class<?> fieldType) {
    if (fieldType == String.class) {
      return propertyValue;
    } else if (fieldType == int.class || fieldType == Integer.class) {
      return Integer.parseInt(propertyValue);
    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
      return Boolean.parseBoolean(propertyValue);
    } else if (fieldType == double.class || fieldType == Double.class) {
      return Double.parseDouble(propertyValue);
    } else {
      throw new RuntimeException("Unsupported type: " + fieldType);
    }
  }
}
