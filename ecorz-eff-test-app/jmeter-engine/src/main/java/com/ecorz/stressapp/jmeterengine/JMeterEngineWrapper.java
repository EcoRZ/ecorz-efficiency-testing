package com.ecorz.stressapp.jmeterengine;

import com.ecorz.stressapp.jmeterengine.config.CustomThreadGroupElement;
import com.ecorz.stressapp.jmeterengine.config.DefaultHttpSamplerElement;
import com.ecorz.stressapp.jmeterengine.config.DefaultLoopElement;
import com.google.common.base.Preconditions;
import com.lithium.mineraloil.jmeter.JMeterRunner;
import com.lithium.mineraloil.jmeter.test_elements.HTTPSamplerElement;
import com.lithium.mineraloil.jmeter.test_elements.LoopElement;
import com.lithium.mineraloil.jmeter.test_elements.ThreadGroupElement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ecorz.stressapp.common.run.RunConfigParams;
import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.common.run.benchmarks.BMOption;

public class JMeterEngineWrapper {
  private final static Logger LOGGER = LoggerFactory.getLogger(JMeterEngineWrapper.class);

  public void runWithConfig(Map<BMOption, List<String>> optAndArgsMap, String dumpFile, RunConfigParams configParams) throws RunException {
    JMeterRunner jmeter = new JMeterRunner(dumpFile, configParams.jmeterHome);

    checkValidOpt(optAndArgsMap, BMOption.urt, configParams.totalArgs);
    LOGGER.warn(String.format("%s option/step cannot be set at the current implementation state.", BMOption.urt));

    // todo: Configure testString
    HTTPSamplerElement httpSamplerElement = DefaultHttpSamplerElement.INSTANCE.create(
"particular configuration snippets which manage modules, global configuration",
          configParams.lbIp, Integer.valueOf(configParams.lbPort)
    );

    LoopElement loopElement = DefaultLoopElement.INSTANCE.create(-1);
    loopElement.addStep(httpSamplerElement);

    checkValidOpt(optAndArgsMap, BMOption.tg, configParams.totalArgs);
    CustomThreadGroupElement.Fields fields = CustomThreadGroupElement.Fields.builder().
        numOfThreads(Integer.valueOf(optAndArgsMap.get(BMOption.tg).get(0))).
        duration(Integer.valueOf(configParams.testDuration)).
        rampUpPeriod(Integer.valueOf(optAndArgsMap.get(BMOption.tg).get(1))).
        startUpDel(Integer.valueOf(configParams.testDelay)).build();
    ThreadGroupElement threadGroupElement = new CustomThreadGroupElement(fields).create();
    threadGroupElement.addReportableStep(loopElement);

    jmeter.addStep(threadGroupElement);
    jmeter.run();
    //Assert.assertTrue("Test com.ecorz.stressapp.common.run failed. Error rate: " + jmeter.getSummaryResults().getErrorRate(),
    //    jmeter.getSummaryResults().isSuccessful());
  }

  private void checkValidOpt(Map<BMOption, List<String>> optAndArgsMap, BMOption opt, String totalArgs) throws RunException {
    Preconditions.checkState(optAndArgsMap.containsKey(opt), String.format("opt: %s is not set", opt));
    Preconditions.checkNotNull(optAndArgsMap.get(opt), String.format("No args list is allocated for opt: %s", opt));
    Preconditions
        .checkState(Integer.valueOf(totalArgs) == optAndArgsMap.get(opt).size(),
        String.format("Required args size %d does not match gotten args size %d",
            Integer.valueOf(totalArgs), optAndArgsMap.get(opt).size()));

    for(String elem: optAndArgsMap.get(opt)) {
      try {
        Integer.parseInt(elem);
      } catch (NumberFormatException nfe) {
        throw new RunException(nfe.getMessage());
      }
    }
  }

  public void runWithJmxFile(File file) throws IOException {
    LOGGER.warn("Method runWithJmxFile() needs to get implemented");
    //TODO
    /* JMeterUtils.loadJMeterProperties(
        jmeterConfig.getJmeterHomeBin() + "/jmeter.properties");
    JMeterUtils.initLocale();
    JMeterUtils.setJMeterHome(jmeterConfig.getJmeterHome());

    SaveService.loadProperties();

    HashTree testPlanTree = SaveService.loadTree(file);

    StandardJMeterEngine jmeter = new StandardJMeterEngine();
    jmeter.configure(testPlanTree);
    jmeter.com.ecorz.stressapp.common.run(); */
  }
}
