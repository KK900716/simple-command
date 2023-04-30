package com.example.sync.manager.controller.syntax;

import java.util.Date;

public class Prompt {

  public static String getPrompt() {
    return "[" + new Date() + "] sync-data >> ";
  }
}
