package com.example.sync.manager.controller.executor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SystemCommand {
  public static void command(String command) {
    switch (command) {
      case "quit":
      case "q":
        log.info("Command: {}. Program termination.", command);
        System.exit(0);
        return;
      case "help":
        System.out.println("quit: Exit current program！");
        System.out.println("q: Exit current program！");
        return;
      case "":
        return;
      default:
        log.error("command: {} is error!", command);
        System.out.println("Unrecognized command, please re-enter!");
    }
  }
}
