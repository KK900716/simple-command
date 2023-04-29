package com.example.sync.common.exception;

public class InnerException extends SyncException {
  private static final ExceptionEnum EXCEPTION = ExceptionEnum.INNER_EXCEPTION;

  public InnerException(Throwable cause) {
    super(EXCEPTION.getErrorMessage(), cause);
  }

  public InnerException(String message) {
    super(EXCEPTION.getErrorMessage() + message);
  }

  public InnerException(String message, Throwable cause) {
    super(EXCEPTION.getErrorMessage() + message, cause);
  }
}
