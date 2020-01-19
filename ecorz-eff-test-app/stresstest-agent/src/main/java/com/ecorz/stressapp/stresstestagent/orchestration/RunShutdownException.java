package com.ecorz.stressapp.stresstestagent.orchestration;

public class RunShutdownException extends Exception {

  public RunShutdownException(String s) {
    super(s);
  }

  public RunShutdownException(String s, Throwable t) {
    super(s, t);
  }
}
