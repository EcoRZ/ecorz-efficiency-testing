package com.ecorz.stressapp.stresstestagent.converters;

import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomain;
import com.ecorz.stressapp.stresstestagent.result.ResultPersist;

public class ResultDomainToPersist {
  private ResultDomainToPersist() {
  }

  public static ResultPersist convert(ResultDomain domain) {
    ResultPersist persist = new ResultPersist(domain.getResultFileName());
    return persist;
  }

  public static ResultDomain convertBack(ResultPersist persist) {
    ResultDomain domain = new ResultDomain();
    return domain.resultFileName(domain.getResultFileName());
  }
}
