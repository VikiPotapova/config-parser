package org.example.implementation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class YamlConfigLoaderTest {

  private YamlConfigLoader yamlConfigLoader;

  private static final String DEFAULT_YAML_FILE = "application.yaml";
  private static final String PROFILED_YAML_FILE = "application";

  @BeforeEach
  public void setUp() {
    yamlConfigLoader = new YamlConfigLoader();
  }

  @Test
  public void testLoadDefaultConfigFileExists() throws IOException {
    File result = yamlConfigLoader.loadDefaultConfig();
    Assertions.assertNotNull(result);
    Assertions.assertEquals(DEFAULT_YAML_FILE, result.getName());
  }

  @Test
  public void testLoadProfiledConfigFileExists() throws IOException {
    String profile = "local";
    String profiledYamlFilePath = PROFILED_YAML_FILE + "-" + profile + ".yaml";

    File mockFile = mock(File.class);
    Mockito.when(mockFile.exists()).thenReturn(true);
    Mockito.when(mockFile.getPath()).thenReturn(profiledYamlFilePath);

    Assertions.assertNotNull(mockFile);
    Assertions.assertEquals(profiledYamlFilePath, mockFile.getPath());
  }

  @Test
  public void testLoadProfiledConfigFileNotFound() {
    String profile = "none";
    String profiledYamlFilePath = PROFILED_YAML_FILE + "-" + profile + ".yaml";

    File mockFile = mock(File.class);
    Mockito.when(mockFile.exists()).thenReturn(false);
    Mockito.when(mockFile.getPath()).thenReturn(profiledYamlFilePath);

    IOException exception = assertThrows(IOException.class, () -> {
      yamlConfigLoader.loadProfiledConfig(profile);
    });
    Assertions.assertTrue(exception.getMessage().contains("Configuration file not found"));
  }
}