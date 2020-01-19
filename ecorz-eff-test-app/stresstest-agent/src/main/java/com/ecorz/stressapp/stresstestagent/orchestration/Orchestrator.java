package com.ecorz.stressapp.stresstestagent.orchestration;

import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.stresstestagent.config.JMeterConfig;
import com.ecorz.stressapp.stresstestagent.config.PrometheusServiceConfig;
import com.ecorz.stressapp.stresstestagent.config.ResultServiceConfig;
import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import com.ecorz.stressapp.stresstestagent.config.ServiceConfig;
import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomainResponse;
import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFields;
import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFieldsResponse;
import com.ecorz.stressapp.stresstestagent.prometheus.PrometheusException;
import com.ecorz.stressapp.stresstestagent.prometheus.QueryFields;
import com.ecorz.stressapp.stresstestagent.services.DummyService;
import com.ecorz.stressapp.stresstestagent.services.JmeterService;
import com.ecorz.stressapp.stresstestagent.services.PrometheusService;
import com.ecorz.stressapp.stresstestagent.services.ResultService;
import com.ecorz.stressapp.stresstestagent.services.RunService;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Orchestrator {
  @Autowired
  private JMeterConfig jMeterConfig;
  @Autowired
  private PrometheusServiceConfig prometheusServiceConfig;
  @Autowired
  private ResultServiceConfig resultServiceConfig;
  @Autowired
  private RunServiceConfig runServiceConfig;
  @Autowired
  private ServiceConfig serviceConfig;

  @Autowired
  private DummyService dummyService;
  @Autowired
  private JmeterService jmeterService;
  @Autowired
  private PrometheusService prometheusService;
  @Autowired
  private ResultService resultService;
  @Autowired
  private RunService runService;

  public UUID saveRun(RunConfigFields configFields) {
    return runService.saveRun(configFields);
  }

  public UUID saveRun(String file) {
    return runService.saveRun(file);
  }

  public UUID startRun(UUID runUuid) throws RunException {
    //blocking call
    UUID uuid = runService.startRun(runUuid);

    try {
      RunShutdown.INSTANCE.doShutdown(runUuid);
    } catch (RunShutdownException e) {
     throw new RunException(String.format("Cannot shutdown run %s", runUuid), e);
    }

    QueryFields fields = prometheusService.generateFields();
    String dumpFileName = prometheusService.generateDumpFileName();
    try {
      //blocking call
      dumpPowerConsumption(fields, dumpFileName);
    } catch (PrometheusException e) {
      throw new RunException(String.format("Cannot dump Prometheus results for run %s", runUuid), e);
    }

    return  uuid;
  }

  public List<RunConfigFieldsResponse> getRuns() {
    return runService.getRuns();
  }

  public void deleteRun(UUID uuid) {
    runService.deleteRun(uuid);
  }

  public List<ResultDomainResponse> getResults() {
    return resultService.getResults();
  }

  private void dumpPowerConsumption(QueryFields fields, String dumpFileName) throws PrometheusException {
    prometheusService.dump(fields, dumpFileName);
  }
}
