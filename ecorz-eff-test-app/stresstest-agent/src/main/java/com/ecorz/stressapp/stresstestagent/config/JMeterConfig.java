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
  @Value("${ecorz.jmeter.custom.jar.dir}")
  private String JMETER_CUSTOM_DIR;
  @Value("${ecorz.jmeter.custom.jar.file}")
  private String JMETER_CUSTOM_FILE;

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

  public String getFullCustomJmeterJarFilePath() {
    return JMETER_CUSTOM_DIR + "/" + JMETER_CUSTOM_FILE;
  }
}
