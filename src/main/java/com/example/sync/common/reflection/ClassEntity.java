package com.example.sync.common.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;

/**
 * @author 44380
 */
@Getter
public class ClassEntity<T> {
  private final T obj;
  private final Method method;
  private final List<ParameterEntity> parameters;
  private final Map<String, Integer> parametersIndex = new HashMap<>();

  public ClassEntity(T obj, Method method, List<ParameterEntity> parameters) {
    this.obj = obj;
    this.method = method;
    this.parameters = parameters;
    for (int i = 0; i < parameters.size(); i++) {
      parametersIndex.put(parameters.get(i).getParameterAnnotation().value(), i);
    }
  }

  @Data
  public static class ParameterEntity {
    private final Parameter parameter;
    private final com.example.sync.common.annotation.Parameter parameterAnnotation;
  }
}
