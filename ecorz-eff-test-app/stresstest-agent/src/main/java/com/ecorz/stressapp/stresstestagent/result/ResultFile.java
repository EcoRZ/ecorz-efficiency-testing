package com.ecorz.stressapp.stresstestagent.result;

import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ResultFile {
  private final String fullFileName;

  public static class ResultsFileFactory {
    public static ResultFile jMeter(String directory, BenchmarkContainer container) {
      String strDate = generateDateString();

      final String finalName = String.format("%s/%s-run-%s", directory, strDate, container);
      return new ResultFile(finalName);
    }

    public static ResultFile prometheus(String directory) {
      String strDate = generateDateString();

      final String finalName = String.format("%s/%s-prometheus", directory, strDate);
      return new ResultFile(finalName);
    }

    private static String generateDateString() {
      Date date = Calendar.getInstance().getTime();
      DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss", Locale.GERMANY);
      return dateFormat.format(date);
    }
  }

  private ResultFile(String fullFileName) {
   this.fullFileName = fullFileName;
  }

  public String getFullFileName() {
    return fullFileName;
  }
}
