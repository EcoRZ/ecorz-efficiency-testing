package com.ecorz.stressapp.stresstestagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RunServiceConfig {
  @Value("${ecorz.lb.ip}")
  private String lbIp;
  @Value("${ecorz.lb.port}")
  private String lbPort;
  @Value("${ecorz.run.totalargs}")
  private String totalArgs;

  public String getLbIp (){
    return lbIp;
  }

  public String getLbPort (){
    return lbPort;
  }

  public String getTotalArgs() {
    return totalArgs;
  }
}
