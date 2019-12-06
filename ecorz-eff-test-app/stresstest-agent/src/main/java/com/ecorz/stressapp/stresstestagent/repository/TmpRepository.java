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
  // todo: make this consistent, i.e. this should be not a domain object then
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

  public void deleteConfigById(UUID uuid) {
    if(!configMap.containsKey(uuid)) {
      throw new IllegalArgumentException(String.format("Cannot delete run config with id %s as this id does not exist", uuid));
    }

    configMap.remove(uuid);
  }

  public void addResultDomain(UUID uuid, ResultDomain result) {
    resultMap.put(uuid, result);
  }
}
