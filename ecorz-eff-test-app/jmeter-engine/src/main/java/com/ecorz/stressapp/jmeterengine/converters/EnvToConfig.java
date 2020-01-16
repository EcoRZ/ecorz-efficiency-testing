package com.ecorz.stressapp.jmeterengine.converters;

import static com.ecorz.stressapp.common.Environment.dumpFileEnvVar;
import static com.ecorz.stressapp.common.Environment.jMeterHomeEnvVar;
import static com.ecorz.stressapp.common.Environment.jMeterOptsDel;
import static com.ecorz.stressapp.common.Environment.lbIpEnvVar;
import static com.ecorz.stressapp.common.Environment.lbPortEnvVar;
import static com.ecorz.stressapp.common.Environment.testDelayEnvVar;
import static com.ecorz.stressapp.common.Environment.testDurationEnvVar;
import static com.ecorz.stressapp.common.Environment.tgParamEnvVar;
import static com.ecorz.stressapp.common.Environment.totalArgsEnvVar;
import static com.ecorz.stressapp.common.Environment.urtParamEnvVar;

import com.ecorz.stressapp.common.run.RunConfigParams;
import com.ecorz.stressapp.common.run.benchmarks.BMOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvToConfig {

  public final static EnvToConfig INSTANCE = new EnvToConfig();

  private EnvToConfig() {
  }

  public Map<BMOption, List<String>> getOptAndArgsMap() {
    Map<BMOption,List<String>> optAndArgsMap = new HashMap<>();
    final String tgString = System.getenv(tgParamEnvVar) != null ? System.getenv(tgParamEnvVar) : "";
    final String urtString = System.getenv(urtParamEnvVar) != null ? System.getenv(urtParamEnvVar) : "";

    String tgStringArr[] = tgString.split(jMeterOptsDel);
    String urtStringArr[] = urtString.split(jMeterOptsDel);

    List<String> tgList = new ArrayList<>();
    List<String> urtList = new ArrayList<>();

    tgList = Arrays.asList(tgStringArr);
    urtList = Arrays.asList(urtStringArr);

    optAndArgsMap.put(BMOption.tg, tgList);
    optAndArgsMap.put(BMOption.urt, urtList);

    return optAndArgsMap;
  }

  public String getDumpFile() {
    final String dumpFile = System.getenv(dumpFileEnvVar) != null ? System.getenv(dumpFileEnvVar) : "";
    return dumpFile;
  }

  public RunConfigParams getRunConfigParams() {
    final String tgParam = System.getenv(tgParamEnvVar) != null ? System.getenv(tgParamEnvVar) : "";
    final String urtParam = System.getenv(urtParamEnvVar) != null ? System.getenv(urtParamEnvVar) : "";
    final String dumpFile = System.getenv(dumpFileEnvVar) != null ? System.getenv(dumpFileEnvVar) : "";
    final String jmeterHome = System.getenv(jMeterHomeEnvVar) != null ? System.getenv(jMeterHomeEnvVar) : "";
    final String lbIp = System.getenv(lbIpEnvVar) != null ? System.getenv(lbIpEnvVar) : "";
    final String lbPort = System.getenv(lbPortEnvVar) != null ? System.getenv(lbPortEnvVar) : "";
    final String testDuration = System.getenv(testDurationEnvVar) != null ? System.getenv(testDurationEnvVar) : "";
    final String testDelay = System.getenv(testDelayEnvVar) != null ? System.getenv(testDelayEnvVar) : "";
    final String totalArgs = System.getenv(totalArgsEnvVar) != null ? System.getenv(totalArgsEnvVar) : "";

    return new RunConfigParams(tgParam, urtParam, dumpFile, jmeterHome, lbIp, lbPort, testDuration, testDelay, totalArgs);
  }
}
