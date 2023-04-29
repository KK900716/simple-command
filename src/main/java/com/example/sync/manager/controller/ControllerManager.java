package com.example.sync.manager.controller;

import com.example.sync.common.annotation.Console;
import com.example.sync.common.annotation.Controller;
import com.example.sync.common.exception.InnerRuntimeExecution;
import com.example.sync.common.exception.SyncException;
import com.example.sync.common.reflection.ClassEntity;
import com.example.sync.common.reflection.ReflectionTool;
import com.example.sync.manager.controller.console.ControllerInterface;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ControllerManager {
  private static final ArrayBlockingQueue<String> CONSOLE_BLOCK_QUEUE =
      new ArrayBlockingQueue<>(10);
  private static final Map<String, ClassEntity<ControllerInterface>> COMMAND = new HashMap<>();

  public static void startController() {
    new Thread(
            () -> {
              Scanner input = new Scanner(System.in);
              while (true) {
                String console = input.nextLine();
                CONSOLE_BLOCK_QUEUE.add(console);
              }
            },
            "input")
        .start();
  }

  public static void consumingMessages() throws SyncException {
    URL url = ReflectionTool.getCurrentClassPath(ControllerInterface.class);
    List<Class<ControllerInterface>> controller =
        ReflectionTool.getAllClass(ControllerInterface.class, Controller.class, url);
    cacheAllCommands(controller);
    log.debug("Read in all compiled commands :{}.", COMMAND.keySet());
    new Thread(
            () -> {
              while (true) {
                executeConsole();
              }
            },
            "execute")
        .start();
  }

  private static void executeConsole() {
    String command;
    try {
      command = CONSOLE_BLOCK_QUEUE.take();
      ClassEntity<ControllerInterface> entity = COMMAND.get(command);
      if (entity != null) {
        Object invoke = entity.getMethod().invoke(entity.getObj());
      } else {
        SystemCommand.command(command);
      }
    } catch (InterruptedException | InvocationTargetException | IllegalAccessException e) {
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

  public static void initWindow() {
    System.out.println("-".repeat(100));
    System.out.println("|" + String.format("%98s", "") + "|");
    System.out.println(
        "|" + String.format("%-98s", " ".repeat(35) + "Welcome sync to command!") + "|");
    System.out.println("|" + String.format("%98s", "") + "|");
    System.out.println("-".repeat(100));
  }
}
