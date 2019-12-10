package com.ecorz.stressapp.stresstestagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RunServiceConfig {
  // @Value("${example.property}")
  // private String exampleProperty;
  private final static String lbIp = "134.60.64.217";
  private final static String lbPort = "8080";

  public String getLbIp (){
    return lbIp;
  }

  public String getLbPort (){
    return lbPort;
  }
}
