package com.ecorz.stressapp.stresstestagent.engines.jmeter;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.ecorz.stressapp.stresstestagent.config.JMeterConfig;
import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import com.ecorz.stressapp.stresstestagent.engines.jmeter.config.CustomThreadGroupElement;
import com.ecorz.stressapp.stresstestagent.engines.jmeter.config.DefaultHttpSamplerElement;
import com.ecorz.stressapp.stresstestagent.engines.jmeter.config.DefaultLoopElement;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import com.ecorz.stressapp.stresstestagent.run.RunException;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption;
import com.lithium.mineraloil.jmeter.JMeterRunner;
import com.lithium.mineraloil.jmeter.test_elements.HTTPSamplerElement;
import com.lithium.mineraloil.jmeter.test_elements.LoopElement;
import com.lithium.mineraloil.jmeter.test_elements.ThreadGroupElement;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.ResultSaver;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.timers.UniformRandomTimer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JMeterEngineWrapper {
  private final static Logger LOGGER = LoggerFactory.getLogger(JMeterEngineWrapper.class);

  @Autowired
  RunServiceConfig runServiceConfig;
  @Autowired
  JMeterConfig jmeterConfig;

  public void runWithConfig(Map<BMOption, List<String>> optAndArgsMap, String dumpFile) throws RunException {
    JMeterRunner jmeter = new JMeterRunner(dumpFile, jmeterConfig.getJmeterHome());

    checkValidOpt(optAndArgsMap, BMOption.urt);
    LOGGER.warn(String.format("%s option/step cannot be set at the current implementation state.", BMOption.urt));

    // todo: Configure testString
    HTTPSamplerElement httpSamplerElement = DefaultHttpSamplerElement.INSTANCE.create(
"particular configuration snippets which manage modules, global configuration",
          runServiceConfig.getLbIp(), Integer.valueOf(runServiceConfig.getLbPort())
    );

    LoopElement loopElement = DefaultLoopElement.INSTANCE.create(-1);
    loopElement.addStep(httpSamplerElement);

    checkValidOpt(optAndArgsMap, BMOption.tg);
    CustomThreadGroupElement.Fields fields = CustomThreadGroupElement.Fields.builder().
        numOfThreads(Integer.valueOf(optAndArgsMap.get(BMOption.tg).get(0))).
        duration(Integer.valueOf(jmeterConfig.getTestDuration())).
        rampUpPeriod(Integer.valueOf(optAndArgsMap.get(BMOption.tg).get(1))).
        startUpDel(Integer.valueOf(jmeterConfig.getTestDelay())).build();
    ThreadGroupElement threadGroupElement = new CustomThreadGroupElement(fields).create();
    threadGroupElement.addReportableStep(loopElement);

    jmeter.addStep(threadGroupElement);
    jmeter.run();
    //Assert.assertTrue("Test run failed. Error rate: " + jmeter.getSummaryResults().getErrorRate(),
    //    jmeter.getSummaryResults().isSuccessful());
  }

  private void checkValidOpt(Map<BMOption, List<String>> optAndArgsMap, BMOption opt) throws RunException {
    checkState(optAndArgsMap.containsKey(opt), String.format("opt: %s is not set", opt));
    checkNotNull(optAndArgsMap.get(opt), String.format("No args list is allocated for opt: %s", opt));
    checkState(Integer.valueOf(runServiceConfig.getTotalArgs()) == optAndArgsMap.get(opt).size(),
        String.format("Required args size %d does not match gotten args size %d",
            Integer.valueOf(runServiceConfig.getTotalArgs()), optAndArgsMap.get(opt).size()));

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
    jmeter.run(); */
  }
}
