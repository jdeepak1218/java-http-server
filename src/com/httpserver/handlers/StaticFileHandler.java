package com.httpserver.handlers;

import com.httpserver.Handler;
import com.httpserver.HttpRequest;
import com.httpserver.HttpResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticFileHandler implements Handler {

  private String staticDir;

  public StaticFileHandler(String staticDir) {
    this.staticDir = staticDir;
  }

  @Override
  public HttpResponse handle(HttpRequest request) {
    String path = request.getPath();
    Path filePath = Path.of(staticDir + path);

    if (Files.isDirectory(filePath)) {
      String html = buildDirectoryListing(path, filePath);
      return HttpResponse.ok(html, "text/html");
    }

    if (!Files.exists(filePath)) {
      return HttpResponse.notFound();
    }

    try {
      byte[] fileBytes = Files.readAllBytes(filePath);
      String contentType = getContentType(path);
      String fileName = filePath.getFileName().toString(); // ADD
      HttpResponse response = new HttpResponse(
        200,
        "OK",
        contentType,
        fileBytes
      );
      response.setFileName(fileName); // ADD
      return response;
    } catch (IOException e) {
      return HttpResponse.serverError();
    }
  }

  private String getContentType(String path) {
    if (path.endsWith(".html")) return "text/html";
    if (path.endsWith(".css")) return "text/css";
    if (path.endsWith(".js")) return "application/javascript";
    if (path.endsWith(".json")) return "application/json";
    if (path.endsWith(".png")) return "image/png";
    if (path.endsWith(".jpg")) return "image/jpeg";
    if (path.endsWith(".pdf")) return "application/pdf";
    if (path.endsWith(".mp3")) return "audio/mpeg";
    if (path.endsWith(".mp4")) return "video/mp4";
    if (path.endsWith(".zip")) return "application/zip";
    if (path.endsWith(".txt")) return "text/plain";
    return "application/octet-stream";
  }

  private String buildDirectoryListing(String urlPath, Path dirPath) {
    StringBuilder html = new StringBuilder();

    html
      .append("<!DOCTYPE html><html><head>")
      .append("<title>Index of ")
      .append(urlPath)
      .append("</title>")
      .append("<style>")
      .append("body { font-family: Arial, sans-serif; padding: 20px; }")
      .append("h1 { border-bottom: 1px solid #ccc; padding-bottom: 10px; }")
      .append(
        "a { display: block; padding: 5px 0; text-decoration: none; color: #0066cc; }"
      )
      .append("a:hover { text-decoration: underline; }")
      .append("</style>")
      .append("</head><body>")
      .append("<h1>📁 Index of ")
      .append(urlPath)
      .append("</h1>");

    try {
      Files.list(dirPath).forEach(file -> {
        String fileName = file.getFileName().toString();
        String fileUrl = urlPath.endsWith("/")
          ? urlPath + fileName
          : urlPath + "/" + fileName;

        if (Files.isDirectory(file)) {
          html
            .append("<a href='")
            .append(fileUrl)
            .append("'>📁 ")
            .append(fileName)
            .append("/</a>");
        } else {
          long size = 0;
          try {
            size = Files.size(file);
          } catch (IOException e) {}
          html
            .append("<a href='")
            .append(fileUrl)
            .append("'>📄 ")
            .append(fileName)
            .append(" (")
            .append(formatSize(size))
            .append(")</a>");
        }
      });
    } catch (IOException e) {
      html.append("<p>Error reading directory</p>");
    }

    html.append("</body></html>");
    return html.toString();
  }

  private String formatSize(long bytes) {
    if (bytes < 1024) return bytes + " B";
    if (bytes < 1024 * 1024) return (bytes / 1024) + " KB";
    return (bytes / (1024 * 1024)) + " MB";
  }
}
