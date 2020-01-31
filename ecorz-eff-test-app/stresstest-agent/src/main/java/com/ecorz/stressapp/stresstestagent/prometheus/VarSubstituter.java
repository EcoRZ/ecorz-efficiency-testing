package com.ecorz.stressapp.stresstestagent.prometheus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class VarSubstituter {

  private VarSubstituter() {
  }

  public static String substitute(String varName, String varValue, String evalString) {
    String resultString = evalString;
    List<String> regexReplacePatterns = generateSubstitutionStrings(varName);

    for (String regexReplacePattern: regexReplacePatterns) {
      resultString = resultString.replaceAll(regexReplacePattern, varValue);
    }

    return resultString;
  }

  public static String substitute(Map<String,String> varMap, String evalString) {
    String resultString = evalString;

    for (Entry<String,String> var: varMap.entrySet()) {
      resultString = substitute(var.getKey(), var.getValue(), resultString);
    }

    return resultString;
  }

  private static List<String> generateSubstitutionStrings(String varName) {
    List<String> returnList = new ArrayList<>();

    returnList.add("${" + varName + "}");
    returnList.add("$" + varName);

    return returnList;
  }
}
