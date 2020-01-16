package com.ecorz.stressapp.stresstestagent.repository;

import com.ecorz.stressapp.stresstestagent.result.ResultPersist;
import com.ecorz.stressapp.stresstestagent.run.RunConfig;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TmpRepository {
  private final static Logger LOGGER = LoggerFactory.getLogger(TmpRepository.class);
  private final Map<UUID,RunConfig> configMap;
  private final Map<UUID,ResultPersist> resultMap;
  private final Map<UUID,File> temporaryFileMap;

  public TmpRepository() {
    configMap = new HashMap<>();
    resultMap = new HashMap<>();
    temporaryFileMap = new HashMap<>();
  }

  public Map<UUID, RunConfig> getConfigMap() {
    return configMap;
  }

  public Map<UUID, ResultPersist> getResultPersistMap() {
    return resultMap;
  }

  public RunConfig getConfigById(UUID runConigUuid) {
    return configMap.get(runConigUuid);
  }

  public ResultPersist getResultById(UUID resultUuid) {
    return resultMap.get(resultUuid);
  }

  public void addConfig(UUID uuid, RunConfig configFields) {
    configMap.put(uuid, configFields);
  }

  public void deleteConfigById(UUID uuid) {
    if(!configMap.containsKey(uuid)) {
      throw new IllegalArgumentException(String.format("Cannot delete com.ecorz.stressapp.common.run config with id %s as this id does not exist", uuid));
    }

    configMap.remove(uuid);
  }

  public void addResultPersist(UUID uuid, ResultPersist result) {
    resultMap.put(uuid, result);
  }

  public Map<UUID, File> getTemporaryFileMap() {
    LOGGER.warn(String.format("Using temporary method getTemporaryFileMap()"));
    return temporaryFileMap;
  }

  public File getFileById(UUID fileUuid) {
    LOGGER.warn(String.format("Using temporary method getFileById()"));
    return temporaryFileMap.get(fileUuid);
  }

  public void addFile(UUID uuid, File file) {
    LOGGER.warn(String.format("Using temporary method addFile()"));
    temporaryFileMap.put(uuid, file);
  }
}
