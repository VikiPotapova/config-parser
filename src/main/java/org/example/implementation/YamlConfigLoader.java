package org.example.implementation;


import static org.example.utils.ConstantVariables.DEFAULT_YAML_FILE;
import static org.example.utils.ConstantVariables.PROFILED_YAML_FILE;

import java.io.File;
import java.io.IOException;
import org.example.interfaces.ConfigLoader;

public class YamlConfigLoader implements ConfigLoader {

  @Override
  public File loadDefaultConfig() throws IOException {

    File defaultYamlFileObj = new File(DEFAULT_YAML_FILE);
    if (!defaultYamlFileObj.exists()) {
      throw new IOException("Configuration file not found: " + DEFAULT_YAML_FILE);
    }
    return defaultYamlFileObj;
  }

  @Override
  public File loadProfiledConfig(String profile) throws IOException {
    String profiledYamlFile = String.format("%s-%s.yaml", PROFILED_YAML_FILE, profile);
    File profiledYamlFileObj = new File(profiledYamlFile);
    if (!profiledYamlFileObj.exists()) {
      throw new IOException("Configuration file not found: " + profiledYamlFile);
    }
    return profiledYamlFileObj;
  }

}
