package com.ecorz.stressapp.stresstestagent.engines;


  //- POST Methode startrun/id: zum Starten des Simulations-Runs -> nein, in RunEngine

import static com.ecorz.stressapp.common.Environment.jMeterOptsDel;
import static com.ecorz.stressapp.common.run.benchmarks.BMOption.urt;
import static com.ecorz.stressapp.stresstestagent.engines.RunTime.callRuntime;

import com.ecorz.stressapp.common.converters.AgentToEngineConfig;
import com.ecorz.stressapp.common.converters.AgentToEngineFile;
import com.ecorz.stressapp.common.run.RunConfigParams;
import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.common.run.RunFileParams;
import com.ecorz.stressapp.common.run.benchmarks.BMOption;
import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.config.JMeterConfig;
import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunEngine {
  private static Logger LOGGER = LoggerFactory.getLogger(RunEngine.class);

  @Autowired
  private RunServiceConfig runServiceConfig;
  @Autowired
  private JMeterConfig jMeterConfig;

  public void trigger(BenchmarkContainer bmContainer, String dumpFile) throws RunException {
    LOGGER.info(String.format("Starting %s with following Configuration String:\n %s", this,
        convertToGenericCommandString(bmContainer, dumpFile)));

    callJmeterEngineViaRuntime(generateParams(bmContainer.getOptAndArgsMap(),dumpFile));
  }

  private RunConfigParams generateParams(Map<BMOption, List<String>> optAndArgsMap, String dumpFile) throws RunException{
    checkMap(optAndArgsMap);
    return new RunConfigParams(optAndArgsMap.get(BMOption.tg).stream().collect(Collectors.joining(jMeterOptsDel)),
        optAndArgsMap.get(urt).stream().collect(Collectors.joining(jMeterOptsDel)), dumpFile,
        jMeterConfig.getJmeterHome(), runServiceConfig.getLbIp(), runServiceConfig.getLbPort(),
        jMeterConfig.getTestDuration(), jMeterConfig.getTestDelay(), runServiceConfig.getTotalArgs());
  }

  private void checkMap(Map<BMOption, List<String>> optAndArgsMap) throws RunException {
    if(optAndArgsMap.get(BMOption.tg) == null || optAndArgsMap.get(urt) == null) {
      throw new RunException("OptAndArgsMap does not contain all required keys");
    }
  }

  private void callJmeterEngineViaRuntime(RunConfigParams runConfigParams) throws RunException {
    final String jarOpts = AgentToEngineConfig.INSTANCE.convertToString(runConfigParams);
    final String java_ = System.getProperty("java.home") + "/bin/java";
    final String runStr = java_ + " -cp \'" + jMeterConfig.getJMeterCp() + "\' " + jarOpts + " " +
          jMeterConfig.getJmeterMainClass();

    callRuntime(runStr);
  }

  public void trigger(File fileById) throws RunException {
    LOGGER.info(String.format("Starting %s with following testPlan file:\n%s",
        this, fileById));

    callJmeterEngineViaRuntime(generateParams(fileById));
  }

  private RunFileParams generateParams(File fileById) {
    return new RunFileParams(fileById);
  }

  private void callJmeterEngineViaRuntime(RunFileParams runFileParams) throws RunException {
    final String jarOpts = AgentToEngineFile.INSTANCE.convertToString(runFileParams);
    final String java_ = System.getProperty("java.home") + "/bin/java";
    final String runStr = java_ + " -cp \'" + jMeterConfig.getJMeterCp() + "\' " + jarOpts + " " +
        jMeterConfig.getJmeterMainClass();

    callRuntime(runStr);
  }

  private static String convertToGenericCommandString(BenchmarkContainer bmContainer, String dumpFile) {
    final String optsAndArgsMergedString = bmContainer.getOptAndArgsMap().entrySet().stream().
        map(entry -> String.join(" ","--" + entry.getKey(), String.join(
            " ", entry.getValue()))).collect(Collectors.joining(" "));
    final String bmSpecificString = String.join(" ", bmContainer.name(),
        optsAndArgsMergedString);
    final String returnStr = String.join(" ", bmSpecificString, "--file", dumpFile);

    return returnStr;
  }
}
