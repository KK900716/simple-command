package com.example.sync.manager.controller.console.t;

import com.example.sync.common.annotation.Console;
import com.example.sync.common.annotation.Controller;
import com.example.sync.manager.controller.console.ControllerInterface;

@Controller
public class T1 implements ControllerInterface {
  @Console("pa")
  public void test() {
    System.out.println("pa");
  }
}
