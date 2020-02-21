package com.ecorz.stressapp.stresstestagent.engines;

import com.ecorz.stressapp.common.run.RunException;
import com.ecorz.stressapp.stresstestagent.config.JMeterConfig;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//TODO: Create Conversion logic without using scripts
@Component
public final class ConversionEngine {

  private static final String jtlExtension = ".jtl";
  private static final String xmlAppend = "-summary.xml";
  private static final String csvExtension = ".jmx";

  @Autowired
  private JMeterConfig jMeterConfig;

  public void removeFatJtlFile(String dumpFolder, String fileNameStripped) throws RunException {
    final String absFilePath = dumpFolder + "/" + fileNameStripped + jtlExtension;

    File file = new File(absFilePath);

    if(!file.delete()) {
      throw new RunException(String.format("Cannot delete file: %s", absFilePath));
    }
  }

  public void convertXmlToCsv(String dumpFolder, String fileNameStripped) throws RunException {
    final String absFilePathXml = dumpFolder + "/" + fileNameStripped + xmlAppend;
    final String absFilePathCsv = dumpFolder + "/" + fileNameStripped + csvExtension;
    final String awkOpts = absFilePathXml + " " + absFilePathCsv;
    final String runStr = "awk " + awkOpts;


  }
}
