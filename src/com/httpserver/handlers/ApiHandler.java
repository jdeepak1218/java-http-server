package com.httpserver.handlers;

import com.httpserver.Handler;
import com.httpserver.HttpRequest;
import com.httpserver.HttpResponse;
import java.util.List;

public class ApiHandler implements Handler {

  private static List<String> users = List.of("Alice", "Bob", "Charlie");

  @Override
  public HttpResponse handle(HttpRequest request) {
    String json = buildJson(users);
    return HttpResponse.ok(json, "application/json");
  }

  private String buildJson(List<String> users) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < users.size(); i++) {
      sb.append("\"").append(users.get(i)).append("\"");
      if (i < users.size() - 1) {
        sb.append(",");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
