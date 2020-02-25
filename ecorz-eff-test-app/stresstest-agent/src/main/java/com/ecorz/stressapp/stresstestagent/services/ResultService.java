package com.ecorz.stressapp.stresstestagent.services;

import com.ecorz.stressapp.common.result.ResultException;
import com.ecorz.stressapp.common.result.ResultFileWriter;
import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.config.ResultServiceConfig;
import com.ecorz.stressapp.stresstestagent.domain.Util;
import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomainResponse;
import com.ecorz.stressapp.stresstestagent.repository.TmpRepository;
import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomain;
import com.ecorz.stressapp.stresstestagent.result.DumpFileMetaTmpGenerator;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import com.ecorz.stressapp.stresstestagent.result.ResultPersist;
import com.ecorz.stressapp.stresstestagent.run.RunConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

  public ResultFile generateFileJMeter(UUID runUuid, Date date) throws ResultException {
    BenchmarkContainer bmContainer = null;
    try {
      bmContainer = util.getContainerFromRunUuid(runUuid);
    } catch (RunException e) {
      throw new ResultException(String.format("Cannot create a JMeter Result File"
          + " for run %s as it does not seem to exist", runUuid));
    }

    return ResultFile.ResultsFileFactory.jMeter(
        jMeterEngineRelativeDumpFolder, bmContainer, date);
  }

  public ResultFile generateFilePrometheus(Date date) {
    return ResultFile.ResultsFileFactory.prometheus(
        resultConfig.getResultsDumpFolder(), date);
  }

  public ResultFile generateFileMeta(Date date) {
    return ResultFile.ResultsFileFactory.meta(
        resultConfig.getResultsDumpFolder(), date);
  }

  public final String getResultsDumpFolder() {
    return resultConfig.getResultsDumpFolder();
  }

  public void dumpMetaTmp(UUID runUuid, ResultFile resultFileMeta) throws ResultException {
    if(tmpRepository.getConfigMap().get(runUuid) == null) {
      throw new ResultException(String.format("Cannot dump meta as there is no run with id: %s", runUuid));
    }

    RunConfig config = tmpRepository.getConfigMap().get(runUuid);
    DumpFileMetaTmpGenerator generator = new DumpFileMetaTmpGenerator(config.
        getContainer());
    final String fileContent = generator.generate();

    try {
      ResultFileWriter.dump(resultFileMeta.getFullFileName(), fileContent);
    } catch (IOException e) {
      throw new ResultException(String.format("Cannot write into file %s", resultFileMeta.getFullFileName()), e);
    }
  }
}
