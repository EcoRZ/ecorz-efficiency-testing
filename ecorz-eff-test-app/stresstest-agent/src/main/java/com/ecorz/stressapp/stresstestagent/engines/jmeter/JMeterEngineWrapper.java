package com.ecorz.stressapp.stresstestagent.engines.jmeter;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import com.ecorz.stressapp.stresstestagent.run.RunException;
import com.ecorz.stressapp.stresstestagent.run.benchmarks.BMOption;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.timers.UniformRandomTimer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JMeterEngineWrapper {
  private final static Logger LOGGER = LoggerFactory.getLogger(JMeterEngineWrapper.class);

  @Autowired
  RunServiceConfig config;

  public void runWithConfig(Map<BMOption, List<String>> optAndArgsMap, String dumpFile) throws RunException {
    //JMeter Engine
    StandardJMeterEngine jmeter = new StandardJMeterEngine();

    //JMeter initialization (properties, log levels, locale, etc)
    JMeterUtils.loadJMeterProperties(
        "stresstest-agent/target/classes/com/ecorz/stressapp/stresstestagent/engines/jmeter/config/jmeter.properties");
    JMeterUtils.initLocale();

    // JMeter Test Plan, basic all u JOrphan HashTree
    HashTree testPlanTree = new HashTree();

    // ResponseAssertion
    ResponseAssertion responseAssertion = new ResponseAssertion();
    responseAssertion.addTestString("This is the default welcome page used");
    responseAssertion.setToSubstringType();
    responseAssertion.setTestFieldResponseData();

    // URT
    checkValidOpt(optAndArgsMap, BMOption.urt);
    UniformRandomTimer uniformRandomTimer = new UniformRandomTimer();
    // uniformRandomTimer.setDelay("1000");
    // uniformRandomTimer.setRange(100);
    uniformRandomTimer.setDelay(optAndArgsMap.get(BMOption.urt).get(0));
    uniformRandomTimer.setRange(optAndArgsMap.get(BMOption.urt).get(1));
    LOGGER.warn(String.format("Arg 3: %s not used yet for opt: %s",
        optAndArgsMap.get(BMOption.urt).get(2), BMOption.urt));

    // HTTP Sampler
    HTTPSampler httpSampler = new HTTPSampler();
    httpSampler.setDomain(config.getLbIp());
    httpSampler.setPort(Integer.valueOf(config.getLbPort()));
    httpSampler.setPath("/");
    httpSampler.setMethod("GET");

    // Loop Controller
    LoopController loopController = new LoopController();
    loopController.setContinueForever(true);
    loopController.addTestElement(httpSampler);
    loopController.setFirst(true);
    loopController.initialize();

    // Thread Group
    checkValidOpt(optAndArgsMap, BMOption.tg);
    ThreadGroup threadGroup = new ThreadGroup();
    threadGroup.setNumThreads(Integer.valueOf(optAndArgsMap.get(BMOption.tg).get(0)));
    threadGroup.setRampUp(Integer.valueOf(optAndArgsMap.get(BMOption.tg).get(1)));
    threadGroup.setDuration(60);
    threadGroup.setDelay(5);
    threadGroup.setSamplerController(loopController);
    LOGGER.warn(String.format("Arg 3: %s not used yet for opt: %s",
        optAndArgsMap.get(BMOption.tg).get(2), BMOption.tg.toString()));

    // Test Plan
    TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");

    // Construct Test Plan from previously initialized elements
    testPlanTree.add("testPlan", testPlan);
    testPlanTree.add("responseAssertion", responseAssertion);
    testPlanTree.add("uniformRandomTimer", uniformRandomTimer);
    testPlanTree.add("loopController", loopController);
    testPlanTree.add("threadGroup", threadGroup);
    testPlanTree.add("httpSampler", httpSampler);

    // Run Test Plan
    jmeter.configure(testPlanTree);
    jmeter.run();
  }

  private void checkValidOpt(Map<BMOption, List<String>> optAndArgsMap, BMOption opt) throws RunException {
    checkState(optAndArgsMap.containsKey(opt), String.format("opt: %s is not set", opt));
    checkNotNull(optAndArgsMap.get(opt), String.format("No args list is allocated for opt: %s", opt));
    checkState(Integer.valueOf(config.getTotalArgs()) == optAndArgsMap.get(opt).size(),
        String.format("Required args size %d does not match gotten args size %d",
            Integer.valueOf(config.getTotalArgs()), optAndArgsMap.get(opt).size()));

    for(String elem: optAndArgsMap.get(opt)) {
      try {
        Integer.parseInt(elem);
      } catch (NumberFormatException nfe) {
        throw new RunException(nfe.getMessage());
      }
    }
  }

  public void runWithJmxFile(String jmeterHome, File file) throws IOException {
    //JMeter initialization (properties, log levels, locale, etc)
    JMeterUtils.loadJMeterProperties(
        "stresstest-agent/target/classes/com/ecorz/stressapp/stresstestagent/engines/jmeter/config/jmeter.properties");
    JMeterUtils.initLocale();
    JMeterUtils.setJMeterHome(jmeterHome);

    // Initialize JMeter SaveService
    SaveService.loadProperties();

    HashTree testPlanTree = SaveService.loadTree(file);

    // Run JMeter Test
    StandardJMeterEngine jmeter = new StandardJMeterEngine();
    jmeter.configure(testPlanTree);
    jmeter.run();
  }
}
