package com.example.sync.common.exception;

public abstract class SyncException extends Exception {
  public SyncException(String message) {
    super(message);
  }

  public SyncException(String message, Throwable cause) {
    super(message, cause);
  }

  public SyncException(Throwable cause) {
    super(cause);
  }
}
