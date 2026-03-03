package com.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class HttpRequest {

  private String method;
  private String path;
  private Map<String, String> headers = new HashMap<>();

  public HttpRequest(InputStream inputStream) throws IOException {
    BufferedReader reader = new BufferedReader(
      new InputStreamReader(inputStream)
    );
    String reqLine = reader.readLine();
    if (reqLine == null || reqLine.isEmpty()) {
      this.method = "Unknown";
      this.path = "/";
      return;
    }
    String parts[] = reqLine.split(" ");
    this.method = parts[0];
    this.path = parts[1];
    String headLine;
    while ((headLine = reader.readLine()) != null && !headLine.isEmpty()) {
      String header[] = headLine.split(": ", 2);
      if (header.length == 2) {
        headers.put(header[0], header[1]);
      }
    }
  }

  public String getMethod() {
    return method;
  }

  public String getPath() {
    return path;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }
}
