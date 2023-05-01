package com.example.sync;

import com.example.sync.common.exception.SyncException;
import com.example.sync.manager.Manager;
import lombok.extern.slf4j.Slf4j;

/**
 * Sample
 *
 * @author 44380
 */
@Slf4j
public class Main {
  public static void main(String[] args) throws SyncException {
    log.info("Start application!");
    Manager.runApplication(args);
  }
}
