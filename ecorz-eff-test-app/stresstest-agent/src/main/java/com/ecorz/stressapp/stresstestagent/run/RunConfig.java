package com.ecorz.stressapp.stresstestagent.run;

import com.ecorz.stressapp.stresstestagent.domain.run.RunConfigFields;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ecorz.stressapp.common.run.benchmarks.BMOption;
import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.common.run.benchmarks.OptAndArgs;

public class RunConfig {
  private final static Logger LOGGER = LoggerFactory.getLogger(RunConfig.class);

  private final UUID uuid;
  private final BenchmarkContainer container;
  private final List<String> stuff;

  public static class RunConfigFactory {

    public static RunConfig createThreadGroupConfig(UUID uuid, ArgsWrapper wrapper, List<String> stuff) {
      OptAndArgs optAndArgs1 = new OptAndArgs(BMOption.tg, Arrays.asList(wrapper.arg1, wrapper.arg2,
          wrapper.arg3));
      List<OptAndArgs> optAndArgsList = Arrays.asList(optAndArgs1);
      return new RunConfig(uuid, BenchmarkContainer.TGROUP_BENCH, optAndArgsList, stuff);
    }

    public static RunConfig createURandomTimerConfig(UUID uuid, ArgsWrapper wrapper, List<String> stuff) {
      OptAndArgs optAndArgs1 = new OptAndArgs(BMOption.urt, Arrays.asList(wrapper.arg1, wrapper.arg2,
          wrapper.arg3));
      List<OptAndArgs> optAndArgsList = Arrays.asList(optAndArgs1);
      return new RunConfig(uuid, BenchmarkContainer.URT_BENCH, optAndArgsList, stuff);
    }

    public static RunConfig ofDomain(UUID uuid, RunConfigFields fields) {
      BenchmarkContainer container = BenchmarkContainer.valueOf(fields.getBmName());

      switch(container) {
        case TGROUP_BENCH:
          return createThreadGroupConfig(uuid, new ArgsWrapper(fields.getArg1(), fields.getArg2(),
              fields.getArg3()), fields.getStuff());
        case URT_BENCH:
          return createURandomTimerConfig(uuid, new ArgsWrapper(fields.getArg1(), fields.getArg2(),
              fields.getArg3()), fields.getStuff());
        default:
          throw new IllegalArgumentException(String.format("Cannot convert %s to Benchmark type", fields.getBmName()));
      }
    }

    public static RunConfig ofFile(UUID uuid, File file_) {
      LOGGER.warn("ofFile method not implemented yet, falling back to building empty RunConfig object ...");
      return new RunConfig();
    }
  }

  private RunConfig() {
    LOGGER.warn("Use empty constructor temporarily");

    this.uuid = UUID.randomUUID();
    this.container = BenchmarkContainer.NOT_IMPLEMENTED;
    this.stuff = new ArrayList<>();
  }

  private RunConfig(UUID uuid, BenchmarkContainer container, List<OptAndArgs> optAndArgsList, List<String> stuff) {
    this.uuid = uuid;
    this.container = container;

    for(OptAndArgs optAndArgs: optAndArgsList) {
      List<String> args = new ArrayList<>();
      for(String arg: optAndArgs.args) {
        args.add(arg);
      }
      container.replaceOptArgs(optAndArgs.opt, args);
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
