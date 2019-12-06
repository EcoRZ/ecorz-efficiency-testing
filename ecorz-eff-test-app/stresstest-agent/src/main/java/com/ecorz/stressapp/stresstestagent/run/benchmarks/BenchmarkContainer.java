package com.ecorz.stressapp.stresstestagent.run.benchmarks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.base.MoreObjects;
import java.util.stream.Collectors;

public enum BenchmarkContainer {
  BENCH_1(new BMOption[]{ BMOption.OPT_1 }),
  BENCH_2(new BMOption[]{ BMOption.OPT_2 });

  private final Map<BMOption,List<String>> setParams;

  BenchmarkContainer(BMOption[] opts) {
    setParams = new HashMap<>();

    for(BMOption opt: opts) {
      setParams.put(opt, new ArrayList<>());
    }
  }

  // todo: test this
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).toString();
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
