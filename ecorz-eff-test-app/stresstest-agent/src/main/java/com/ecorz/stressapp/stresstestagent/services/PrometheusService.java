package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.common.result.ResultFileWriter;
import com.ecorz.stressapp.stresstestagent.config.PrometheusServiceConfig;
import com.ecorz.stressapp.stresstestagent.prometheus.Client;
import com.ecorz.stressapp.stresstestagent.prometheus.DumpFileContentGenerator;
import com.ecorz.stressapp.stresstestagent.prometheus.PromFields.PromMetaFields;
import com.ecorz.stressapp.stresstestagent.prometheus.PromFields.PromQueryFields;
import com.ecorz.stressapp.stresstestagent.prometheus.PrometheusException;
import com.ecorz.stressapp.stresstestagent.prometheus.PromFields;
import com.ecorz.stressapp.stresstestagent.prometheus.QueryStringGenerator;
import com.ecorz.stressapp.stresstestagent.prometheus.TimeGenerator;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrometheusService {
  private PrometheusServiceConfig prometheusServiceConfig;

  private final Client promClient;

  @Autowired
  PrometheusService(PrometheusServiceConfig prometheusServiceConfig) {
    this.prometheusServiceConfig = prometheusServiceConfig;
    this.promClient = new Client(prometheusServiceConfig.getPromRestEnd(),
        prometheusServiceConfig.getPromQueryCmdIdent());
  }

  public PromFields generateFields(long startTime, long endTime) {
    int startOffMs = -Integer.valueOf(prometheusServiceConfig.getPromStartOff()) -
        Integer.valueOf(prometheusServiceConfig.getPromInstanceMinusOff());
    int endOffMs = Integer.valueOf(prometheusServiceConfig.getPromEndOff()) -
        Integer.valueOf(prometheusServiceConfig.getPromInstanceMinusOff());

    String startDate = TimeGenerator.generateRfc3339TimePlusOff(startTime, startOffMs);
    String endDate = TimeGenerator.generateRfc3339TimePlusOff(endTime, endOffMs);

    PromMetaFields metaFields = PromMetaFields.Builder.getBuilder().
        promUser(prometheusServiceConfig.getPromUser()).
        promPassword(prometheusServiceConfig.getPromPassword()).
        promRestEnd(prometheusServiceConfig.getPromRestEnd()).
        promQueryCmdIdent(prometheusServiceConfig.getPromQueryCmdIdent()).
        node18Cpus(prometheusServiceConfig.getPromNode18Cpus()).
        node20Cpus(prometheusServiceConfig.getPromNode20Cpus()).
        build();

    PromQueryFields queryFields = PromQueryFields.Builder.getBuilder().
        startTimeMs(startTime + startOffMs).
        endTimeMs(endTime + endOffMs).
        startDate(startDate).endDate(endDate).
        timeStep(prometheusServiceConfig.getPromTimeStep()).
        encQueryTemplate(prometheusServiceConfig.getPromEncQueryTemplate()).
        node18Id(prometheusServiceConfig.getPromNode18Id()).
        node20Id(prometheusServiceConfig.getPromNode20Id()).
        build();

    PromFields fields = new PromFields(metaFields, queryFields);

    return fields;
  }

  public void dump(PromFields fields, ResultFile dumpFile) throws PrometheusException {
    final String queryStringNode18 = QueryStringGenerator.generatePostQueryNode18(fields.getQueryFields());
    // final String queryStringNode20 = QueryStringGenerator.generatePostQueryNode20(fields.getQueryFields());
    List<String> unformattedPowerContent = promClient.executePost(fields.getMetaFields(), queryStringNode18);

    DumpFileContentGenerator generator = new DumpFileContentGenerator(fields.getQueryFields(), unformattedPowerContent);
    final String fileContent = generator.generate();

    try {
      ResultFileWriter.dump(dumpFile.getFullFileName(), fileContent);
    } catch (IOException e) {
      throw new PrometheusException(String.format("Cannot write into file %s", dumpFile.getFullFileName()), e);
    }
    // promClient.executePost(queryStringNode20);
  }
}
