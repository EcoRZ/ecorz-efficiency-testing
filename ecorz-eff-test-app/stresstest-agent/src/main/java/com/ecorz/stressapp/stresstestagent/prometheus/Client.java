package com.ecorz.stressapp.stresstestagent.prometheus;

import com.ecorz.stressapp.stresstestagent.prometheus.PromFields.PromMetaFields;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public class Client {
  private final String restApiEnd;
  private final String queryCmdIdent;

  public Client(String restApiEnd, String queryCmdIdent) {
    this.restApiEnd = restApiEnd;
    this.queryCmdIdent = queryCmdIdent;
  }

  public void executePost(PromMetaFields metaFields, String queryString) throws PrometheusException {
    CredentialsProvider provider = new BasicCredentialsProvider();
    UsernamePasswordCredentials credentials
        = new UsernamePasswordCredentials(metaFields.getPromUser(), metaFields.getPromPassword());
    provider.setCredentials(AuthScope.ANY, credentials);

    HttpClient client = HttpClientBuilder.create()
        .setDefaultCredentialsProvider(provider)
        .build();

    HttpResponse response = null;
    try {
      response = client.execute(
          new HttpPost(buildUrl(queryString)));
    } catch (IOException e) {
      throw new PrometheusException(String.format("Got I/O Exception while querying prom api"), e) ;
    }

    int statusCode = response.getStatusLine()
        .getStatusCode();

    if (statusCode != HttpStatus.SC_OK) {
      throw new PrometheusException(String.format("Querying prom api resulted in http error code: %d", statusCode));
    }

    try {
      System.out.println(String.format("%s", response.getEntity().getContent()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String buildUrl(String queryString) {
    return String.format("http://%s/%s=%s", restApiEnd, queryCmdIdent, queryString);
  }
}
