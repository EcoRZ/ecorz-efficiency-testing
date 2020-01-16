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
  @Value("${ecorz.jmeter.test.duration}")
  private String TEST_DURATION;
  @Value("${ecorz.jmeter.test.delay}")
  private String TEST_DELAY;
  @Value("${ecorz.jmeter.engine.custom.jar.dir}")
  private String JMETER_CUSTOM_DIR;
  @Value("${ecorz.jmeter.engine.main.class}")
  private String JMETER_MAIN_CLASS;
  @Value("${ecorz.jmeter.engine.cp}")
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
