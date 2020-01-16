package com.ecorz.stressapp.common.run;

public class RunException extends Exception {

  public RunException(String s) {
    super(s);
  }

  public RunException(String s, Throwable t) {
    super(s, t);
  }
}
