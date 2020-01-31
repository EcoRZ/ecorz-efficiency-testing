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
  @Value("${ecorz.prometheus.query.cmd.ident}")
  private String promQueryCmdIdent;
  @Value("${ecorz.prometheus.start.measure.offset}")
  private String promStartOff;
  @Value("${ecorz.prometheus.end.measure.offset}")
  private String promEndOff;
  @Value("${ecorz.prometheus.timestep}")
  private String timeStep;
  @Value("${ecorz.prometheus.url.enc.query.template}")
  private String encQueryTemplate;
  @Value("${ecorz.prometheus.node19.id}")
  private String node19Id;
  @Value("${ecorz.prometheus.node20.id}")
  private String node20Id;
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

  public String getPromQueryCmdIdent(){
    return promQueryCmdIdent;
  }

  public String getPromStartOff (){
    return promStartOff;
  }

  public String getPromEndOff (){
    return promEndOff;
  }

  public String getPromTimeStep () {
    return timeStep;
  }

  public String getPromEncQueryTemplate () {
    return encQueryTemplate;
  }

  public String getPromNode19Id (){
    return node19Id;
  }

  public String getPromNode20Id (){
    return node20Id;
  }

  public String getPromNode19Cpus (){
    return node19Cpus;
  }

  public String getPromNode20Cpus (){
    return node20Cpus;
  }
}
