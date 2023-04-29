package com.example.sync.common.exception;

public class InnerRuntimeExecution extends RuntimeException {
  private static final ExceptionEnum EXCEPTION = ExceptionEnum.INNER_RUNTIME_EXCEPTION;

  public InnerRuntimeExecution(Throwable cause) {
    super(EXCEPTION.getErrorMessage(), cause);
  }

  public InnerRuntimeExecution(String message) {
    super(EXCEPTION.getErrorMessage() + message);
  }

  public InnerRuntimeExecution(String message, Throwable cause) {
    super(EXCEPTION.getErrorMessage() + message, cause);
  }
}
