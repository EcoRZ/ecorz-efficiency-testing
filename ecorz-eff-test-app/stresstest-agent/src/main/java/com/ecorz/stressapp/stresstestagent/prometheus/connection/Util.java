package com.ecorz.stressapp.stresstestagent.prometheus.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

public class Util {

  private Util() {
  }

  public static URL generateUrl(String urlString) throws MalformedURLException {
    return new URL(urlString);
  }

  public static URLConnection buildConnection(URL url) throws IOException {
    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
    uc.setRequestMethod("POST");
    uc.setRequestProperty("Content-Type",
        "application/json");
    uc.setRequestProperty("Accept",
        "application/json");
    return  uc;
  }

  public static URLConnection buildConnection(URL url, String username, String password)
      throws IOException {
    URLConnection uc = buildConnection(url);
    String userpass = username + ":" + password;
    String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
    uc.setRequestProperty ("Authorization", basicAuth);

    return uc;
  }
}
