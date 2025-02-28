package org.example.implementation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.interfaces.EnvResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EnvResolverTest {

  @Mock
  private EnvResolver envResolver;


  @BeforeEach
  public void setUp() {
    envResolver = new EnvResolverImpl();
  }


  @Test
  public void testResolveValueNormalString() {
    String propertyValue = "String";
    Class<?> fieldType = String.class;
    Object expectedValue = envResolver.resolveValue(propertyValue, fieldType);
    Assertions.assertEquals(expectedValue, propertyValue);
  }

  @Test
  public void testResolveValue_validInt() {
    String propertyValue = "123";
    Class<?> fieldType = int.class;

    Object result = envResolver.resolveValue(propertyValue, fieldType);
    Assertions.assertEquals(123, result);
  }

  @Test
  public void testResolveValueValidBoolean() {
    String propertyValue = "true";
    Class<?> fieldType = Boolean.class;

    Object result = envResolver.resolveValue(propertyValue, fieldType);
    Assertions.assertTrue((Boolean) result);
  }

  @Test
  public void testResolveValueValidEnvironmentVariable() {
    String propertyValue = "${app.name}";
    Class<?> fieldType = String.class;

    Object result = envResolver.resolveValue(propertyValue, fieldType);
    Assertions.assertEquals("app.name", result);
  }

  @Test
  public void testResolveValueInvalidEnvironmentVariable() {
    String propertyValue = "${MY_ENV_VAR}";
    Class<?> fieldType = String.class;

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      envResolver.resolveValue(propertyValue, fieldType);
    });
    Assertions.assertEquals("Environment variable MY_ENV_VAR is not set", exception.getMessage());
  }

  @Test
  public void testResolveValueEmptyPropertyValue() {
    String propertyValue = "";
    Class<?> fieldType = String.class;

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      envResolver.resolveValue(propertyValue, fieldType);
    });
    Assertions.assertEquals("Property value  is empty", exception.getMessage());
  }

  @Test
  public void testResolveValueInvalidType() {
    String propertyValue = "123";
    Class<?> fieldType = Object.class;

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      envResolver.resolveValue(propertyValue, fieldType);
    });
    Assertions.assertEquals("Unsupported type: class java.lang.Object", exception.getMessage());
  }
}