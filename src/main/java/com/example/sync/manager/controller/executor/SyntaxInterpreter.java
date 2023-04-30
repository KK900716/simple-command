package com.example.sync.manager.controller.executor;

import com.example.sync.common.reflection.ClassEntity;
import com.example.sync.manager.controller.console.ControllerInterface;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 44380
 */
public class SyntaxInterpreter {
  private final Map<String, ClassEntity<ControllerInterface>> cache;
  private static final int PARAMETER_MAX = 10;
  private final String PARAMETER_SEPARATOR = ":";

  public SyntaxInterpreter(Map<String, ClassEntity<ControllerInterface>> cache) {
    this.cache = cache;
  }

  public SyntaxRes analysis(String console) {
    List<String> segment = split(console);
    ClassEntity<ControllerInterface> entity = cache.get(segment.get(0));
    if (entity != null) {
      return getSyntaxRes(segment, entity);
    } else {
      SystemCommand.command(console);
      return null;
    }
  }

  private SyntaxRes getSyntaxRes(List<String> segment, ClassEntity<ControllerInterface> entity) {
    Method method = entity.getMethod();
    List<ClassEntity.ParameterEntity> parameters = entity.getParameters();
    Map<String, Integer> parametersIndex = entity.getParametersIndex();
    Object[] objects = new Object[parameters.size()];
    if (segment.size() > PARAMETER_MAX) {
      System.out.println("You parameter too many, please enter again.");
      return null;
    }
    if (segment.size() > 1) {
      for (int i = 1; i < segment.size(); i++) {
        String[] split = segment.get(i).split(PARAMETER_SEPARATOR);
        if (split.length > 2) {
          System.out.println("You entered too many \":\", please enter again.");
          return null;
        }
        Integer index = parametersIndex.get(split[0]);
        if (index != null) {
          if (split.length == 1) {
            objects[index] = parameters.get(index).getParameterAnnotation().defaultV();
          } else {
            objects[index] = split[1];
          }
        } else {
          System.out.println("Parameter \"" + split[0] + "\" is incorrect, please re-enter.");
          return null;
        }
      }
    }
    return new SyntaxRes(method, entity.getObj(), objects);
  }

  private List<String> split(String console) {
    List<String> res = new ArrayList<>();
    StringBuilder word = new StringBuilder();
    for (int i = 0; i < console.length(); i++) {
      char c = console.charAt(i);
      if (c == ' ') {
        if (!(console.charAt(i - 1) == ' ')) {
          res.add(word.toString());
          word.setLength(0);
        }
      } else {
        word.append(c);
      }
    }
    res.add(word.toString());
    return res;
  }
}
