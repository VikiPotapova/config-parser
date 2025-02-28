package org.example.interfaces;

import java.io.IOException;

public interface ConfigReader {
  <T> T readConfig(Class<T> configClass, String profile) throws IOException;
}
