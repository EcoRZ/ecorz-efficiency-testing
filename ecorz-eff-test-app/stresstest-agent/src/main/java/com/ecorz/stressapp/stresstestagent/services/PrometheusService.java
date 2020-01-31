package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.stresstestagent.config.PrometheusServiceConfig;
import com.ecorz.stressapp.stresstestagent.prometheus.Client;
import com.ecorz.stressapp.stresstestagent.prometheus.PromFields.PromMetaFields;
import com.ecorz.stressapp.stresstestagent.prometheus.PromFields.PromQueryFields;
import com.ecorz.stressapp.stresstestagent.prometheus.PrometheusException;
import com.ecorz.stressapp.stresstestagent.prometheus.PromFields;
import com.ecorz.stressapp.stresstestagent.prometheus.QueryStringGenerator;
import com.ecorz.stressapp.stresstestagent.prometheus.TimeGenerator;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrometheusService {
  @Autowired
  private PrometheusServiceConfig prometheusServiceConfig;

  private final Client promClient;

  @Autowired
  PrometheusService() {
    this.promClient = new Client(prometheusServiceConfig.getPromRestEnd(),
        prometheusServiceConfig.getPromQueryCmdIdent());
  }

  public PromFields generateFields(long startTime, long endTime) {
    int startOffMs = Integer.valueOf(prometheusServiceConfig.getPromStartOff());
    int endOffMs = Integer.valueOf(prometheusServiceConfig.getPromEndOff());

    String startDate = TimeGenerator.generateRfc3339TimeMinusOff(startTime, startOffMs);
    String endDate = TimeGenerator.generateRfc3339TimePlusOff(endTime, endOffMs);

    PromMetaFields metaFields = PromMetaFields.Builder.getBuilder().
        promUser(prometheusServiceConfig.getPromUser()).
        promPassword(prometheusServiceConfig.getPromPassword()).
        promRestEnd(prometheusServiceConfig.getPromRestEnd()).
        promQueryCmdIdent(prometheusServiceConfig.getPromQueryCmdIdent()).
        node19Cpus(prometheusServiceConfig.getPromNode19Cpus()).
        node20Cpus(prometheusServiceConfig.getPromNode20Cpus()).
        build();

    PromQueryFields queryFields = PromQueryFields.Builder.getBuilder().
        startDate(startDate).endDate(endDate).
        timeStep(prometheusServiceConfig.getPromTimeStep()).
        encQueryTemplate(prometheusServiceConfig.getPromEncQueryTemplate()).
        node19Id(prometheusServiceConfig.getPromNode19Id()).
        node20Id(prometheusServiceConfig.getPromNode20Id()).
        build();

    PromFields fields = new PromFields(metaFields, queryFields);

    return fields;
  }

  public void dump(PromFields fields, ResultFile dumpFile) throws PrometheusException {
    final String queryStringNode19 = QueryStringGenerator.generatePostQueryNode19(fields.getQueryFields());
    // final String queryStringNode20 = QueryStringGenerator.generatePostQueryNode20(fields.getQueryFields());
    promClient.executePost(fields.getMetaFields(), queryStringNode19);
    // promClient.executePost(queryStringNode20);
  }
}
