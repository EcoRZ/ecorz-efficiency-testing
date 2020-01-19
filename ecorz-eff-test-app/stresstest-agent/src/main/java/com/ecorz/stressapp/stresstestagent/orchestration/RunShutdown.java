package com.ecorz.stressapp.stresstestagent.orchestration;

import java.util.UUID;

public class RunShutdown {
  public static RunShutdown INSTANCE = new RunShutdown();
  private final long sleepTime;

  private RunShutdown() {
    sleepTime = 20000;
  }

  void doShutdown(UUID runUuid) throws RunShutdownException {
    try {
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      throw new RunShutdownException(String.format("Cannot shutdown run: %s", runUuid), e);
    }
  }
}
