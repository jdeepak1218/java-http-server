package com.httpserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

  private static final DateTimeFormatter formatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static void log(
    String method,
    String path,
    int statusCode,
    long duration
  ) {
    String time = LocalDateTime.now().format(formatter);
    System.out.println(
      "[" +
        time +
        "] " +
        method +
        " " +
        path +
        " " +
        statusCode +
        " " +
        duration +
        "ms"
    );
  }
}
