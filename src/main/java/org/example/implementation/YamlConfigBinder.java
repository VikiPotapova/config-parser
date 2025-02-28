package org.example.implementation;

import static org.example.utils.ConstantVariables.SPLITTING_REGEX;

import java.lang.reflect.Field;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.annotation.ConfigProperty;
import org.example.interfaces.ConfigBinder;
import org.example.interfaces.EnvResolver;

@RequiredArgsConstructor
public class YamlConfigBinder implements ConfigBinder {

  private final EnvResolver envResolver;

  @Override
  public <T> T createConfigInstance(Class<T> configClass) {
    try {
      return configClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create config instance", e);
    }
  }

  @Override
  public void setProperties(Object configInstance, Map<String, Object> configData) {
    Field[] fields = configInstance.getClass().getDeclaredFields();

    for (Field field : fields) {
      ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
      if (annotation == null) {
        continue;
      }
      String propertyName = annotation.value();
      String[] propertyParts = propertyName.split(SPLITTING_REGEX);
      Object propertyValue = getNestedPropertyValue(configData, propertyParts);

      if (propertyValue != null) {
        try {
          field.setAccessible(true);
          if (propertyValue instanceof Map) {
            Map<String, Object> nestedMap = (Map<String, Object>) propertyValue;
            Object nestedObject = createConfigInstance(field.getType());
            setNestedConfigFields(nestedObject,
                nestedMap);
            field.set(configInstance, nestedObject);
          } else {
            Object value = envResolver.resolveValue(propertyValue.toString(), field.getType());
            field.set(configInstance, value);
          }
        } catch (IllegalAccessException e) {
          throw new RuntimeException("Failed to set property: " + propertyName, e);
        }

      }
    }
  }

  @Override
  public Object getNestedPropertyValue(Map<String, Object> configData, String[] propertyParts) {
    Object value = configData;
    for (String part : propertyParts) {
      if (value instanceof Map) {
        value = ((Map<String, Object>) value).get(part);
      } else {
        return null;
      }
    }

    return value;
  }

  @Override
  public void setNestedConfigFields(Object nestedConfigInstance, Map<String, Object> nestedMap) {
    Field[] nestedFields = nestedConfigInstance.getClass().getDeclaredFields();

    for (Field nestedField : nestedFields) {
      String fieldName = nestedField.getName();
      if (!nestedMap.containsKey(fieldName)) {
        continue;
      }
      try {
        nestedField.setAccessible(true);
        Object fieldValue = nestedMap.get(fieldName);
        nestedField.set(nestedConfigInstance, fieldValue);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Failed to set nested field: " + nestedField.getName(), e);

      }
    }
  }
}
