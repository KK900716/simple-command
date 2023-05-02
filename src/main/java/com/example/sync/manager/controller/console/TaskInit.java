package com.example.sync.manager.controller.console;

import com.example.sync.common.annotation.Console;
import com.example.sync.common.annotation.Controller;
import com.example.sync.common.annotation.Parameter;
import com.example.sync.manager.Context;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 44380
 */
@Controller
@Slf4j
public class TaskInit implements ControllerInterface {
  private final Context context = Context.getInstance();

  @Console("session")
  public void test(
      @Parameter("-create") String createName,
      @Parameter("-choose") String choose,
      @Parameter("q") String q) {
    context.quitSession(q);
    // create session
    if (createName != null && !"true".equals(createName)) {
      log.debug("create session name {}", createName);
      context.setSession(createName);
    }
  }
}
