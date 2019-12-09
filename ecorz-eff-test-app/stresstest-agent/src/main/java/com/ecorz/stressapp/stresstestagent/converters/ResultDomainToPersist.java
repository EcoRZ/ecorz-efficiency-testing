package com.ecorz.stressapp.stresstestagent.converters;

import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomain;
import com.ecorz.stressapp.stresstestagent.result.ResultPersist;
import java.util.UUID;

public class ResultDomainToPersist {
  private ResultDomainToPersist() {
  }

  public static ResultPersist convert(UUID uuid, ResultDomain domain) {
    ResultPersist persist = new ResultPersist(uuid, domain.getResultFileName());
    return persist;
  }

  public static ResultDomain convertBack(ResultPersist persist) {
    ResultDomain domain = new ResultDomain();
    return domain.resultFileName(persist.getResultFileName());
  }
}
