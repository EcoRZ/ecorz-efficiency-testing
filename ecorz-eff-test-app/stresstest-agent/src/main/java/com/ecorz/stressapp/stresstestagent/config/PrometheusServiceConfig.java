package com.ecorz.stressapp.stresstestagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PrometheusServiceConfig {

  @Value("${ecorz.prometheus.user}")
  private String promUser;
  @Value("${ecorz.prometheus.password}")
  private String promPassword;
  @Value("${ecorz.prometheus.rest.end}")
  private String promRestEnd;
  @Value("${ecorz.prometheus.start.measure.offset}")
  private String promStartOff;
  @Value("${ecorz.prometheus.end.measure.offset}")
  private String promEndOff;
  @Value("${ecorz.prometheus.node19.cpus}")
  private String node19Cpus;
  @Value("${ecorz.prometheus.node20.cpus}")
  private String node20Cpus;

  public String getPromUser (){
    return promUser;
  }

  public String getPromPassword (){
    return promPassword;
  }

  public String getPromRestEnd (){
    return promRestEnd;
  }

  public String getPromStartOff (){
    return promStartOff;
  }

  public String getPromEndOff (){
    return promEndOff;
  }

  public String getPromNode19Cpus (){
    return node19Cpus;
  }

  public String getPromNode20Cpus (){
    return node20Cpus;
  }
}
