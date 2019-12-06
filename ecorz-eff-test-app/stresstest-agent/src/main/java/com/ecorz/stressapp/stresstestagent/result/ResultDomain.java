package com.ecorz.stressapp.stresstestagent.result;

public class ResultDomain {
  private final String resultFileName;

  public ResultDomain(String resultFileName) {
    this.resultFileName = resultFileName;
  }

  public String getResultFileName() {
    return resultFileName;
  }
}
