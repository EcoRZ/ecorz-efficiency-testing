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
import static com.ecorz.stressapp.common.Environment.wsTestStrEnvVar;

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
    final String tgString = System.getProperty(tgParamEnvVar) != null ? System.getProperty(tgParamEnvVar) : "";
    final String urtString = System.getProperty(urtParamEnvVar) != null ? System.getProperty(urtParamEnvVar) : "";

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
    final String dumpFile = System.getProperty(dumpFileEnvVar) != null ? System.getProperty(dumpFileEnvVar) : "";
    return dumpFile;
  }

  public RunConfigParams getRunConfigParams() {
    final String tgParam = System.getProperty(tgParamEnvVar) != null ? System.getProperty(tgParamEnvVar) : "";
    final String urtParam = System.getProperty(urtParamEnvVar) != null ? System.getProperty(urtParamEnvVar) : "";
    final String dumpFile = System.getProperty(dumpFileEnvVar) != null ? System.getProperty(dumpFileEnvVar) : "";
    final String jmeterHome = System.getProperty(jMeterHomeEnvVar) != null ? System.getProperty(jMeterHomeEnvVar) : "";
    final String lbIp = System.getProperty(lbIpEnvVar) != null ? System.getProperty(lbIpEnvVar) : "";
    final String lbPort = System.getProperty(lbPortEnvVar) != null ? System.getProperty(lbPortEnvVar) : "";
    final String wsTestStr = System.getProperty(wsTestStrEnvVar) != null ? System.getProperty(wsTestStrEnvVar) : "";
    final String testDuration = System.getProperty(testDurationEnvVar) != null ? System.getProperty(testDurationEnvVar) : "";
    final String testDelay = System.getProperty(testDelayEnvVar) != null ? System.getProperty(testDelayEnvVar) : "";
    final String totalArgs = System.getProperty(totalArgsEnvVar) != null ? System.getProperty(totalArgsEnvVar) : "";

    return new RunConfigParams(tgParam, urtParam, dumpFile, jmeterHome, lbIp, lbPort, wsTestStr,
        testDuration, testDelay, totalArgs);
  }
}
