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
  @Value("${ecorz.prometheus.instance.minus.offset}")
  private String promInstanceMinusOff;
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
  @Value("${ecorz.prometheus.node18.id}")
  private String node18Id;
  @Value("${ecorz.prometheus.node20.id}")
  private String node20Id;
  @Value("${ecorz.prometheus.node18.cpus}")
  private String node18Cpus;
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

  public String getPromInstanceMinusOff(){
    return promInstanceMinusOff;
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

  public String getPromNode18Id (){
    return node18Id;
  }

  public String getPromNode20Id (){
    return node20Id;
  }

  public String getPromNode18Cpus (){
    return node18Cpus;
  }

  public String getPromNode20Cpus (){
    return node20Cpus;
  }
}
