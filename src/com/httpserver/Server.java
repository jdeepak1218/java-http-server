package com.httpserver;

public class Server {

  public static void main(String args[]) {
    int port = 8080;
    String staticDir = "static";
    if (args.length >= 1) {
      try {
        port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid port. Using default : 8080");
      }
    }
    if (args.length >= 2) {
      staticDir = args[1];
    }
    System.out.println("Serving files from: " + staticDir);
    System.out.println("Server running on port : " + port);
    HttpServer server = new HttpServer(port, staticDir);
    server.start();
  }
}
