package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.stresstestagent.config.ResultServiceConfig;
import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFields;
import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFieldsResponse;
import com.ecorz.stressapp.stresstestagent.engines.RunEngine;
import com.ecorz.stressapp.stresstestagent.repository.TmpRepository;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import com.ecorz.stressapp.stresstestagent.result.ResultPersist;
import com.ecorz.stressapp.stresstestagent.run.RunConfig;
import com.ecorz.stressapp.stresstestagent.run.RunConfig.RunConfigFactory;
import com.ecorz.stressapp.stresstestagent.run.RunException;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.OptAndArgs;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunService {
  private final static Logger LOGGER = LoggerFactory.getLogger(RunService.class);

  // @Autowired
  // private RunRepository runRepository;
  @Autowired
  private TmpRepository tmpRepository;
  @Autowired
  private ResultService resultService;
  @Autowired
  private RunEngine runEngine;
  @Autowired
  private ResultServiceConfig config;

  public UUID saveRun(RunConfigFields runConfigFields) {
    UUID uuid = UUID.randomUUID();
    RunConfig config = RunConfigFactory.ofDomain(uuid, runConfigFields);
    tmpRepository.addConfig(uuid, config);

    return uuid;
  }


  public UUID saveRun(String file) {
    UUID uuid = UUID.randomUUID();
    File file_ = new File(file);
    RunConfig config = RunConfigFactory.ofFile(uuid, file_);
    tmpRepository.addConfig(uuid, config);
    tmpRepository.addFile(uuid, file_);

    return uuid;
  }

  public List<RunConfigFieldsResponse> getRuns() {
    return tmpRepository.getConfigMap().entrySet().stream().
        map(entry -> { RunConfigFields fields = RunConfigFields.ofConfig(entry.getValue());
          RunConfigFieldsResponse response = new RunConfigFieldsResponse();
          response.uuid(entry.getKey()); response.arg1(fields.getArg1());
          response.arg2(fields.getArg2()); response.arg3(fields.getArg3());
          response.bmName(fields.getBmName()); response.stuff(fields.getStuff());
          return response; } ).collect(Collectors.toList());
  }

  public UUID startRun(UUID runUuid) throws RunException {
    RunConfig configFields = tmpRepository.getConfigById(runUuid);

    if(configFields == null) {
      throw new RunException(String.format("Cannot start run with id %s as it does not exist.", runUuid));
    }

    BenchmarkContainer bmContainer = configFields.getContainer();
    final ResultFile file = ResultFile.ResultsFileFactory.of(
        config.getResultsDumpFolder(), bmContainer);

    if(tmpRepository.getFileById(runUuid) != null) {
      LOGGER.warn("Using temporary solution to trigger engine directly with file");
      try {
        runEngine.trigger(tmpRepository.getFileById(runUuid));
      } catch (IOException e) {
        LOGGER.error(String.format("Cannot start engine via input-file %s",
            tmpRepository.getConfigById(runUuid)), e);
      }
    } else {
      runEngine.trigger(bmContainer, file.getFullFileName());
    }
    UUID uuid = UUID.randomUUID();
    resultService.saveResult(new ResultPersist(uuid, file.getFullFileName()));

    return uuid;
  }

  public void deleteRun(UUID uuid) {
    tmpRepository.deleteConfigById(uuid);
  }

  private static List<OptAndArgs> getOptAndArgsList(RunConfig configFields) {
    Map<BMOption,List<String>> optAndArgsMap = configFields.getContainer().getOptAndArgsMap();
    return OptAndArgs.of(optAndArgsMap);
  }
}
