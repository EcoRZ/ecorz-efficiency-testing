package com.ecorz.stressapp.stresstestagent.domain.run;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class RunConfigFieldsResponse extends RunConfigFields {
  @JsonProperty
  UUID uuid;

  public RunConfigFieldsResponse uuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public UUID getUuid() {
    return uuid;
  }
}
