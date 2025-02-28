package org.example.interfaces;

import java.io.File;
import java.io.IOException;

public interface ConfigLoader {

  File loadDefaultConfig() throws IOException;
  File loadProfiledConfig(String profile) throws IOException;
}
