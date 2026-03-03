package com.httpserver;

public interface Handler {
  HttpResponse handle(HttpRequest request);
}
