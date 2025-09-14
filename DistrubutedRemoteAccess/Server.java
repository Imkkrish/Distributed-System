import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 5003;
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        System.out.println("[Server] Starting on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("[Server] Error: " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                // Removed redundant prompt; client will prompt for name
                clientName = in.readLine();
                if (clientName != null && !clientName.trim().isEmpty()) {
                    System.out.println("[Server] " + clientName + " connected.");
                }
            } catch (IOException e) {
                System.err.println("[Server] Error initializing client: " + e.getMessage());
            }
        }

        public void run() {
            String msg;
            boolean firstMessage = true;
            try {
                while ((msg = in.readLine()) != null) {
                    if (firstMessage) {
                        // Skip logging/forwarding the first message (name)
                        firstMessage = false;
                        continue;
                    }
                    logMessage(clientName, msg);
                    forwardMessage(clientName, msg);
                }
            } catch (IOException e) {
                System.out.println("[Server] " + clientName + " disconnected.");
            } finally {
                cleanup();
            }
        }

        private void forwardMessage(String from, String msg) {
            synchronized (clients) {
                for (ClientHandler client : clients) {
                    if (!client.clientName.equals(from)) {
                        client.out.println(from + ": " + msg);
                    }
                }
            }
        }

        private void logMessage(String from, String msg) {
            System.out.println("[Server] " + from + " -> " + msg);
        }

        private void cleanup() {
            try {
                clients.remove(this);
                socket.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }
}
