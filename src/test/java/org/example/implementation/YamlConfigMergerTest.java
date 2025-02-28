package org.example.implementation;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class YamlConfigMergerTest{
  private YamlConfigMerger yamlConfigMerger;

  @BeforeEach
  public void setUp() {
    yamlConfigMerger = new YamlConfigMerger();
  }

  @Test
  public void testMergeConfig_simpleMerge() {
    Map<String, Object> defaultMap = new HashMap<>();
    defaultMap.put("key1", "value1");
    defaultMap.put("key2", "value2");

    Map<String, Object> localMap = new HashMap<>();
    localMap.put("key1", "newValue1");
    localMap.put("key3", "value3");

    Map<String, Object> mergedMap = yamlConfigMerger.mergeConfig(defaultMap, localMap);

    Assertions.assertEquals(3, mergedMap.size());
    Assertions.assertEquals("newValue1", mergedMap.get("key1"));
    Assertions.assertEquals("value2", mergedMap.get("key2"));
    Assertions.assertEquals("value3", mergedMap.get("key3"));
  }

  @Test
  public void testMergeConfig_withNestedMaps() {
    Map<String, Object> defaultMap = new HashMap<>();
    Map<String, Object> nestedMap = new HashMap<>();
    nestedMap.put("nestedKey1", "nestedValue1");
    defaultMap.put("nested", nestedMap);

    Map<String, Object> localMap = new HashMap<>();
    Map<String, Object> localNestedMap = new HashMap<>();
    localNestedMap.put("nestedKey1", "newNestedValue1");
    localMap.put("nested", localNestedMap);

    Map<String, Object> mergedMap = yamlConfigMerger.mergeConfig(defaultMap, localMap);

    Assertions.assertTrue(mergedMap.containsKey("nested"));
    Map<String, Object> mergedNestedMap = (Map<String, Object>) mergedMap.get("nested");
    Assertions.assertEquals("newNestedValue1", mergedNestedMap.get("nestedKey1"));
  }
}