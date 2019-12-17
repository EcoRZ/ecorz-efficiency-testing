package com.ecorz.stressapp.stresstestagent.engines.jmeter.config;

import com.ecorz.stressapp.stresstestagent.config.JMeterConfig;
import com.ecorz.stressapp.stresstestagent.config.RunServiceConfig;
import com.lithium.mineraloil.jmeter.test_elements.HTTPSamplerElement;
import com.lithium.mineraloil.jmeter.test_elements.ResponseAssertionElement;
import com.lithium.mineraloil.jmeter.test_elements.ResponseField;
import com.lithium.mineraloil.jmeter.test_elements.ResponsePatternType;
import com.lithium.mineraloil.jmeter.test_elements.ResponseSampleType;
import example.com.mycompany.myproject.performance.steps.AssertionSteps;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultHttpSamplerElement {

  public static DefaultHttpSamplerElement INSTANCE = new DefaultHttpSamplerElement();

  private DefaultHttpSamplerElement() {
  }

  public HTTPSamplerElement create(String assertString, String domain, int port) {
    return HTTPSamplerElement.builder()
        .protocol("http")
        .domain(domain)
        .port(port)
        .path("/")
        .useKeepAlive(true)
        .followRedirects(true)
        .doMultiPartPost(true)
        .method("GET")
        .build()
        .addStep(buildAssertionElem(assertString));
  }

  private ResponseAssertionElement buildAssertionElem(String substr) {
    return ResponseAssertionElement.builder().
        responseSampleType(ResponseSampleType.MAIN_SAMPLE).
        responseField(ResponseField.TEXT).
        responsePatternType(ResponsePatternType.SUBSTRING).
        ignoreStatus(false).
        not(false).
        testString(substr).
        name("Assert body contains substr").
        build();
  }
}
