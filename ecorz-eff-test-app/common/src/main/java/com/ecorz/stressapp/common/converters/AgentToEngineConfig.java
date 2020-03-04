package com.ecorz.stressapp.common.converters;

import static com.ecorz.stressapp.common.Environment.dumpFileEnvVar;
import static com.ecorz.stressapp.common.Environment.jMeterHomeEnvVar;
import static com.ecorz.stressapp.common.Environment.lbIpEnvVar;
import static com.ecorz.stressapp.common.Environment.lbPortEnvVar;
import static com.ecorz.stressapp.common.Environment.testDelayEnvVar;
import static com.ecorz.stressapp.common.Environment.testDurationEnvVar;
import static com.ecorz.stressapp.common.Environment.tgParamEnvVar;
import static com.ecorz.stressapp.common.Environment.totalArgsEnvVar;
import static com.ecorz.stressapp.common.Environment.urtParamEnvVar;
import static com.ecorz.stressapp.common.Environment.wsTestStrEnvVar;

import com.ecorz.stressapp.common.run.RunConfigParams;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgentToEngineConfig {
  public final static AgentToEngineConfig INSTANCE = new AgentToEngineConfig();

  public final static String tgParamCmdLineDefString = String.format("-D%s=", tgParamEnvVar);
  public final static String urtParamCmdLineDefString = String.format("-D%s=", urtParamEnvVar);
  public final static String dumpFileCmdLineDefString = String.format("-D%s=", dumpFileEnvVar);
  public final static String jMeterHomeCmdLineDefString = String.format("-D%s=", jMeterHomeEnvVar);
  public final static String lbIpCmdLineDefString = String.format("-D%s=", lbIpEnvVar);
  public final static String lbPortCmdLineDefString = String.format("-D%s=", lbPortEnvVar);
  public final static String wsTestStrCmdLineDefString = String.format("-D%s=", wsTestStrEnvVar);
  public final static String testDurationCmdLineDefString = String.format("-D%s=", testDurationEnvVar);
  public final static String testDelayCmdLineDefString = String.format("-D%s=", testDelayEnvVar);
  public final static String totalArgsCmdLineDefString = String.format("-D%s=", totalArgsEnvVar);

  private final static Pattern tgParamPattern = Pattern.compile(tgParamCmdLineDefString + "([^\\s]+)");
  private final static Pattern urtParamPattern = Pattern.compile(urtParamCmdLineDefString + "([^\\s]+)");
  private final static Pattern dumpFilePattern = Pattern.compile(dumpFileCmdLineDefString + "([^\\s]+)");
  private final static Pattern jMeterHomePattern = Pattern.compile(jMeterHomeCmdLineDefString + "([^\\s]+)");
  private final static Pattern lbIpPattern = Pattern.compile(lbIpCmdLineDefString + "([^\\s]+)");
  private final static Pattern lbPortPattern = Pattern.compile(lbPortCmdLineDefString + "([^\\s]+)");
  private final static Pattern wsTestStrPattern = Pattern.compile(wsTestStrCmdLineDefString + "([^\\s]+)");
  private final static Pattern testDurationPattern = Pattern.compile(testDurationCmdLineDefString + "([^\\s]+)");
  private final static Pattern testDelayPattern = Pattern.compile(testDelayCmdLineDefString + "([^\\s]+)");
  private final static Pattern totalArgsPattern = Pattern.compile(totalArgsCmdLineDefString + "([^\\s]+)");

  private AgentToEngineConfig() {
  }

  public String convertToString(RunConfigParams params) {
    StringBuilder builder = new StringBuilder();

    builder.append(String.format("%s%s ", tgParamCmdLineDefString, params.tgParam));
    builder.append(String.format("%s%s ", urtParamCmdLineDefString, params.urtParam));
    builder.append(String.format("%s%s ", dumpFileCmdLineDefString, params.dumpFile));
    builder.append(String.format("%s%s ", jMeterHomeCmdLineDefString, params.jmeterHome));
    builder.append(String.format("%s%s ", lbIpCmdLineDefString, params.lbIp));
    builder.append(String.format("%s%s ", lbPortCmdLineDefString, params.lbPort));
    builder.append(String.format("%s\"%s\" ", wsTestStrCmdLineDefString, params.wsTestStr));
    builder.append(String.format("%s%s ", testDurationCmdLineDefString, params.testDuration));
    builder.append(String.format("%s%s ", testDelayCmdLineDefString, params.testDelay));
    builder.append(String.format("%s%s", totalArgsCmdLineDefString, params.totalArgs));

    return builder.toString();
  }

  public RunConfigParams convertToParams(String paramsString) {
    String tgParam = "dump";
    String urtParam = "dump";
    String dumpFile = "dump";
    String jmeterHome = "dump";
    String lbIp = "dump";
    String lbPort = "dump";
    String wsTestStr = "dump";
    String testDuration = "dump";
    String testDelay = "dump";
    String totalArgs = "dump";

    Matcher matcher = tgParamPattern.matcher(paramsString);

    if (matcher.find()) {
      tgParam = matcher.group(1);
    }

    matcher = urtParamPattern.matcher(paramsString);

    if (matcher.find()) {
      urtParam = matcher.group(1);
    }

    matcher = dumpFilePattern.matcher(paramsString);

    if (matcher.find()) {
      dumpFile = matcher.group(1);
    }

    matcher = jMeterHomePattern.matcher(paramsString);

    if (matcher.find()) {
     jmeterHome = matcher.group(1);
    }

    matcher = lbIpPattern.matcher(paramsString);

    if (matcher.find()) {
      lbIp = matcher.group(1);
    }

    matcher = lbPortPattern.matcher(paramsString);

    if (matcher.find()) {
      lbPort = matcher.group(1);
    }

    matcher = wsTestStrPattern.matcher(paramsString);

    if (matcher.find()) {
      wsTestStr = matcher.group(1);
    }

    matcher = testDurationPattern.matcher(paramsString);

    if (matcher.find()) {
      testDuration = matcher.group(1);
    }

    matcher = testDelayPattern.matcher(paramsString);

    if (matcher.find()) {
      testDelay = matcher.group(1);
    }

    matcher = totalArgsPattern.matcher(paramsString);

    if (matcher.find()) {
      totalArgs = matcher.group(1);
    }

    return new RunConfigParams(tgParam, urtParam, dumpFile, jmeterHome, lbIp, lbPort, wsTestStr,
        testDuration, testDelay, totalArgs);
  }
}
