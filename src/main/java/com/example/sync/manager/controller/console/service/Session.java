package com.example.sync.manager.controller.console.service;

import lombok.Getter;

public class Session {
  @Getter private final String name;

  public Session(String name) {
    this.name = name;
  }

  public static Session createSession(String name) {
    return new Session(name);
  }
}
