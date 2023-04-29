package com.example.sync.manager.deamon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Deamon implements Runnable {
  private Deamon() {}

  public static void startDeamon() {
    Thread deamon = new Thread(new Deamon(), "Deamon");
    deamon.setDaemon(true);
    deamon.start();
  }

  @Override
  public void run() {
    log.info("Deamon start!");
  }
}
