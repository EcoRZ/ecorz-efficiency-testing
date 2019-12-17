package com.ecorz.stressapp.stresstestagent.engines.jmeter.config;

import com.lithium.mineraloil.jmeter.test_elements.LoopElement;

public class DefaultLoopElement {
  public static DefaultLoopElement INSTANCE = new DefaultLoopElement();

  private DefaultLoopElement() {
  }

  public LoopElement create(int loopCount) {
    return LoopElement.builder().
        name("loop forever").
        loopCount(loopCount).
        isFirst(true).build();
  }

}
