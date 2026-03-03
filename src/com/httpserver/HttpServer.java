package com.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

  private int port;
  private ExecutorService threadPool;
  private String staticDir;

  public HttpServer(int port, String staticDir) {
    this.port = port;
    this.staticDir = staticDir;
    threadPool = Executors.newFixedThreadPool(10);
  }

  public void start() {
    try (
      ServerSocket serverSocket = new ServerSocket(
        port,
        50,
        java.net.InetAddress.getByName("0.0.0.0")
      )
    ) {
      System.out.println("Server is listening at port: " + port);
      while (true) {
        Socket clientSocket = serverSocket.accept();
        threadPool.submit(new ClientHandler(clientSocket, staticDir));
      }
    } catch (IOException e) {
      System.out.println("Server error : " + e.getMessage());
    }
  }
}
