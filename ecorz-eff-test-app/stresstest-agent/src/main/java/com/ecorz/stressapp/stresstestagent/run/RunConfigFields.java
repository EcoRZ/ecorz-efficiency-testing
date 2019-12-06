package com.ecorz.stressapp.stresstestagent.run;

import com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.OptAndArgs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunConfigFields {
  private final BenchmarkContainer container;
  private final List<String> stuff;

  public static class RunConfigFieldsFactory {

    public RunConfigFields createBM1_stuff1(String argForOpt1, List<String> stuff) {
      OptAndArgs optAndArgs1 = new OptAndArgs(BMOption.OPT_1, argForOpt1);
      List<OptAndArgs> optAndArgs = Arrays.asList(optAndArgs1);
      return new RunConfigFields(BenchmarkContainer.BENCH_1, optAndArgs, stuff);
    }
  }

  private RunConfigFields(BenchmarkContainer container, List<OptAndArgs> optAndArgsList, List<String> stuff) {
    this.container = container;

    for(OptAndArgs optAndArgs: optAndArgsList) {
      for(String arg: optAndArgs.args) {
        container.addOptArg(optAndArgs.opt, arg);
      }
    }

    this.stuff = stuff;
  }

  public BenchmarkContainer getContainer() {
    return container;
  }

  public List<String> getStuff() {
    return stuff;
  }
}
