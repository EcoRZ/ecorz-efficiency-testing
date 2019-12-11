package com.ecorz.stressapp.stresstestagent.engines.jmeter;

import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import com.ecorz.stressapp.stresstestagent.result.ResultFile;
import java.io.File;
import java.io.IOException;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JMeterEngineWrapper {

  @Autowired
  RunServiceConfig config;

  public void exampleRun() {
    //JMeter Engine
    StandardJMeterEngine jmeter = new StandardJMeterEngine();

    //JMeter initialization (properties, log levels, locale, etc)
    JMeterUtils.loadJMeterProperties(
        "stresstest-agent/target/classes/com/ecorz/stressapp/stresstestagent/engines/jmeter/config/jmeter.properties");
    JMeterUtils.initLocale();

    // JMeter Test Plan, basic all u JOrphan HashTree
    HashTree testPlanTree = new HashTree();

    // HTTP Sampler
    HTTPSampler httpSampler = new HTTPSampler();
    httpSampler.setDomain(config.getLbIp());
    httpSampler.setPort(Integer.valueOf(config.getLbPort()));
    httpSampler.setPath("/");
    httpSampler.setMethod("GET");

    // Loop Controller
    LoopController loopController = new LoopController();
    loopController.setLoops(5);
    loopController.addTestElement(httpSampler);
    loopController.setFirst(true);
    loopController.initialize();

    // Thread Group
    ThreadGroup threadGroup = new ThreadGroup();
    threadGroup.setNumThreads(10);
    threadGroup.setRampUp(2);
    threadGroup.setSamplerController(loopController);

    // Test Plan
    TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");

    // Construct Test Plan from previously initialized elements
    testPlanTree.add("testPlan", testPlan);
    testPlanTree.add("loopController", loopController);
    testPlanTree.add("threadGroup", threadGroup);
    testPlanTree.add("httpSampler", httpSampler);

    // Run Test Plan
    jmeter.configure(testPlanTree);
    jmeter.run();
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
