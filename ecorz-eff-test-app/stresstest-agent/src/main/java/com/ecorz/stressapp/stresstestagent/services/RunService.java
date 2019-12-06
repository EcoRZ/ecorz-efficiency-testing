package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import com.ecorz.stressapp.stresstestagent.engines.RunEngine;
import com.ecorz.stressapp.stresstestagent.repository.TmpRepository;
import com.ecorz.stressapp.stresstestagent.result.ResultDomain;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import com.ecorz.stressapp.stresstestagent.run.RunConfigFields;
import com.ecorz.stressapp.stresstestagent.run.RunException;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.OptAndArgs;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunService {

  // @Autowired
  // private RunRepository runRepository;
  @Autowired
  private TmpRepository tmpRepository;
  @Autowired
  private RunEngine runEngine;
  @Autowired
  private RunServiceConfig config;

  public UUID saveRun(RunConfigFields runConfigFields) {
    UUID uuid = UUID.randomUUID();
    tmpRepository.addConfig(uuid, runConfigFields);

    return uuid;
  }

  public List<RunConfigFields> getRuns() {
    return new ArrayList<RunConfigFields>(tmpRepository.getConfigMap().values());
  }

  public UUID startRun(UUID runUuid) throws RunException {
    RunConfigFields configFields = tmpRepository.getConfigById(runUuid);

    if(configFields == null) {
      throw new RunException(String.format("Cannot start run with id %s as it does not exist.", runUuid));
    }

    BenchmarkContainer bmContainer = configFields.getContainer();
    List<OptAndArgs> optAndArgsList = getOptAndArgsList(configFields);
    final ResultFile file = ResultFile.ResultsFileFactory.of(
        config.getResultsDumpFolder(), bmContainer);

    runEngine.trigger(bmContainer, optAndArgsList, file.getFullFileName());

    UUID uuid = UUID.randomUUID();

    tmpRepository.addResultDomain(uuid, new ResultDomain(file.getFullFileName()));

    return uuid;
  }

  private static List<OptAndArgs> getOptAndArgsList(RunConfigFields configFields) {
    Map<BMOption,List<String>> optAndArgsMap = configFields.getContainer().getOptAndArgsMap();
    return OptAndArgs.of(optAndArgsMap);
  }
}
