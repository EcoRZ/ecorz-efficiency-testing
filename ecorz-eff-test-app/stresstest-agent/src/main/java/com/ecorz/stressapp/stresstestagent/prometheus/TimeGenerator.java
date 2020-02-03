package com.ecorz.stressapp.stresstestagent.prometheus;

import static java.lang.Math.round;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class TimeGenerator {

  private static final int milToSecFac = 1000;

  private TimeGenerator() {
  }

  public static String generateRfc3339TimeMinusOff(int offMs) {
    int useOff = round((-1) * ((float) offMs) / ((float)milToSecFac));

    DateFormat dateFormat = generateFormat();
    Calendar cal = getCalWithOffset(useOff);

    return dateFormat.format(cal.getTime());
  }

  public static String generateRfc3339TimeMinusOff(long startTimeMs, int offMs) {
    DateFormat dateFormat = generateFormat();
    Date date = new Date(startTimeMs - offMs);

    return dateFormat.format(date);
  }

  public static String generateRfc3339TimePlusOff(int offMs) {
    int useOff = round(((float) offMs) / ((float)milToSecFac));

    DateFormat dateFormat = generateFormat();
    Calendar cal = getCalWithOffset(useOff);

    return dateFormat.format(cal.getTime());
  }

  public static String generateRfc3339TimePlusOff(long startTimeMs, int offMs) {
    DateFormat dateFormat = generateFormat();
    Date date = new Date(startTimeMs + offMs);

    return dateFormat.format(date);
  }

  public static DateFormat generateFormat() {
    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.GERMANY);
  }

  private static Calendar getCalWithOffset(int useOff) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.SECOND, useOff);

    return cal;
  }
}
