package com.ecorz.stressapp.stresstestagent.engines.jmeter;

import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.springframework.beans.factory.annotation.Autowired;

class ExampleUtil {

  @Autowired
  RunServiceConfig config;

  void exampleRun() {
    //JMeter Engine
    StandardJMeterEngine jmeter = new StandardJMeterEngine();

    //JMeter initialization (properties, log levels, locale, etc)
    JMeterUtils.loadJMeterProperties("/path/to/your/jmeter/bin/jmeter.properties");
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
    loopController.setLoops(1);
    loopController.addTestElement(httpSampler);
    loopController.setFirst(true);
    loopController.initialize();

    // Thread Group
    ThreadGroup threadGroup = new ThreadGroup();
    threadGroup.setNumThreads(1);
    threadGroup.setRampUp(1);
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
}
