package org.example.utils;

import lombok.Data;

@Data
public class ConstantVariables {

  public static final String DEFAULT_YAML_FILE = "src/main/resources/application.yaml";
  public static final String PROFILED_YAML_FILE = "src/main/resources/application";
  public static final String SPLITTING_REGEX = "[./\\-_]";
}
