package com.ecorz.stressapp.stresstestagent.run.benchmarks;

import static com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption.tg;
import static com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption.urt;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.base.MoreObjects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BenchmarkContainer {
  BENCH_1(new HashMap<BMOption,List<String>>() {{
    put(tg, Arrays.asList("0", "0", "0")); }}),
  BENCH_2(new HashMap<BMOption,List<String>>() {{
    put(urt, Arrays.asList("0", "0")); }});

  private final Map<BMOption,List<String>> setParams;

  BenchmarkContainer(HashMap<BMOption,List<String>> initializeMap) {
    this.setParams = initializeMap;
  }

  public void addOptArg(BMOption opt, String arg) {
    if(!isAllowedOpt(opt)) {
      throw new IllegalArgumentException(String.format("%s is not a valid option for benchmark %s.", opt, this));
    }

    setParams.get(opt).add(arg);
  }

  public void removeOptArg(BMOption opt, String arg) {
    if(!isAllowedOpt(opt)) {
      throw new IllegalArgumentException(String.format("%s is not a valid option for benchmark %s.", opt, this));
    }

    setParams.put(opt, setParams.get(opt).stream().filter(
        argKeep -> argKeep != arg).collect(Collectors.toList()));
    setParams.get(opt).stream();
  }

  public Map<BMOption,List<String>> getOptAndArgsMap() {
    return setParams;
  }

  public boolean isAllowedOpt(BMOption opt) {
    return setParams.containsKey(opt);
  }
};
