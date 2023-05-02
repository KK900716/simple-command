package com.example.sync.manager;

import com.example.sync.manager.controller.console.service.Session;
import com.example.sync.manager.controller.executor.Prompt;
import java.util.HashMap;
import java.util.Map;
import lombok.Setter;

public class Context {
  private static final Context CONTEXT = new Context();
  private Map<String, Session> session = new HashMap<>();
  private Session nowSession;
  @Setter private Prompt prompt;

  private Context() {}

  public static Context getInstance() {
    return CONTEXT;
  }

  public void setSession(String name) {
    prompt.setHook(name);
    Session have = session.get(name);
    if (have == null) {
      nowSession = Session.createSession(name);
      session.put(name, nowSession);
    } else {
      switchSession(name);
      System.out.println("session " + name + " already exist, Have switched!");
    }
  }

  public void quitSession(String q) {
    if (q == null) {
      switchSession(null);
    } else {
      switch (q) {
        case "true":
          switchSession(null);
          return;
        case "delete":
          if (nowSession == null) {
            System.out.println("Switch to a session and then delete it.");
          } else {
            prompt.setHook("");
            session.remove(nowSession.getName());
            System.out.println("session " + nowSession.getName() + " delete!");
            nowSession = null;
          }
          return;
        default:
          System.out.println("Please enter the correct parameters!");
      }
    }
  }

  private void switchSession(String name) {
    if (name == null) {
      nowSession = null;
      prompt.setHook("");
    } else {
      nowSession = session.get(name);
      if (nowSession == null) {
        System.out.println("session " + name + " nonexistence!");
      }
    }
  }
}
