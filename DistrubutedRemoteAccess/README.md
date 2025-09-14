# DistributedRemoteAccess

A simple Java socket-based distributed system for message relay between two clients via a central server.

## Architecture
- **Server**: Listens on port 5000, relays messages between clients, logs all messages.
- **Client1**: Runs on Mac host, connects to server via `localhost:5000`.
- **Client2**: Runs in Docker container, connects to server via `host.docker.internal:5000`.

## Files
- `Server.java`: Central relay server
- `Client1.java`: First client (host)
- `Client2.java`: Second client (Docker)


## How to Compile and Run

### 1. Compile all Java files (on Mac host)
```sh
cd "/Users/krishkumar/Desktop/Distributed System/DistrubutedRemoteAccess"
javac Server.java Client1.java Client2.java
```

### 2. Start the Server (on Mac host)
```sh
java Server
# Output:
# [Server] Starting on port 5003
# [Server] Krish 2 connected.
# [Server] Krish 1 connected.
# [Server] Krish 1 -> Hello
# [Server] Krish 2 -> Hii
```

### 3. Start Client 1 (on Mac host)
```sh
cd "/Users/krishkumar/Desktop/Distributed System/DistrubutedRemoteAccess"
javac Client1.java
java Client1
# Output:
# Enter your name: Krish 1
# [Client1] Connected to server at localhost:5003
# Hello
# Krish 2: Hii
```

### 4. Create and Run Docker Container for Client 2

#### a. Pull Java image and create container:
```sh
docker pull openjdk:latest
docker run -it --name java-client2 openjdk:latest /bin/bash
```

#### b. Copy Client2.java to container (from Mac):
```sh
docker cp "/Users/krishkumar/Desktop/Distributed System/DistrubutedRemoteAccess/Client2.java" java-client2:/DistrubutedRemoteAccess/Client2.java
```

#### c. Run Client2 inside container:
```sh
docker exec -it java-client2 /bin/bash
cd /DistrubutedRemoteAccess
javac Client2.java
java Client2
# Output:
# Enter your name: Krish 2
# [Client2] Connected to server at host.docker.internal:5003
# Krish 1: Hello
# Hii
```

## Example Demo

1. Start the server: `java Server`
2. Start Client 1: `java Client1` (on Mac)
3. Start Client 2: `java Client2` (in Docker)
4. Client1 types: `Hello` → appears at Client2.
5. Client2 types: `Hii` → appears at Client1.
6. Type `exit` to disconnect a client.

## Notes
- Server logs all messages in its terminal.
- If a client disconnects, server removes it cleanly.
- Both clients can send/receive messages via the server.
- Use unique names for each client for clear logs.
