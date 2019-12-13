package com.ecorz.stressapp.stresstestagent.engines;


  //- POST Methode startrun/id: zum Starten des Simulations-Runs -> nein, in RunEngine

import com.ecorz.stressapp.stresstestagent.engines.jmeter.JMeterEngineWrapper;
import com.ecorz.stressapp.stresstestagent.run.RunException;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BenchmarkContainer;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunEngine {
  private static Logger LOGGER = LoggerFactory.getLogger(RunEngine.class);

  @Autowired
  private JMeterEngineWrapper jmeterWrapper;

  public void trigger(BenchmarkContainer bmContainer, String dumpFile) throws RunException {
    LOGGER.info(String.format("Starting %s with following Configuration String:\n %s", this,
        convertToGenericCommandString(bmContainer, dumpFile)));

    jmeterWrapper.runWithConfig(bmContainer.getOptAndArgsMap(), dumpFile);
  }

  public void trigger(String jmeterHome, File fileById) throws IOException {
    LOGGER.info(String.format("Starting %s with following testPlan file:\n%s\n"
            + "and following jmeter_home:\n%s", this, fileById, jmeterHome));

    jmeterWrapper.runWithJmxFile(jmeterHome, fileById);
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
