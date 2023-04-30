package com.example.sync.manager.controller.executor;

import java.lang.reflect.Method;
import lombok.Data;

@Data
public class SyntaxRes {
  private final Method method;
  private final Object object;
  private final Object[] args;
}
