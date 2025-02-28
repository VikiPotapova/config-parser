package org.example;

import java.io.IOException;
import org.example.implementation.EnvResolverImpl;
import org.example.implementation.YamlConfigBinder;
import org.example.implementation.YamlConfigLoader;
import org.example.implementation.YamlConfigMerger;
import org.example.implementation.YamlConfigReader;
import org.example.interfaces.ConfigReader;
import org.example.model.Configuration;

public class App {

  public static void main(String[] args) {
    try {
      ConfigReader configReader = new
          YamlConfigReader(new YamlConfigLoader(), new YamlConfigBinder(new EnvResolverImpl()),
          new YamlConfigMerger());
      Configuration config = configReader.readConfig(Configuration.class, "local");

      System.out.println("App Name: " + config.getAppName());
      System.out.println("Port: " + config.getPort());
      System.out.println("Active: " + config.isActive());
      System.out.println("Profile: " + config.getProfile());
      System.out.println("Nested Field1: " + config.getNestedConfig().getField1());
      System.out.println("Nested Field2: " + config.getNestedConfig().getField2());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
