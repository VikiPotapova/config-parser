package org.example.implementation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.example.interfaces.EnvResolver;
import org.example.model.Configuration;
import org.example.model.Configuration.NestedConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class YamlConfigBinderTest {

  @InjectMocks
  private YamlConfigBinder yamlConfigBinder;
  @Mock
  private EnvResolver envResolver;

  @BeforeEach
  public void setUp() {
    envResolver = new EnvResolverImpl();
    yamlConfigBinder = new YamlConfigBinder(envResolver);
  }

  @Test
  public void testCreateConfigInstanceSuccess() {
    Configuration instance = yamlConfigBinder.createConfigInstance(Configuration.class);
    Assertions.assertNotNull(instance);
    Assertions.assertTrue(instance instanceof Configuration);
  }

  @Test
  public void testGetNestedPropertyValue() {
    Map<String, Object> configData = new HashMap<>();
    Map<String, Object> nestedMap = new HashMap<>();
    nestedMap.put("nestedProperty", "value");
    configData.put("nestedConfig", nestedMap);

    String[] propertyParts = {"nestedConfig", "nestedProperty"};
    Object value = yamlConfigBinder.getNestedPropertyValue(configData, propertyParts);
    Assertions.assertEquals("value", value);
  }

  @Test
  public void testSetNestedConfigFields() throws NoSuchFieldException, IllegalAccessException {
    NestedConfig nestedConfig = new NestedConfig();
    Map<String, Object> nestedMap = new HashMap<>();
    nestedMap.put("field1", "NestedValue");

    yamlConfigBinder.setNestedConfigFields(nestedConfig, nestedMap);

    Field field = NestedConfig.class.getDeclaredField("field1");
    field.setAccessible(true);
    Assertions.assertEquals("NestedValue", field.get(nestedConfig));
  }
}

