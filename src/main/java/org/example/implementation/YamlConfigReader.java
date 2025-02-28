package org.example.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.interfaces.ConfigBinder;
import org.example.interfaces.ConfigLoader;
import org.example.interfaces.ConfigMerger;
import org.example.interfaces.ConfigReader;

@RequiredArgsConstructor
public class YamlConfigReader implements ConfigReader {

  private final ConfigLoader configLoader;
  private final ConfigBinder configBinder;
  private final ConfigMerger configMerger;
  private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

  @Override
  public <T> T readConfig(Class<T> configClass, String profile) throws IOException {

    File defaultYamlFileObj = configLoader.loadDefaultConfig();
    Map<String, Object> defaultConfigData = objectMapper.readValue(defaultYamlFileObj, Map.class);
    T configInstance = configBinder.createConfigInstance(configClass);

    if (profile.isEmpty()) {
      configBinder.setProperties(configInstance, defaultConfigData);
      return configInstance;
    }

    File profiledYamlFileObj = configLoader.loadProfiledConfig(profile);
    Map<String, Object> profiledConfigData = objectMapper.readValue(profiledYamlFileObj,
        Map.class);
    configBinder.setProperties(configInstance,
        configMerger.mergeConfig(defaultConfigData, profiledConfigData));
    return configInstance;
  }

}