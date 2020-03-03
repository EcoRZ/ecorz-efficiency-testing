package com.ecorz.stressapp.common.run;

import java.util.Objects;

public class RunConfigParams {
  public final String tgParam;
  public final String urtParam;
  public final String dumpFile;
  public final String jmeterHome;
  public final String lbIp;
  public final String lbPort;
  public final String wsTestStr;
  public final String testDuration;
  public final String testDelay;
  public final String totalArgs;

  public RunConfigParams(String tgParam, String urtParam, String dumpFile, String jmeterHome,
        String lbIp, String lbPort, String wsTestStr, String testDuration, String testDelay, String totalArgs) {
    this.tgParam = tgParam;
    this.urtParam = urtParam;
    this.dumpFile = dumpFile;
    this.jmeterHome = jmeterHome;
    this.lbIp = lbIp;
    this.lbPort = lbPort;
    this.wsTestStr = wsTestStr;
    this.testDuration = testDuration;
    this.testDelay = testDelay;
    this.totalArgs = totalArgs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunConfigParams that = (RunConfigParams) o;
    return tgParam.equals(that.tgParam) &&
        urtParam.equals(that.urtParam) &&
        dumpFile.equals(that.dumpFile) &&
        lbIp.equals(that.lbIp) &&
        lbPort.equals(that.lbPort) &&
        wsTestStr.equals(that.wsTestStr) &&
        testDuration.equals(that.testDuration) &&
        testDelay.equals(that.testDelay) &&
        totalArgs.equals(that.totalArgs) &&
        jmeterHome.equals(that.jmeterHome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tgParam, urtParam, dumpFile, jmeterHome, lbIp, lbPort, wsTestStr, testDuration, testDelay, totalArgs);
  }
}
