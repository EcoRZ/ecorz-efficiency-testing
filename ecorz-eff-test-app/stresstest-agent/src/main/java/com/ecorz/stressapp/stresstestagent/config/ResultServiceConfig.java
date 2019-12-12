package com.ecorz.stressapp.stresstestagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResultServiceConfig {
  @Value("${ecorz.results.resultsdumpfolder}")
  private String resultsDumpFolder;

  public String getResultsDumpFolder (){
    return resultsDumpFolder;
  }
}
