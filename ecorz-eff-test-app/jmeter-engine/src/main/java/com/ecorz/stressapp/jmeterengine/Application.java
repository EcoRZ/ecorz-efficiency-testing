package com.ecorz.stressapp.jmeterengine;

import static com.ecorz.stressapp.common.Environment.fileByIdEnvVar;

import com.ecorz.stressapp.common.run.RunConfigParams;
import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.common.run.benchmarks.BMOption;
import com.ecorz.stressapp.jmeterengine.converters.EnvToConfig;
import com.ecorz.stressapp.jmeterengine.converters.EnvToFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Application {
  public static void main(String[] args) {
    JMeterEngineWrapper wrapper = new JMeterEngineWrapper();

    if(runWithFile()) {
      File fileById = EnvToFile.INSTANCE.getFileById();
      try {
        System.out.println("Starting JMeter-Engine with jmx-file as input...");
        wrapper.runWithJmxFile(fileById);
      } catch (IOException e) {
        System.err.println(String.format("Cannot open file %s, Exception was %s", fileById, e));
      }
    } else {
      try {
        System.out.println("Starting JMeter-Engine with config as input...");
        Map<BMOption,List<String>> optAndArgsMap = EnvToConfig.INSTANCE.getOptAndArgsMap();
        String dumpFile = EnvToConfig.INSTANCE.getDumpFile();
        RunConfigParams params = EnvToConfig.INSTANCE.getRunConfigParams();
        wrapper.runWithConfig(optAndArgsMap, dumpFile, params);
      } catch (RunException e) {
        System.err.println(String.format("Cannot start runner as there was a problem with the config,"
            + "Exception was %s", e));
      }
    }
  }

  private static boolean runWithFile() {
    return System.getenv(fileByIdEnvVar) != null ? true : false;
  }
}
