package com.example.sync.manager.controller.executor;

import java.util.concurrent.ThreadFactory;
import org.jetbrains.annotations.NotNull;

public class ExecutorServiceFactory implements ThreadFactory {
  private static int COUNTER = 1;

  @Override
  public Thread newThread(@NotNull Runnable r) {
    return new Thread(r, "Service-" + COUNTER++);
  }
}
