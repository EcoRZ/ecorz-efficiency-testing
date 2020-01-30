package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.common.run.benchmarks.BMOption;
import com.ecorz.stressapp.common.run.benchmarks.OptAndArgs;
import com.ecorz.stressapp.stresstestagent.config.ResultServiceConfig;
import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import com.ecorz.stressapp.stresstestagent.domain.Util;
import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFields;
import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFieldsResponse;
import com.ecorz.stressapp.stresstestagent.engines.RunEngine;
import com.ecorz.stressapp.stresstestagent.repository.TmpRepository;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import com.ecorz.stressapp.stresstestagent.result.ResultPersist;
import com.ecorz.stressapp.stresstestagent.run.RunConfig;
import com.ecorz.stressapp.stresstestagent.run.RunConfig.RunConfigFactory;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;

@Service
public class RunService {
  private final static Logger LOGGER = LoggerFactory.getLogger(RunService.class);

  // @Autowired
  // private RunRepository runRepository;
  @Autowired
  private TmpRepository tmpRepository;
  @Autowired
  private RunEngine runEngine;
  // todo: put this config in ResultService and adjust code here
  @Autowired
  private RunServiceConfig runConfig;
  @Autowired
  private Util util;

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

  public void startRun(UUID runUuid, ResultFile resultFile) throws RunException {
    BenchmarkContainer bmContainer = util.getContainerFromRunUuid(runUuid);

    if(tmpRepository.getFileById(runUuid) != null) {
      LOGGER.warn("Using temporary solution to trigger engine directly with file");
      try {
        runEngine.trigger(tmpRepository.getFileById(runUuid));
      } catch (RunException e) {
        LOGGER.error(String.format("Cannot start engine via input-file %s",
            tmpRepository.getFileById(runUuid)), e);
      }
    } else {
      // todo: check if resultFile == generateFile()
      runEngine.trigger(bmContainer, resultFile.getFullFileName());
    }
  }

  public void deleteRun(UUID uuid) {
    tmpRepository.deleteConfigById(uuid);
  }

  private static List<OptAndArgs> getOptAndArgsList(RunConfig configFields) {
    Map<BMOption,List<String>> optAndArgsMap = configFields.getContainer().getOptAndArgsMap();
    return OptAndArgs.of(optAndArgsMap);
  }
}
