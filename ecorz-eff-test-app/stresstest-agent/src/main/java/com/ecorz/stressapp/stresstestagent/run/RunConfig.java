package com.ecorz.stressapp.stresstestagent.run;

import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFields;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.OptAndArgs;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RunConfig {
  private final UUID uuid;
  private final BenchmarkContainer container;
  private final List<String> stuff;

  public static class RunConfigFactory {

    public static RunConfig createBM1_stuff1(UUID uuid, ArgsWrapper wrapper, List<String> stuff) {
      OptAndArgs optAndArgs1 = new OptAndArgs(BMOption.tg, Arrays.asList(wrapper.arg1, wrapper.arg2,
          wrapper.arg3));
      List<OptAndArgs> optAndArgsList = Arrays.asList(optAndArgs1);
      return new RunConfig(uuid, BenchmarkContainer.BENCH_1, optAndArgsList, stuff);
    }

    public static RunConfig ofDomain(UUID uuid, RunConfigFields fields) {
      BenchmarkContainer container = BenchmarkContainer.valueOf(fields.getBmName());

      switch(container) {
        case BENCH_1:
          return createBM1_stuff1(uuid, new ArgsWrapper(fields.getArg1(), fields.getArg2(),
              fields.getArg3()), fields.getStuff());
        case BENCH_2:
        default:
          throw new IllegalArgumentException(String.format("Cannot convert %s to Benchmark type", fields.getBmName()));
      }
    }
  }

  private RunConfig(UUID uuid, BenchmarkContainer container, List<OptAndArgs> optAndArgsList, List<String> stuff) {
    this.uuid = uuid;
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

  private static class ArgsWrapper {
    public final String arg1, arg2, arg3;

    public ArgsWrapper(String arg1, String arg2, String arg3) {
      this.arg1 = arg1;
      this.arg2 = arg2;
      this.arg3 = arg3;
    }
  }
}
