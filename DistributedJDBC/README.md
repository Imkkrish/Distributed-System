# Distributed JDBC System

## File Paths (Cross-Platform)
- **Windows:** Use backslashes (\\) in paths, e.g., `C:\Users\YourName\Distributed System\DistributedJDBC\mysql-connector-j-9.4.0.jar`
- **macOS/Linux:** Use forward slashes (/) in paths, e.g., `/Users/yourname/Distributed System/DistributedJDBC/mysql-connector-j-9.4.0.jar`

When running commands, adjust the path format to match your operating system.

This project demonstrates a simple distributed system in Java where multiple clients can connect to a server and perform database operations using JDBC (MySQL).

## Features
- Multi-client support (each client handled in a separate thread)
- Server logs all client activity and database results
- Supports `INSERT <id> <name>` command (case-insensitive)
- Error messages sent to clients for invalid input or SQL errors

## Prerequisites
- Java JDK
- MySQL server running on `localhost:3306`
- Database `student` with table `student(id INT PRIMARY KEY, name VARCHAR(255))`
- MySQL JDBC driver (`mysql-connector-j-<version>.jar`)

## Setup
1. Place `mysql-connector-j-9.4.0.jar` in the `DistributedJDBC` folder (already done).
2. Update the MySQL password in `Server.java`:
   ```java
   String password = "your_actual_mysql_root_password";
   ```
3. Compile the code:
    - **Windows:**
       ```sh
   javac -cp ".;C:\\Users\\YourName\\Distributed System\\DistributedJDBC\\mysql-connector-j-9.4.0.jar" DistributedJDBC\\Server.java DistributedJDBC\\Client.java
       ```
    - **macOS/Linux:**
       ```sh
   javac -cp ".:/Users/yourname/Distributed System/DistributedJDBC/mysql-connector-j-9.4.0.jar" DistributedJDBC/Server.java DistributedJDBC/Client.java
       ```
4. Start the server:
    - **Windows:**
       ```sh
   java -cp ".;C:\\Users\\YourName\\Distributed System\\DistributedJDBC\\mysql-connector-j-9.4.0.jar" DistributedJDBC.Server
       ```
    - **macOS/Linux:**
       ```sh
   java -cp ".:/Users/yourname/Distributed System/DistributedJDBC/mysql-connector-j-9.4.0.jar" DistributedJDBC.Server
       ```
5. Start one or more clients (in separate terminals):
    - **Windows:**
       ```sh
   java -cp ".;C:\\Users\\YourName\\Distributed System\\DistributedJDBC\\mysql-connector-j-9.4.0.jar" DistributedJDBC.Client
       ```
    - **macOS/Linux:**
       ```sh
   java -cp ".:/Users/yourname/Distributed System/DistributedJDBC/mysql-connector-j-9.4.0.jar" DistributedJDBC.Client
       ```

## Usage
- On the client, type:
  - `INSERT <id> <name>` to add a row to the database
  - `EXIT` to disconnect
- Server logs all activity in its console

## Example
```
INSERT 101 Alice
INSERT 102 Bob
EXIT
```

## Notes
- Make sure the MySQL server is running and accessible.
- Use unique `id` values to avoid primary key conflicts.
- You can extend the system to support more SQL commands (SELECT, UPDATE, DELETE).
