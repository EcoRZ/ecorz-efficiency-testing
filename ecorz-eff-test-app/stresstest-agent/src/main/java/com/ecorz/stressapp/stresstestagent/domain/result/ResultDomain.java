package com.ecorz.stressapp.stresstestagent.domain.result;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultDomain {
  @JsonProperty
  private final String resultFileName;

  public ResultDomain(String resultFileName) {
    this.resultFileName = resultFileName;
  }

  public String getResultFileName() {
    return resultFileName;
  }
}
