package com.ecorz.stressapp.common.result;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultFileWriter {

  private ResultFileWriter() {
  }

  public static void dump(String fileName, String dumpContent) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    writer.write(dumpContent);
    writer.close();
  }
}
