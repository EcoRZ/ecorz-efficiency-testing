package com.ecorz.stressapp.common.result;

public class ResultException extends Exception {

  public ResultException(String s) {
    super(s);
  }

  public ResultException(String s, Throwable t) {
    super(s, t);
  }
}
