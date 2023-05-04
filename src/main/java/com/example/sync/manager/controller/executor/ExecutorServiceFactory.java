package com.example.sync.manager.controller.executor;

import java.util.concurrent.ThreadFactory;
import javax.annotation.Nonnull;

public class ExecutorServiceFactory implements ThreadFactory {
  private static int COUNTER = 1;

  @Override
  public Thread newThread(@Nonnull Runnable r) {
    return new Thread(r, "Service-" + COUNTER++);
  }
}
