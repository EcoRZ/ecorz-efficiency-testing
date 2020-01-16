package com.ecorz.stressapp.common.run.benchmarks;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// todo: use builder instead
public class OptAndArgs {
  public final BMOption opt;
  public final List<String> args;

  public OptAndArgs(BMOption opt, List<String> args) {
    this.opt = opt;
    this.args = args;
  }

  public static List<OptAndArgs> of(Map<BMOption,List<String>> optArgsMap) {
    List<OptAndArgs> optAndArgsList = optArgsMap.entrySet().stream().map(
        mapEntry -> new OptAndArgs(mapEntry.getKey(),
            mapEntry.getValue())).collect(Collectors.toList());

    return optAndArgsList;
  }

  public OptAndArgs(BMOption opt, String arg) {
    this.opt = opt;
    this.args = Arrays.asList(arg);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for(String arg: args) {
      sb.append(String.format("--%s %s ", opt, arg));
    }

    return  sb.toString();
  }
}
