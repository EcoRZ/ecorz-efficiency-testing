package com.ecorz.stressapp.stresstestagent.prometheus.connection.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonHandler {

  private JsonHandler() {
  }

  // if same fieldname exists in different hierarchies, top-hierarchy is chosen
  public static List<String> getFieldContent(InputStream in, String fieldName,
      Delimiters dels) throws IOException {
    final List<String> returnList = new ArrayList<>();
    final String toStringConv = convertToString(in);

    final Pattern fieldPattern = generatePattern(fieldName, dels);
    final Matcher matcher = fieldPattern.matcher(toStringConv);

    if (matcher.find()) {
      String arrString = matcher.group(1);
      String[] arr = arrString.split(",");
      returnList.addAll(Arrays.asList(arr));
    }

    return returnList;
  }

  private static Pattern generatePattern(String fieldName, Delimiters dels) {
    final String strPattern = "\"" + fieldName + "\":" + dels.startDel +
        "(.*)" + dels.endDel + dels.consecStrPattern;
    Pattern fieldPattern = Pattern.compile(strPattern);

    return fieldPattern;
  }

  private static String convertToString(InputStream in) throws IOException {
    String inputLine;
    StringBuilder sb = new StringBuilder();

    BufferedReader inReader = new BufferedReader(
        new InputStreamReader(in));

    while ((inputLine = inReader.readLine()) != null) {
      sb.append(inputLine + "\n");
    }
    in.close();

    return sb.toString();
  }
}
