package com.ecorz.stressapp.stresstestagent.prometheus;

import com.ecorz.stressapp.stresstestagent.prometheus.PromFields.PromMetaFields;
import com.ecorz.stressapp.stresstestagent.prometheus.connection.Util;
import com.ecorz.stressapp.stresstestagent.prometheus.connection.json.Delimiters;
import com.ecorz.stressapp.stresstestagent.prometheus.connection.json.Delimiters.DelType;
import com.ecorz.stressapp.stresstestagent.prometheus.connection.json.JsonHandler;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Client {
  private final String restApiEnd;
  private final String queryCmdIdent;

  public Client(String restApiEnd, String queryCmdIdent) {
    this.restApiEnd = restApiEnd;
    this.queryCmdIdent = queryCmdIdent;
  }

  public List<String> executePost(PromMetaFields metaFields, String queryString) throws PrometheusException {
    try {
      URL url = Util.generateUrl(buildUrl(queryString));
      URLConnection uc = Util.buildConnection(url, metaFields.getPromUser(),
          metaFields.getPromPassword());
      Delimiters dels = new Delimiters(DelType.EDGE, "\\}\\]");
      List<String> content = JsonHandler.getFieldContent(uc.getInputStream(), "values", dels);

      return content;
    } catch (MalformedURLException e) {
      throw new PrometheusException(String.format("%s is a malformed URL, which cannot be queried", buildUrl(queryString)), e);
    } catch (IOException e) {
      throw new PrometheusException(String.format("Problem reading post response from URL: %s", buildUrl(queryString)), e);
    }
  }

  private String buildUrl(String queryString) {
    return String.format("http://%s/%s=%s", restApiEnd, queryCmdIdent, queryString);
  }
}
