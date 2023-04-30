package com.example.sync.common.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import lombok.Data;

/**
 * @author 44380
 */
@Data
public class ClassEntity<T> {
  private final T obj;
  private final Method method;
  private final List<ParameterEntity> parameters;

  @Data
  public static class ParameterEntity {
    private final Parameter parameter;
    private final com.example.sync.common.annotation.Parameter parameterAnnotation;
  }
}
