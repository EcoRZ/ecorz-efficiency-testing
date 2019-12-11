package com.ecorz.stressapp.stresstestagent.domain.run;

import static com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption.tg;

import com.ecorz.stressapp.stresstestagent.run.RunConfig;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BenchmarkContainer;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.OptAndArgs;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RunConfigFields {
  @JsonProperty
  String bmName;
  @JsonProperty
  String arg1;
  @JsonProperty
  String arg2;
  @JsonProperty
  String arg3;
  @JsonProperty
  List<String> stuff;

  public static RunConfigFields ofConfig(RunConfig config) {
    RunConfigFields fields = new RunConfigFields();

    BenchmarkContainer container = config.getContainer();
    String bmNameTmp = container.toString();
    fields.bmName(bmNameTmp);

    String arg1Tmp,arg2Tmp, arg3Tmp ;
    // todo: get the hard coded info which option corresponds to arg1, arg2, arg3 from global config
    switch(container) {
      // todo: handle this with Optional
      case BENCH_1: {
        checkOptSet(container, tg);
        checkArgsSet(container, container.getOptAndArgsMap().entrySet().
            stream().filter(entry -> entry.getKey() == tg).
            map(entry -> new OptAndArgs(entry.getKey(), entry.getValue())).
            collect(Collectors.toList()).get(0), 3);
        arg1Tmp = container.getOptAndArgsMap().get(tg).get(0);
        arg2Tmp = container.getOptAndArgsMap().get(tg).get(1);
        arg3Tmp = container.getOptAndArgsMap().get(tg).get(2);
        break;
      }
      case BENCH_2:
      default:
        throw new IllegalArgumentException(String.format("Arg field generation from Config not implemented for %s yet.", container.toString()));
    }
    fields.arg1(arg1Tmp);
    fields.arg2(arg2Tmp);
    fields.arg3(arg3Tmp);

    fields.stuff(config.getStuff());

    return fields;
  }

  public RunConfigFields bmName(String bmName) {
    this.bmName = bmName;
    return this;
  }

  public RunConfigFields arg1(String arg1) {
    this.arg1 = arg1;
    return this;
  }

  public RunConfigFields arg2(String arg2) {
    this.arg2 = arg2;
    return this;
  }

  public RunConfigFields arg3(String arg3) {
    this.arg3 = arg3;
    return this;
  }

  public RunConfigFields stuff(List<String> stuff) {
    this.stuff = stuff;
    return this;
  }

  public String getBmName() {
    return bmName;
  }

  public String getArg1() {
    return arg1;
  }

  public String getArg2() {
    return arg2;
  }

  public String getArg3() {
    return arg3;
  }

  public List<String> getStuff() {
    return stuff;
  }

  private static void checkOptSet(BenchmarkContainer container, BMOption opt) {
    if(container.getOptAndArgsMap().get(opt) == null) {
      throw new IllegalArgumentException(String.format("Required Option %s not set for benchmark %s",
          opt, container.toString()));
    }
  }

  private static void checkArgsSet(BenchmarkContainer container, OptAndArgs entry,
      int requiredArgsNum) {
    if(entry.args == null) {
      throw new IllegalArgumentException(String.format("No String List allocated for opt: %s in benchmark %s", entry.opt,
          container.toString()));
    }

    int actualArgsNum = entry.args.size();
    if(actualArgsNum != requiredArgsNum) {
      throw new IllegalArgumentException(String.format("Incorrect number of args set for %s. Required: %d; Got: %d",
          entry.opt.toString(), requiredArgsNum, actualArgsNum));
    }
  }
}
