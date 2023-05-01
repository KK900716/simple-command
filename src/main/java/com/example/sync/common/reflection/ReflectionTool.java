package com.example.sync.common.reflection;

import com.example.sync.common.exception.InnerException;
import com.example.sync.common.exception.InnerRuntimeExecution;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 44380
 */
@Slf4j
public final class ReflectionTool {
  private static final String CLASS = ".class";
  private static final String JAR = ".jar";

  public static <T> List<Class<T>> getAllClass(Class<T> clazz, Class<?> annotation)
      throws InnerException {
    URL location = clazz.getProtectionDomain().getCodeSource().getLocation();
    String packageName = clazz.getPackageName().replaceAll("\\.", "/");
    File file;
    try {
      file = new File(location.toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    if (file.getName().endsWith(JAR)) {
      return getJarClass(clazz, annotation, location, packageName);
    } else {
      return getClass(clazz, annotation, location, packageName);
    }
  }

  private static <T> ArrayList<Class<T>> getClass(
      Class<T> clazz, Class<?> annotation, URL location, String packageName) throws InnerException {
    ArrayList<Class<T>> res = new ArrayList<>();
    try {
      Files.walkFileTree(
          Path.of(URI.create(location + packageName)),
          new InnerSimpleFileVisitor<>(clazz, annotation, res));
    } catch (IOException e) {
      throw new InnerException(e);
    }
    return res;
  }

  private static <T> ArrayList<Class<T>> getJarClass(
      Class<T> clazz, Class annotation, URL location, String packageName) throws InnerException {
    ArrayList<Class<T>> res = new ArrayList<>();
    try {
      Enumeration<JarEntry> entries = new JarFile(location.toString()).entries();
      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();
        Class<?> aClass;
        if (entry.getName().contains(packageName) && entry.getName().endsWith(CLASS)) {
          aClass = Class.forName(entry.getName(), true, clazz.getClassLoader());
          Annotation ano = aClass.getAnnotation(annotation);
          if (ano != null) {
            res.add((Class<T>) aClass);
          }
        }
      }
    } catch (IOException | ClassNotFoundException e) {
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
