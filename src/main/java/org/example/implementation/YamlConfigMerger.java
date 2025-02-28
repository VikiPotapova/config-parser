package org.example.implementation;

import java.util.Map;
import org.example.interfaces.ConfigMerger;

public class YamlConfigMerger implements ConfigMerger {

  @Override
  public Map<String, Object> mergeConfig(Map<String, Object> defaultMap,
      Map<String, Object> localMap) {
    for (Map.Entry<String, Object> entry : localMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (defaultMap.containsKey(key) && defaultMap.get(key) instanceof Map
          && value instanceof Map) {
        Map<String, Object> defaultNestedMap = (Map<String, Object>) defaultMap.get(key);
        Map<String, Object> localNestedMap = (Map<String, Object>) value;
        defaultMap.put(key, mergeConfig(defaultNestedMap, localNestedMap));
      } else {
        defaultMap.put(key, value);
      }
    }
    return defaultMap;
  }
}
