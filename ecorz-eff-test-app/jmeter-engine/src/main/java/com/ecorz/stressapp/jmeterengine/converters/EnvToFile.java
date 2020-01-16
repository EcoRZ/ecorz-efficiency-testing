package com.ecorz.stressapp.jmeterengine.converters;

import static com.ecorz.stressapp.common.Environment.fileByIdEnvVar;

import java.io.File;

public class EnvToFile {

  public final static EnvToFile INSTANCE = new EnvToFile();

  private EnvToFile() {
  }

  public File getFileById() {
    final File fileById = System.getProperty(fileByIdEnvVar) != null ? new File(System.getProperty(fileByIdEnvVar)) : null;
    return fileById;
  }
}
