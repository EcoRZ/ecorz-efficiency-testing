package com.ecorz.stressapp.stresstestagent.engines;

import com.ecorz.stressapp.common.run.RunException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

class RunTime {

  private RunTime() {
  }

  static void callRuntime(String cmd) throws RunException {
    try {
      String tmpFileName = "tmp_file";
      BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFileName));
      writer.write(cmd);
      writer.close();

      Process process = Runtime.getRuntime().exec("sh " + tmpFileName);
      Files.deleteIfExists(Paths.get(tmpFileName));

      //Process process = Runtime.getRuntime().exec(cmd);

      StringBuilder output = new StringBuilder();

      BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getErrorStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line + "\n");
      }

      int exitVal = process.waitFor();
      if (exitVal != 0) {
        throw new RunException(String.format("Cannot execute command:\n"
            + "%s\nas return-val was not equal to zero.\nOutput was:\n%s", cmd, output.toString()));
      }

    } catch (IOException e) {
      throw new RunException(String.format("Cannot execute command:\n"
          + "%s\nas there was an I/O Exception", cmd));
    } catch (InterruptedException e) {
      throw new RunException(String.format("Cannot execute command:\n"
          + "%s\nas the command was interrupted", cmd));
    }
  }

}
