package com.ecorz.stressapp.stresstestagent.engines;


  //- POST Methode startrun/id: zum Starten des Simulations-Runs -> nein, in RunEngine

import com.ecorz.stressapp.stresstestagent.run.RunException;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.OptAndArgs;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RunEngine {
  private static Logger LOGGER = LoggerFactory.getLogger(RunEngine.class);

  public void trigger(BenchmarkContainer bmContainer, List<OptAndArgs> optAndArgsList, String dumpFile) throws RunException {
    LOGGER.info(String.format("Starting %s with following cli String:\n %s", this,
        convertToCliString(optAndArgsList, dumpFile)));
  }

  private static String convertToCliString(List<OptAndArgs> optAndArgsList, String dumpFile) {
    final String returnStr = String.join(" ", optAndArgsList.toArray().toString(), "--file", dumpFile);

    return returnStr;
  }
}
