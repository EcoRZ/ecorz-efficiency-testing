package com.ecorz.stressapp.stresstestagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RunServiceConfig {
  @Value("${ecorz.lb.ip}")
  private String lbIp;
  @Value("${ecorz.lb.port}")
  private String lbPort;
  @Value("${ecorz.ws.content.str.apache}")
  private String strApache;
  @Value("${ecorz.ws.content.str.python}")
  private String strPython;
  @Value("${ecorz.ws.content.str.use}")
  private String useWhat;
  @Value("${ecorz.run.totalargs}")
  private String totalArgs;

  public String getLbIp (){
    return lbIp;
  }

  public String getLbPort (){
    return lbPort;
  }

  public String getStrApache() {
    return strApache;
  }

  public String getStrPython() {
    return strPython;
  }

  public String getUseWhat() {
    return useWhat;
  }

  public String getTotalArgs() {
    return totalArgs;
  }
}
