package com.example.sync.common.reflection;

import com.example.sync.common.exception.InnerException;
import com.example.sync.common.exception.InnerRuntimeExecution;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 44380
 */
public final class ReflectionTool {

  private static final String CLASS = ".class";

  public static URL getCurrentClassPath(Class<?> clazz) {
    String packageName = clazz.getPackageName().replaceAll("\\.", "/");
    ClassLoader loader = clazz.getClassLoader();
    return loader.getResource(packageName);
  }

  public static <T> List<Class<T>> getAllClass(Class<T> clazz, Class<?> annotation, URL url)
      throws InnerException {
    ArrayList<Class<T>> res = new ArrayList<>();
    try {
      Files.walkFileTree(
          Path.of(url.toURI()), new InnerSimpleFileVisitor<>(clazz, annotation, res));
    } catch (URISyntaxException | IOException e) {
      throw new InnerException(e);
    }
    return res;
  }

  static class InnerSimpleFileVisitor<T> extends SimpleFileVisitor<Path> {
    private final ClassLoader loader;
    private final String packageName;
    private final Class annotation;
    private final ArrayList<Class<T>> res;

    public InnerSimpleFileVisitor(Class<T> clazz, Class<?> annotation, ArrayList<Class<T>> res) {
      this.loader = clazz.getClassLoader();
      packageName = clazz.getPackageName();
      this.annotation = annotation;
      this.res = res;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      String name = file.toFile().getName();
      if (name.endsWith(CLASS)) {
        Class<?> aClass;
        try {
          String className = packageName + "." + name.replaceAll(CLASS, "");
          aClass = Class.forName(className, true, loader);
        } catch (ClassNotFoundException e) {
          throw new InnerRuntimeExecution(e);
        }
        Annotation ano = aClass.getAnnotation(annotation);
        if (ano != null) {
          res.add((Class<T>) aClass);
        }
      }
      return FileVisitResult.CONTINUE;
    }
  }
}
