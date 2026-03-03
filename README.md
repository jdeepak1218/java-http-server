# Java HTTP Server

A lightweight, multi-threaded HTTP file server built completely from scratch in Java — no frameworks, no dependencies, just pure Java.

Inspired by Python's `python -m http.server`, but built in Java with extra features like REST API support, request logging, and thread pooling.

---

## Features

- **Multi-threaded** — handles multiple requests simultaneously using a thread pool
- **File Server** — serve and share any folder on your PC over local network
- **Directory Listing** — browse folders in the browser just like Python's HTTP server
- **File Downloads** — anyone on the same WiFi can download your files
- **REST API** — built-in JSON API endpoint
- **Request Logging** — logs every request with timestamp and response time
- **CLI Tool** — run from any folder with a single command
- **Zero Dependencies** — pure Java, no external libraries required

---

## Requirements

- Java 11 or higher ([Download here](https://www.java.com/en/download/))

---

## Quick Start

Download the latest `httpserver.jar` from [Releases](https://github.com/jdeepak1218/java-http-server/releases), then run:

```bash
# serve current folder on port 8080
java -jar httpserver.jar 8080 ./

# serve a specific folder
java -jar httpserver.jar 8080 /path/to/folder

# serve on a custom port
java -jar httpserver.jar 9090 ./
```

Then open your browser at `http://localhost:8080/`

---

## Share Files Over Local Network

**Step 1 — Find your IP address:**
```bash
# Windows
ipconfig

# Mac/Linux
ifconfig
```
Look for the IPv4 Address (e.g. `192.168.1.14`)

**Step 2 — Run the server:**
```bash
java -jar httpserver.jar 8080 ./
```

**Step 3 — Anyone on the same WiFi can now access:**
```
http://192.168.1.14:8080/
```

They can browse your folder and download files directly from their browser.

> Windows users: if others can't connect, allow Java through Windows Defender Firewall.

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Directory listing |
| GET | `/<filename>` | Download or view a file |
| GET | `/api/users` | Returns a JSON list of users |

Example:
```bash
curl http://localhost:8080/api/users
```
```json
["Alice", "Bob", "Charlie"]
```

---

## Build from Source

```bash
# Clone the repo
git clone https://github.com/jdeepak1218/java-http-server.git
cd java-http-server

# Compile
javac -d out src/com/httpserver/*.java src/com/httpserver/handlers/*.java

# Build JAR
echo "Main-Class: com.httpserver.Server" > manifest.txt
jar cfm httpserver.jar manifest.txt -C out .

# Run
java -jar httpserver.jar 8080 ./
```

---

## Project Structure

```
java-http-server/
├── src/
│   └── com/httpserver/
│       ├── Server.java                 # Entry point, CLI args
│       ├── HttpServer.java             # ServerSocket + thread pool
│       ├── ClientHandler.java          # Handles one client request
│       ├── HttpRequest.java            # Parses raw HTTP request
│       ├── HttpResponse.java           # Builds HTTP response
│       ├── Router.java                 # Routes requests to handlers
│       ├── Logger.java                 # Request logging
│       ├── Handler.java                # Handler interface
│       └── handlers/
│           ├── StaticFileHandler.java  # Serves files + directory listing
│           └── ApiHandler.java         # JSON REST API
├── static/                             # Default static files
└── README.md
```

---

## How It Works

```
Browser Request
      |
ServerSocket (listens on port)
      |
Thread Pool (10 workers)
      |
ClientHandler (reads request)
      |
HttpRequest (parses method, path, headers)
      |
Router (decides which handler to call)
      |
StaticFileHandler / ApiHandler
      |
HttpResponse (builds headers + body)
      |
Browser receives response
```

---

## Request Logs

Every request is logged in the terminal:
```
Server running on port: 8080
[2026-03-03 06:18:53] GET / 200 12ms
[2026-03-03 06:18:54] GET /api/users 200 3ms
[2026-03-03 06:18:55] GET /photo.jpg 200 45ms
[2026-03-03 06:18:56] GET /nothing 404 1ms
```

---

## Use Cases

- Share files between devices on the same WiFi without USB or cloud storage
- Quick local file server for development
- Learning how HTTP works at a low level
- A drop-in replacement for Python's `http.server` with more features

---

## Built With

- Java 21
- `java.net.ServerSocket` for networking
- `java.util.concurrent.ExecutorService` for thread pooling
- `java.nio.file.Files` for file handling
- No external dependencies

---

## Contributing

Pull requests are welcome. Some ideas for future improvements:

- HTTPS support
- File upload via browser
- Basic authentication
- Progress bar for large downloads
- WebSocket support

---

## License

MIT License — free to use, modify, and distribute.

---

## Author

Deepak J — built from scratch as a learning project to understand how HTTP servers work under the hood.
