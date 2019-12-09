package com.ecorz.stressapp.stresstestagent.domain.result;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultDomain {
  @JsonProperty
  String resultFileName;

  public ResultDomain resultFileName(String resultFileName) {
    this.resultFileName = resultFileName;
    return this;
  }

  public String getResultFileName() {
    return resultFileName;
  }
}
