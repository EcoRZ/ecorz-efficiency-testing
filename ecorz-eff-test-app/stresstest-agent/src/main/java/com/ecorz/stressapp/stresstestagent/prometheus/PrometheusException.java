package com.ecorz.stressapp.stresstestagent.prometheus;

public class PrometheusException extends Exception {

  public PrometheusException(String s) {
    super(s);
  }

  public PrometheusException(String s, Throwable t) {
    super(s, t);
  }
}
