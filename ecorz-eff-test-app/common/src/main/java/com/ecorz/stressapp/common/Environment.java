package com.ecorz.stressapp.common;

public class Environment {

  private Environment() {
  }

  private final static String defParPrefix = "ecorz";
  private final static String defParPrefixJmeter = defParPrefix + ".jmeter";
  private final static String defParPrefixNet = defParPrefix + ".lb";
  private final static String defParPrefixJmeterTest = defParPrefixJmeter + ".test";
  private final static String defParPrefixJmeterParams = defParPrefixJmeter + ".params";

  public final static String tgParamEnvVar = String.format("%s.tg", defParPrefixJmeterParams);
  public final static String urtParamEnvVar = String.format("%s.urt", defParPrefixJmeterParams);
  public final static String dumpFileEnvVar = String.format("%s.dumpfile", defParPrefix);
  public final static String jMeterHomeEnvVar = String.format("%s.home", defParPrefixJmeter);
  public final static String lbIpEnvVar = String.format("%s.ip", defParPrefixNet);
  public final static String lbPortEnvVar = String.format("%s.port", defParPrefixNet);
  public final static String testDurationEnvVar = String.format("%s.duration", defParPrefixJmeterTest);
  public final static String testDelayEnvVar = String.format("%s.delay", defParPrefixJmeterTest);
  public final static String totalArgsEnvVar = String.format("%s.totalargs", defParPrefix);
  public final static String fileByIdEnvVar = String.format("%s.filebyid", defParPrefixJmeter);

  public final static String jMeterOptsDel = ";";
}
