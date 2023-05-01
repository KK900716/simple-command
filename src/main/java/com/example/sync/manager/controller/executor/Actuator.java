package com.example.sync.manager.controller.executor;

import com.example.sync.common.annotation.Console;
import com.example.sync.common.annotation.Controller;
import com.example.sync.common.exception.InnerException;
import com.example.sync.common.exception.InnerRuntimeExecution;
import com.example.sync.common.reflection.ClassEntity;
import com.example.sync.common.reflection.ReflectionTool;
import com.example.sync.manager.controller.console.ControllerInterface;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Actuator {

  private static SyntaxInterpreter SYNTAX;

  public static void init() throws InnerException {
    List<Class<ControllerInterface>> controller =
        ReflectionTool.getAllClass(
            ControllerInterface.class,
            Controller.class);
    var cache = cacheAllCommands(controller);
    SYNTAX = new SyntaxInterpreter(cache);
  }

  public static void executeConsole(String console) {
    try {
      SyntaxRes syntaxRes = SYNTAX.analysis(console);
      if (syntaxRes != null) {
        Method method = syntaxRes.getMethod();
        Object[] args = syntaxRes.getArgs();
        switch (args.length) {
          case 0:
            method.invoke(syntaxRes.getObject());
            break;
          case 1:
            method.invoke(syntaxRes.getObject(), args[0]);
            break;
          case 2:
            method.invoke(syntaxRes.getObject(), args[0], args[1]);
            break;
          case 3:
            method.invoke(syntaxRes.getObject(), args[0], args[1], args[2]);
            break;
          case 4:
            method.invoke(syntaxRes.getObject(), args[0], args[1], args[2], args[3]);
            break;
          case 5:
            method.invoke(syntaxRes.getObject(), args[0], args[1], args[2], args[3], args[4]);
            break;
          case 6:
            method.invoke(
                syntaxRes.getObject(), args[0], args[1], args[2], args[3], args[4], args[5]);
            break;
          case 7:
            method.invoke(
                syntaxRes.getObject(),
                args[0],
                args[1],
                args[2],
                args[3],
                args[4],
                args[5],
                args[6]);
            break;
          case 8:
            method.invoke(
                syntaxRes.getObject(),
                args[0],
                args[1],
                args[2],
                args[3],
                args[4],
                args[5],
                args[6],
                args[7]);
            break;
          case 9:
            method.invoke(
                syntaxRes.getObject(),
                args[0],
                args[1],
                args[2],
                args[3],
                args[4],
                args[5],
                args[6],
                args[7],
                args[8]);
            break;
          case 10:
            method.invoke(
                syntaxRes.getObject(),
                args[0],
                args[1],
                args[2],
                args[3],
                args[4],
                args[5],
                args[6],
                args[7],
                args[8],
                args[9]);
            break;
          default:
            throw new InnerRuntimeExecution(
                "Too many parameters, please reduce the control parameters.");
        }
      }
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new InnerRuntimeExecution(e);
    }
  }

  private static Map<String, ClassEntity<ControllerInterface>> cacheAllCommands(
      List<Class<ControllerInterface>> controller) {
    Map<String, ClassEntity<ControllerInterface>> command = new HashMap<>();
    for (Class<ControllerInterface> clazz : controller) {
      Method[] methods = clazz.getMethods();
      try {
        ControllerInterface instance = clazz.getConstructor().newInstance();
        for (Method method : methods) {
          Console methodAnnotation = method.getAnnotation(Console.class);
          if (methodAnnotation != null) {
            Parameter[] parameters = method.getParameters();
            List<ClassEntity.ParameterEntity> parameterEntity = new ArrayList<>(parameters.length);
            for (Parameter parameter : parameters) {
              com.example.sync.common.annotation.Parameter parameterAnnotation =
                  parameter.getAnnotation(com.example.sync.common.annotation.Parameter.class);
              if (parameterAnnotation == null) {
                throw new InnerRuntimeExecution(
                    "Class: "
                        + clazz.getName()
                        + ", Method: "
                        + method.getName()
                        + ", Parameter: "
                        + parameter.getName()
                        + ", is not Parameter annotation!");
              }
              parameterEntity.add(new ClassEntity.ParameterEntity(parameter, parameterAnnotation));
            }
            command.put(
                methodAnnotation.value(), new ClassEntity<>(instance, method, parameterEntity));
          }
        }
        log.debug("Read in all compiled commands :{}.", command.keySet());
        return command;
      } catch (InstantiationException
          | IllegalAccessException
          | InvocationTargetException
          | NoSuchMethodException e) {
        throw new InnerRuntimeExecution(e);
      }
    }
    return command;
  }
}
