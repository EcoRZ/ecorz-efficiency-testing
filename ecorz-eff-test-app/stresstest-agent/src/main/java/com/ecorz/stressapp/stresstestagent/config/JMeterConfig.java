package com.ecorz.stressapp.stresstestagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JMeterConfig {
  @Value("${ecorz.jMeter.home}")
  private String JMETER_HOME;
  @Value("${ecorz.jMeter.home.bin}")
  private String JMETER_HOME_BIN;
  @Value("${ecorz.jMeter.fs.base.directory}")
  private String FILE_SERVER_BASE_DIRECTORY;
  @Value("${ecorz.jMeter.test.duration}")
  private String TEST_DURATION;
  @Value("${ecorz.jMeter.test.delay}")
  private String TEST_DELAY;
  @Value("${ecorz.jMeter.engine.custom.jar.dir}")
  private String JMETER_CUSTOM_DIR;
  @Value("${ecorz.jMeter.engine.main.class}")
  private String JMETER_MAIN_CLASS;
  @Value("${ecorz.jMeter.engine.cp}")
  private String JMETER_CP;

  public String getJmeterHome (){
    return JMETER_HOME;
  }

  public String getJmeterHomeBin (){
    return JMETER_HOME_BIN;
  }

  public String getFSBaseDir() {
    return FILE_SERVER_BASE_DIRECTORY;
  }

  public String getTestDuration() {
    return TEST_DURATION;
  }

  public String getTestDelay() {
    return TEST_DELAY;
  }

  public String getJmeterCustomJarDir() {
    return JMETER_CUSTOM_DIR;
  }

  public String getJmeterMainClass() {
    return JMETER_MAIN_CLASS;
  }

  public String getJMeterCp() {
    return JMETER_CP;
  }
}
