package com.example.sync.manager.controller;

import com.example.sync.common.exception.InnerException;
import com.example.sync.manager.controller.executor.Actuator;
import com.example.sync.manager.controller.executor.Prompt;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ControllerManager {
  public static void startController() throws InnerException {
    Actuator.init();
    log.debug("Finish init Actuator!");
    new Thread(
            () -> {
              Scanner input = new Scanner(System.in);
              while (true) {
                System.out.print(Prompt.getPrompt());
                Actuator.executeConsole(input.nextLine().stripLeading().stripTrailing());
              }
            },
            "input")
        .start();
  }

  public static void initWindow() {
    System.out.println("-".repeat(100));
    System.out.println("|" + String.format("%98s", "") + "|");
    System.out.println(
        "|" + String.format("%-98s", " ".repeat(35) + "Welcome to sync command!") + "|");
    System.out.println("|" + String.format("%98s", "") + "|");
    System.out.println("-".repeat(100));
  }
}
