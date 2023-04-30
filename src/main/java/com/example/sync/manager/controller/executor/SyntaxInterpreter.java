package com.example.sync.manager.controller.executor;

import com.example.sync.common.reflection.ClassEntity;
import com.example.sync.manager.controller.console.ControllerInterface;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 44380
 */
public class SyntaxInterpreter {
  private final Map<String, ClassEntity<ControllerInterface>> cache;

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
    Collection<ClassEntity.ParameterEntity> list = parameters;
    Object[] objects = new Object[list.size()];
    if (segment.size() > 1) {
      for (int i = 1; i < segment.size(); i++) {
        String[] split = segment.get(i).split(":");
        if (split.length > 2) {
          System.out.println("You entered too many \":\", please enter again.");
          return null;
        }
        // TODO 有参方法
        for (int j = 0; j < ; j++) {

        }
      }
    } else {
      for (int i = 0; i < list.size(); i++) {
        com.example.sync.common.annotation.Parameter annotation =
            parameters.get(i).getParameterAnnotation();
        objects[i] = annotation.defaultV();
      }
      return new SyntaxRes(method, entity.getObj(), objects);
    }
    return null;
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
