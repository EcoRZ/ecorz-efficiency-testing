package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.stresstestagent.config.JMeterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JmeterService {
  @Autowired
  private JMeterConfig jMeterConfig;

  public final String getJMeterCP() {
    return jMeterConfig.getJMeterCp();
  }
}
