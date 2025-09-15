package DistributedJDBC;
import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5004);
        System.out.println("Server started on port 5004");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        String clientInfo = socket.getRemoteSocketAddress().toString();
        System.out.println("Client connected: " + clientInfo);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from " + clientInfo + ": " + inputLine);
                // Case-insensitive command detection
                String cmd = inputLine.trim().toUpperCase();
                if (cmd.startsWith("INSERT")) {
                    String[] parts = inputLine.split(" ");
                    if (parts.length < 3) {
                        out.println("Error: Usage is INSERT <id> <name>");
                        System.out.println("Error: Usage is INSERT <id> <name>");
                        continue;
                    }
                    int id;
                    try {
                        id = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException nfe) {
                        out.println("Error: id must be an integer");
                        System.out.println("Error: id must be an integer");
                        continue;
                    }
                    String name = parts[2];
                    String result = insertStudentWithError(id, name);
                    out.println(result);
                    System.out.println("Result for " + clientInfo + ": " + result);
                } else if (cmd.equals("EXIT")) {
                    System.out.println("Client disconnected: " + clientInfo);
                    break;
                } else {
                    out.println("Unknown command");
                    System.out.println("Unknown command from " + clientInfo);
                }
            }
            socket.close();
        } catch (Exception e) {
            System.err.println("Exception for client " + clientInfo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    private String insertStudentWithError(int id, String name) {
        String url = "jdbc:mysql://localhost:3306/student";
        String username = "root";
        String password = "admin@123";
        String query = "INSERT INTO student (id, name) VALUES (?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, name);
            int count = ps.executeUpdate();
            ps.close();
            c.close();
            return count + " rows updated";
        } catch (SQLException e) {
            return "SQL Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
