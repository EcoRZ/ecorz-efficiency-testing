package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.common.result.ResultException;
import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.config.ResultServiceConfig;
import com.ecorz.stressapp.stresstestagent.domain.Util;
import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomainResponse;
import com.ecorz.stressapp.stresstestagent.repository.TmpRepository;
import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomain;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import com.ecorz.stressapp.stresstestagent.result.ResultPersist;
import com.ecorz.stressapp.stresstestagent.run.RunConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {

  // todo: Use Repos and consequently postgres DB
  // @Autowired
  // private ResultRepository resultRepository;
  @Autowired
  private TmpRepository tmpRepository;
  @Autowired
  private ResultServiceConfig resultConfig;
  @Autowired
  private Util util;

  // jmeter-engine dumps files in cp-folder, so to use it, rel path must be current folder
  private static final String jMeterEngineRelativeDumpFolder = ".";

  public UUID saveResult(ResultPersist resultPersist) {
    UUID uuid = UUID.randomUUID();
    tmpRepository.addResultPersist(uuid, resultPersist);

    return uuid;
  }

  public List<ResultDomainResponse> getResults() {
    return tmpRepository.getResultPersistMap().entrySet().stream().
        map(entry -> { ResultDomainResponse response = new ResultDomainResponse();
        response.uuid(entry.getKey()); response.resultFileName(entry.getValue().
              getResultFileName()); return response; } ).collect(Collectors.toList());
  }

  public ResultFile generateFileJMeter(UUID runUuid) throws ResultException {
    BenchmarkContainer bmContainer = null;
    try {
      bmContainer = util.getContainerFromRunUuid(runUuid);
    } catch (RunException e) {
      throw new ResultException(String.format("Cannot create a JMeter Result File"
          + " for run %s as it does not seem to exist", runUuid));
    }

    return ResultFile.ResultsFileFactory.jMeter(
        jMeterEngineRelativeDumpFolder, bmContainer);
  }

  public ResultFile generateFilePrometheus() {
    return ResultFile.ResultsFileFactory.prometheus(
        resultConfig.getResultsDumpFolder());
  }

  public final String getResultsDumpFolder() {
    return resultConfig.getResultsDumpFolder();
  }
}
