package com.example.sync.manager;

import com.example.sync.common.exception.SyncException;
import com.example.sync.manager.controller.ControllerManager;
import com.example.sync.manager.deamon.Deamon;

public class Manager {
  public static void runApplication(String[] args) throws SyncException {
    Deamon.startDeamon();
    ControllerManager.initWindow();
    ControllerManager.startController();
  }
}
