package com.ecorz.stressapp.stresstestagent.prometheus;

import com.ecorz.stressapp.stresstestagent.prometheus.PromFields.PromQueryFields;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DumpFileContentGenerator {
  private final static Logger LOGGER = LoggerFactory.getLogger(DumpFileContentGenerator.class);
  private final static String header = "relativeTimestamp,absoluteTimestamp,value";
  private final static int msToSecDiv = 1000;

  private final PromQueryFields fields;
  private final List<String> rawContent;

  public DumpFileContentGenerator(
      PromQueryFields fields, List<String> rawContent) {
    this.fields = fields;
    this.rawContent = rawContent;
  }

  public String generate() throws PrometheusException {
    final Pattern tStepPattern = Pattern.compile("([0-9]+)s");
    final Matcher matcher = tStepPattern.matcher(fields.getTimeStep());

    if (!matcher.find()) {
      throw new PrometheusException(String.format("time step: %s is not given in seconds which is required", fields.getTimeStep()));
    }

    int tStepSec = Integer.valueOf(matcher.group(1));

    int binLength = ((int)(fields.getEndTimeMs() - fields.getStartTimeMs()))/msToSecDiv + 1;

    if(binLength != rawContent.size()/2) {
      LOGGER.warn(String.format("Calculated timesteps number %d and rawContent steps %d do not match", binLength, rawContent.size()/2));
    }

    StringBuilder sb = new StringBuilder();
    sb.append(header + "\n");

    Date date = new Date(fields.getStartTimeMs());
    //only odd numbers contain power values
    for(int i = 1; i < rawContent.size(); i=i+2) {
      sb.append(String.format("%.1f,%s,%s\n", (float)(i-1)*tStepSec/2,
          date.toString(),
          rawContent.get(i).replaceAll("\"", "").replaceAll("\\]", "")));
    }

    return sb.toString();
  }
}
