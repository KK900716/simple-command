package com.example.sync.manager.controller.console;

import com.example.sync.common.annotation.Console;
import com.example.sync.common.annotation.Controller;

/**
 * @author 44380
 */
@Controller
public class ControllerTest implements ControllerInterface {
  private String x;

  @Console("print")
  public void test() {
    x = "abcd";
    System.out.println("successful");
  }

  @Console("hou")
  public void test2() {
    System.out.println(x);
  }
}
