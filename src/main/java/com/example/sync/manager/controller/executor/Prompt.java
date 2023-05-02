package com.example.sync.manager.controller.executor;

import java.util.Date;

public class Prompt {
  private static final Prompt PROMPT = new Prompt();
  private String hook = "";

  private Prompt() {}

  public static Prompt getInstance() {
    return PROMPT;
  }

  public String getPrompt() {
    return "[" + new Date() + "] sync-data" + ("".equals(hook) ? "" : ":" + hook) + " >> ";
  }

  public void setHook(String hook) {
    this.hook = hook;
  }
}
