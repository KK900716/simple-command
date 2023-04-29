package com.example.sync.common.exception;

public enum ExceptionEnum {
  INNER_EXCEPTION("0001"),
  INNER_RUNTIME_EXCEPTION("0002");

  private final String errorCode;

  ExceptionEnum(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return getErrorMessage(null);
  }

  public String getErrorMessage(String s) {
    return "Sync-" + errorCode + ", " + (s == null ? "" : s + ", ") + "error message: ";
  }
}
