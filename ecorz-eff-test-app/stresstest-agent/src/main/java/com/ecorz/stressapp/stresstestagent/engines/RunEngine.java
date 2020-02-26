package com.ecorz.stressapp.stresstestagent.engines;


  //- POST Methode startrun/id: zum Starten des Simulations-Runs -> nein, in RunEngine

import static com.ecorz.stressapp.common.Environment.jMeterOptsDel;
import static com.ecorz.stressapp.common.run.benchmarks.BMOption.tg;
import static com.ecorz.stressapp.common.run.benchmarks.BMOption.urt;
import static com.ecorz.stressapp.stresstestagent.engines.RunTime.callRuntime;

import com.ecorz.stressapp.common.converters.AgentToEngineConfig;
import com.ecorz.stressapp.common.converters.AgentToEngineFile;
import com.ecorz.stressapp.common.run.RunConfigParams;
import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.common.run.RunFileParams;
import com.ecorz.stressapp.common.run.benchmarks.BMOption;
import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.common.run.benchmarks.OptAndArgs;
import com.ecorz.stressapp.stresstestagent.config.JMeterConfig;
import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import com.ecorz.stressapp.stresstestagent.run.RunConfig;
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

  public void trigger(String bmContainerName, RunConfig runConfig, String fullFileName) throws RunException{
    LOGGER.info(String.format("Starting %s with following Configuration String:\n %s", this,
        convertToGenericCommandString(bmContainerName, runConfig, fullFileName)));

    callJmeterEngineViaRuntime(generateParams(runConfig.getOptAndArgsList(), fullFileName));
  }

  private RunConfigParams generateParams(List<OptAndArgs> optAndArgsList, String dumpFile) throws RunException{
    checkList(optAndArgsList);
    return new RunConfigParams(optAndArgsList.stream().filter(item -> item.opt == tg).
        collect(Collectors.toList()).get(0).args.stream().collect(Collectors.joining(jMeterOptsDel)),
        optAndArgsList.stream().filter(item -> item.opt == urt).
        collect(Collectors.toList()).get(0).args.stream().collect(Collectors.joining(jMeterOptsDel)),
        dumpFile, jMeterConfig.getJmeterHome(), runServiceConfig.getLbIp(),
        runServiceConfig.getLbPort(), jMeterConfig.getTestDuration(), jMeterConfig.getTestDelay(),
        runServiceConfig.getTotalArgs());
  }

  private void checkList(List<OptAndArgs> optAndArgsMap) throws RunException {
    if(optAndArgsMap.stream().filter(item -> item.opt == BMOption.tg).
        collect(Collectors.toList()) == null ||
        optAndArgsMap.stream().filter(item -> item.opt == BMOption.urt).
        collect(Collectors.toList()) == null) {
      throw new RunException("OptAndArgsList does not contain all required Opts");
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

  private static String convertToGenericCommandString(String bmContainerName,
      RunConfig runConfig, String dumpFile) {
    final String optsAndArgsMergedString = runConfig.getOptAndArgsList().stream().
        map(item -> String.join(" ", "--" + item.opt, String.join(
            " ", item.args))).collect(Collectors.joining(" "));
    final String bmSpecificString = String.join(" ", bmContainerName,
        optsAndArgsMergedString);
    final String returnStr = String.join(" ", bmSpecificString, "--file", dumpFile);

    return returnStr;
  }
}
