package org.example.interfaces;

import java.util.Map;

public interface ConfigMerger {

  Map<String, Object> mergeConfig(Map<String, Object> defaultMap, Map<String, Object> localMap);
}
