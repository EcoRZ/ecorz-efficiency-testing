package com.ecorz.stressapp.common.converters;

import static com.ecorz.stressapp.common.Environment.fileByIdEnvVar;

import com.ecorz.stressapp.common.run.RunFileParams;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgentToEngineFile {
  public final static AgentToEngineFile INSTANCE = new AgentToEngineFile();

  public final static String fileByIdCmdLineDefString = String.format("-D%s=", fileByIdEnvVar);

  private final static Pattern fileByIdPattern = Pattern.compile(fileByIdCmdLineDefString + "([^\\s]+)");

  private AgentToEngineFile() {
  }

  public String convertToString(RunFileParams params) {
    StringBuilder builder = new StringBuilder();

    builder.append(String.format("%s%s ", fileByIdCmdLineDefString, params.fileById.getName()));

    return builder.toString();
  }

  public RunFileParams convertToParams(String paramsString) {
    File fileById = new File("dump");

    Matcher matcher = fileByIdPattern.matcher(paramsString);

    if (matcher.find()) {
      File fileById_ = new File(matcher.group(1));
      fileById = fileById;
    }

    return new RunFileParams(fileById);
  }
}
