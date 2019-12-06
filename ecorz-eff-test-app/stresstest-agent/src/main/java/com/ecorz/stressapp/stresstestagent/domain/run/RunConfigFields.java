package com.ecorz.stressapp.stresstestagent.domain.run;

import static com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption.OPT_1;
import static com.ecorz.stressapp.stresstestagent.run.benchmarks.BenchmarkContainer.BENCH_1;

import com.ecorz.stressapp.stresstestagent.run.RunConfig;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BenchmarkContainer;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RunConfigFields {
  @JsonProperty
  String bmName;
  @JsonProperty
  String arg1;
  @JsonProperty
  List<String> stuff;

  public static RunConfigFields ofConfig(RunConfig config) {
    RunConfigFields fields = new RunConfigFields();

    BenchmarkContainer container = config.getContainer();
    String bmNameTmp = container.toString();
    fields.bmName(bmNameTmp);

    String arg1Tmp;
    // todo: get the hard coded info which option corresponds to arg1 from global config
    switch(container) {
      // todo: handle this with Optional
      case BENCH_1: {
        if(container.getOptAndArgsMap().get(OPT_1) == null ||
            container.getOptAndArgsMap().get(OPT_1).get(0) == null) {
          throw new IllegalArgumentException(String.format("No args set for %s and required Option %s", BENCH_1.toString(), OPT_1));
        }
        arg1Tmp = container.getOptAndArgsMap().get(OPT_1).get(0);
        break;
      }
      case BENCH_2:
      default:
        throw new IllegalArgumentException(String.format("Arg field generation from Config not implemented for %s yet.", container.toString()));
    }
    fields.arg1(arg1Tmp);

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

  public List<String> getStuff() {
    return stuff;
  }
}
