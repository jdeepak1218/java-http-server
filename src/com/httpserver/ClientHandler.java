package com.httpserver;

import com.httpserver.HttpRequest;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private Socket clientSocket;
  private String staticDir;

  public ClientHandler(Socket clientSocket, String staticDir) {
    this.clientSocket = clientSocket;
    this.staticDir = staticDir;
  }

  @Override
  public void run() {
    try {
      InputStream inputStream = clientSocket.getInputStream();
      OutputStream outputStream = clientSocket.getOutputStream();
      HttpRequest request = new HttpRequest(inputStream);
      long startTime = System.currentTimeMillis();
      Router router = new Router(staticDir);
      HttpResponse response = router.route(request);
      outputStream.write(response.toBytes());
      outputStream.flush();
      long duration = System.currentTimeMillis() - startTime;
      Logger.log(
        request.getMethod(),
        request.getPath(),
        response.getStatusCode(),
        duration
      );
    } catch (IOException e) {
      System.out.println("Client error : " + e.getMessage());
    } finally {
      try {
        clientSocket.close();
      } catch (IOException e) {
        System.out.println("Error closing socket : " + e.getMessage());
      }
    }
  }
}
