package com.ecorz.stressapp.stresstestagent.result;

public class ResultPersist {
    String resultFileName;

    public ResultPersist(String resultFileName) {
      this.resultFileName = resultFileName;
    }

    public String getResultFileName() {
      return resultFileName;
    }
}
