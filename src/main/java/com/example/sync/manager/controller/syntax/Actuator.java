package com.example.sync.manager.controller.syntax;

import com.example.sync.common.annotation.Console;
import com.example.sync.common.annotation.Controller;
import com.example.sync.common.exception.InnerException;
import com.example.sync.common.exception.InnerRuntimeExecution;
import com.example.sync.common.reflection.ClassEntity;
import com.example.sync.common.reflection.ReflectionTool;
import com.example.sync.manager.controller.ExecutorServiceFactory;
import com.example.sync.manager.controller.SystemCommand;
import com.example.sync.manager.controller.console.ControllerInterface;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Actuator {
  private static final Map<String, ClassEntity<ControllerInterface>> COMMAND = new HashMap<>();
  private static final ExecutorService EXECUTOR_SERVICE =
      Executors.newFixedThreadPool(10, new ExecutorServiceFactory());

  public Actuator() {}

  public static void init() throws InnerException {
    List<Class<ControllerInterface>> controller =
        ReflectionTool.getAllClass(
            ControllerInterface.class,
            Controller.class,
            ReflectionTool.getCurrentClassPath(ControllerInterface.class));
    cacheAllCommands(controller);
    log.debug("Read in all compiled commands :{}.", COMMAND.keySet());
  }

  public static void executeConsole(String console) {
    try {
      ClassEntity<ControllerInterface> entity = COMMAND.get(console);
      if (entity != null) {
        Future<Object> submit =
            EXECUTOR_SERVICE.submit(() -> entity.getMethod().invoke(entity.getObj()));
        Object o = submit.get();
      } else {
        SystemCommand.command(console);
      }
    } catch (ExecutionException | InterruptedException e) {
      throw new InnerRuntimeExecution(e);
    }
  }

  private static void cacheAllCommands(List<Class<ControllerInterface>> controller) {
    for (Class<ControllerInterface> clazz : controller) {
      Method[] methods = clazz.getMethods();
      try {
        ControllerInterface instance = clazz.getConstructor().newInstance();
        for (Method method : methods) {
          Console annotation = method.getAnnotation(Console.class);
          if (annotation != null) {
            COMMAND.put(annotation.value(), new ClassEntity<>(instance, method));
          }
        }
      } catch (InstantiationException
          | IllegalAccessException
          | InvocationTargetException
          | NoSuchMethodException e) {
        throw new InnerRuntimeExecution(e);
      }
    }
  }
}
