package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.stresstestagent.prometheus.PrometheusException;
import com.ecorz.stressapp.stresstestagent.prometheus.QueryFields;
import com.ecorz.stressapp.stresstestagent.prometheus.QueryFields.Builder;
import org.springframework.stereotype.Service;

@Service
public class PrometheusService {

  public QueryFields generateFields() {
    // todo: implement approprietly
    QueryFields fields = Builder.getBuilder().build();
    return fields;
  }


  public String generateDumpFileName() {
    // todo: implement approprietly
    return "dumpFile";
  }

  public void dump(QueryFields fields, String dumpFileName) throws PrometheusException {
  }
}
