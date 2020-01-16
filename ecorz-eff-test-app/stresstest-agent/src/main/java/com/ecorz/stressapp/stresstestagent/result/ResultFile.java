package com.ecorz.stressapp.stresstestagent.result;

import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ResultFile {
  private final String fullFileName;

  public static class ResultsFileFactory {
    public static ResultFile of(String directory, BenchmarkContainer container) {
      Date date = Calendar.getInstance().getTime();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss");
      String strDate = dateFormat.format(date);

      final String finalName = String.format("%s/%s-com.ecorz.stressapp.common.run-%s", directory, strDate, container);
      return new ResultFile(finalName);
    }
  }

  private ResultFile(String fullFileName) {
   this.fullFileName = fullFileName;
  }

  public String getFullFileName() {
    return fullFileName;
  }
}
