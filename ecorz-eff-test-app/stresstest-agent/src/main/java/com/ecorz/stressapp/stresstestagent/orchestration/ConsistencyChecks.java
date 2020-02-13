package com.ecorz.stressapp.stresstestagent.orchestration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsistencyChecks {

  private static final String cPDel = ":";
  private static final int cPLength = 2;
  private final static Logger LOGGER = LoggerFactory.getLogger(ConsistencyChecks.class);

  private ConsistencyChecks() {
  }

  public static boolean dumpFolderIsConsistent(String jMeterClassPath, String configDumpFolder) {
    String[] cPSplitted = jMeterClassPath.split(cPDel);
    LOGGER.error("stuff: " + cPSplitted[cPLength-1] + ", " + configDumpFolder);

    if(cPSplitted.length != cPLength) {
      return false;
    }

    if (!cPSplitted[cPLength-1].equals(configDumpFolder)) {
      return false;
    }

    return true;
  }
}
