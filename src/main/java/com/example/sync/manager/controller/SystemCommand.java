package com.example.sync.manager.controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SystemCommand {
  public static void command(String command) {
    switch (command) {
      case "quit":
      case "q":
        log.info("Command: {}. Program termination.", command);
        System.exit(0);
        break;
      case "help":
        System.out.println("quit: Exit current program！");
        System.out.println("q: Exit current program！");
        break;
      default:
        System.out.println("Unrecognized command, please re-enter!");
    }
  }
}
