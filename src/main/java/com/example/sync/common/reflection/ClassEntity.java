package com.example.sync.common.reflection;

import java.lang.reflect.Method;
import lombok.Data;

/**
 * @author 44380
 */
@Data
public class ClassEntity<T> {
  private final T obj;
  private final Method method;
}
