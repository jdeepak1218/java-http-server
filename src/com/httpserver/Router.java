package com.httpserver;

import com.httpserver.handlers.ApiHandler;
import com.httpserver.handlers.StaticFileHandler;
import java.util.HashMap;
import java.util.Map;

public class Router {

  private Map<String, Handler> routes = new HashMap<>();
  private String staticDir;

  public Router(String staticDir) {
    this.staticDir = staticDir;
    routes.put("/api/users", new ApiHandler());
  }

  public HttpResponse route(HttpRequest request) {
    String path = request.getPath();
    if (routes.containsKey(path)) {
      return routes.get(path).handle(request);
    }
    StaticFileHandler staticHandler = new StaticFileHandler(staticDir);
    return staticHandler.handle(request);
  }
}
