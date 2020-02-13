package com.ecorz.stressapp.stresstestagent.orchestration;

public class ConsistencyChecks {

  private static final String cPDel = ":";
  private static final int cPLength = 2;

  private ConsistencyChecks() {
  }

  public static boolean dumpFolderIsConsistent(String jMeterClassPath, String configDumpFolder) {
    String[] cPSplitted = jMeterClassPath.split(cPDel);

    if(cPSplitted.length != cPLength) {
      return false;
    }

    if (!cPSplitted[cPLength-1].equals(configDumpFolder)) {
      return false;
    }

    return true;
  }
}
