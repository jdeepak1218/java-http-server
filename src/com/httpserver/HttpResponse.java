package com.httpserver;

public class HttpResponse {

  private int statusCode;
  private String statusMessage;
  private String contentType;
  private byte[] body;
  private String fileName;

  public HttpResponse(
    int statusCode,
    String statusMessage,
    String contentType,
    byte body[]
  ) {
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
    this.body = body;
    this.contentType = contentType;
    this.fileName = null;
  }

  public byte[] toBytes() {
    String headers =
      "HTTP/1.1 " +
      statusCode +
      " " +
      statusMessage +
      "\r\n" +
      "Content-Type: " +
      contentType +
      "; charset=UTF-8\r\n" +
      "Content-Length: " +
      body.length +
      "\r\n" +
      (fileName != null
        ? "Content-Disposition: attachment; filename=\"" + fileName + "\"\r\n"
        : "") +
      "\r\n";

    byte[] headerBytes = headers.getBytes();
    byte[] response = new byte[headerBytes.length + body.length];
    System.arraycopy(headerBytes, 0, response, 0, headerBytes.length);
    System.arraycopy(body, 0, response, headerBytes.length, body.length);
    return response;
  }

  public static HttpResponse ok(String body, String contentType) {
    try {
      return new HttpResponse(200, "OK", contentType, body.getBytes("UTF-8"));
    } catch (Exception e) {
      return new HttpResponse(200, "OK", contentType, body.getBytes());
    }
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public static HttpResponse notFound() {
    return new HttpResponse(
      404,
      "Not Found",
      "text/html",
      "<h1>404 Not Found</h1>".getBytes()
    );
  }

  public static HttpResponse serverError() {
    return new HttpResponse(
      500,
      "Internal Server Error",
      "text/html",
      "<h1>500 Server Error</h1>".getBytes()
    );
  }

  public int getStatusCode() {
    return statusCode;
  }
}
