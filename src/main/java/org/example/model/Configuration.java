package org.example.model;

import lombok.Data;
import org.example.annotation.ConfigProperty;

@Data
public class Configuration {

  @ConfigProperty("${app.name}")
  private String appName;

  @ConfigProperty("app.port")
  private int port;

  @ConfigProperty("app.active")
  private boolean active;

  @ConfigProperty("app.profile")
  private String profile;

  @ConfigProperty("nested.config")
  private NestedConfig nestedConfig;

  @Data
  public static class NestedConfig {

    @ConfigProperty("nested.config.field1")
    private String field1;

    @ConfigProperty("nested.config.field2")
    private int field2;
  }
}
