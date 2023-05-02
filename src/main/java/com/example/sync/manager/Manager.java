package com.example.sync.manager;

import com.example.sync.common.exception.SyncException;
import com.example.sync.manager.controller.ControllerManager;
import com.example.sync.manager.deamon.Deamon;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Manager {
  public static void runApplication(String[] args) throws SyncException {
    Context context = Context.getInstance();
    Deamon.startDeamon(context);
    log.debug("Finish start deamon!");
    ControllerManager.startController(context);
    log.debug("Finish start controller!");
    ControllerManager.initWindow(context);
    log.debug("Finish init window!");
  }
}
