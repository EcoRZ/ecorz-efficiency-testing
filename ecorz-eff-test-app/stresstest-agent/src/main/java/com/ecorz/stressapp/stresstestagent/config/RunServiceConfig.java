package com.ecorz.stressapp.stresstestagent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RunServiceConfig {

  // @Value("${example.property}")
  // private String exampleProperty;
  private final static String resultsDumpFolder = "./resultsDump";

  public String getResultsDumpFolder (){
    return resultsDumpFolder;
  }
}
