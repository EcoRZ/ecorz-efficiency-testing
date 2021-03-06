package com.ecorz.stressapp.stresstestagent.domain;

import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.repository.TmpRepository;
import com.ecorz.stressapp.stresstestagent.run.RunConfig;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Util {
  @Autowired
  private TmpRepository tmpRepository;

  public String getContainerNameFromRunUuid(UUID runUuid) throws RunException {
    RunConfig config = tmpRepository.getConfigById(runUuid);

    if(config == null) {
      throw new RunException(String.format("Cannot get run with id %s as it does not exist.", runUuid));
    }

    return config.getContainer().name();
  }

}
