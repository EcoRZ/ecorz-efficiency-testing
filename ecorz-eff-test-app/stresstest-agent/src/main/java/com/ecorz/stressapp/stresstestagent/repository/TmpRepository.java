package com.ecorz.stressapp.stresstestagent.repository;

import com.ecorz.stressapp.stresstestagent.domain.result.ResultDomain;
import com.ecorz.stressapp.stresstestagent.run.RunConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class TmpRepository {
  private final Map<UUID,RunConfig> configMap;
  private final Map<UUID,ResultDomain> resultMap;

  public TmpRepository() {
    configMap = new HashMap<>();
    resultMap = new HashMap<>();
  }

  public Map<UUID, RunConfig> getConfigMap() {
    return configMap;
  }

  public Map<UUID, ResultDomain> getResultDomainMap() {
    return resultMap;
  }

  public RunConfig getConfigById(UUID runConigUuid) {
    return configMap.get(runConigUuid);
  }

  public ResultDomain getResultById(UUID resultUuid) {
    return resultMap.get(resultUuid);
  }

  public void addConfig(UUID uuid, RunConfig configFields) {
    configMap.put(uuid, configFields);
  }

  public void addResultDomain(UUID uuid, ResultDomain result) {
    resultMap.put(uuid, result);
  }
}
