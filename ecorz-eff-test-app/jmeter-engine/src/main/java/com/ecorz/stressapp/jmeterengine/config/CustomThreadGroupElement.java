package com.ecorz.stressapp.jmeterengine.config;

import com.lithium.mineraloil.jmeter.test_elements.ThreadGroupElement;

public class CustomThreadGroupElement {
  private final int numOfThreads;
  private final int rampUpPeriod;
  private final int duration;
  private final int startupDel;

  public CustomThreadGroupElement(Fields fields) {
    this.numOfThreads = fields.numOfThreads;
    this.rampUpPeriod = fields.rampUpPeriod;
    this.duration = fields.duration;
    this.startupDel = fields.startupDel;
  }

  public ThreadGroupElement create() {
    return ThreadGroupElement.builder().
        name("Customized thread group").
        threadCount(numOfThreads).
        rampUp(rampUpPeriod).
        delay(startupDel).
        duration(duration).
        setScheduler(true).
        loopCount(-1).
        continueForever(true).
        isFirst(true).
        build();
  }

  public static class Fields {
    private static Builder builder = new Builder();

    private final int numOfThreads;
    private final int rampUpPeriod;
    private final int duration;
    private final int startupDel;

    private Fields(int numOfThreads, int rampUpPeriod, int duration, int startupDel) {
      this.numOfThreads = numOfThreads;
      this.rampUpPeriod = rampUpPeriod;
      this.duration = duration;
      this.startupDel = startupDel;
    }

    public static Builder builder() {
      return builder;
    }

    public static class Builder {
      private int numOfThreads;
      private int rampUpPeriod;
      private int duration;
      private int startupDel;

      private Builder() {
        numOfThreads = rampUpPeriod = startupDel = duration = 0;
      }

      public Builder numOfThreads(int threadNum) {
        this.numOfThreads = threadNum;
        return this;
      }

      public Builder rampUpPeriod(int rampPer) {
        this.rampUpPeriod = rampPer;
        return this;
      }

      public Builder duration(int dur) {
        this.duration = dur;
        return this;
      }

      public Builder startUpDel(int del) {
        this.startupDel = del;
        return this;
      }

      public Fields build() {
        return new Fields(numOfThreads, rampUpPeriod, duration, startupDel);
      }
    }
  }
}
