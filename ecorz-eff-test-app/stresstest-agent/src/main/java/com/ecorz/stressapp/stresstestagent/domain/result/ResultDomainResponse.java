package com.ecorz.stressapp.stresstestagent.domain.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class ResultDomainResponse extends ResultDomain {
  @JsonProperty
  UUID uuid;

  public ResultDomainResponse uuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public UUID getUuid() {
    return uuid;
  }
}
