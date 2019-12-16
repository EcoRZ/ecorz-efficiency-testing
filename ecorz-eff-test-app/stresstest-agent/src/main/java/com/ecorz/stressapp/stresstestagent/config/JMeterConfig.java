package com.ecorz.stressapp.stresstestagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JMeterConfig {
  @Value("${ecorz.jmeter.home}")
  private String JMETER_HOME;
  @Value("${ecorz.jmeter.home.bin}")
  private String JMETER_HOME_BIN;
  @Value("${ecorz.jmeter.fs.base.directory}")
  private String FILE_SERVER_BASE_DIRECTORY;

  public String getJmeterHome (){
    return JMETER_HOME;
  }

  public String getJmeterHomeBin (){
    return JMETER_HOME_BIN;
  }

  public String getFSBaseDir() {
    return FILE_SERVER_BASE_DIRECTORY;
  }
}
