package com.ecorz.stressapp.stresstestagent.result;

import java.util.UUID;

public class ResultPersist {
    final UUID uuid;
    final String resultFileName;

    public ResultPersist(UUID uuid, String resultFileName) {
      this.resultFileName = resultFileName;
      this.uuid = uuid;
    }

    public String getResultFileName() {
      return resultFileName;
    }

    public UUID getUuid() {
      return uuid;
    }
}
