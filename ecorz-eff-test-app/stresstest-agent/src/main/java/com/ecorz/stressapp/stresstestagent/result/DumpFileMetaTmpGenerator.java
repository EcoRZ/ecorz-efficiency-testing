package com.ecorz.stressapp.stresstestagent.result;

import com.ecorz.stressapp.common.result.ResultException;
import com.ecorz.stressapp.common.run.benchmarks.BMOption;
import com.ecorz.stressapp.common.run.benchmarks.BenchmarkContainer;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumpFileMetaTmpGenerator {
  private final static Logger LOGGER = LoggerFactory.getLogger(DumpFileMetaTmpGenerator.class);
  private final static String header = "meta-info";
  private final BenchmarkContainer container;

  public DumpFileMetaTmpGenerator(
      BenchmarkContainer container) {
    this.container = container;
  }

  public String generate() throws ResultException {
    final Map<BMOption,List<String>> setParams = container.getOptAndArgsMap();

    if(setParams.get(BMOption.urt) == null ||
        setParams.get(BMOption.tg) == null) {
      throw new ResultException(String.format("Cannot build meta-file content"
          + " as bm args are not fully set"));
    }

    StringBuilder sb = new StringBuilder();
    sb.append(header + "\n");
    sb.append(String.format("%s\n", container));
    sb.append(String.format("%s: %s\n", "threadGroup[num-of-threads, rampUpPeriod, unused]:", setParams.get(BMOption.tg)));
    sb.append(String.format("%s: %s\n", "uniform-random-timer(unused)[rand-del-max, const-delay-off, unused]", setParams.get(BMOption.urt)));

    return sb.toString();
  }
}
