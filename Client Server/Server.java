import java.io.*;
import java.net.*;
import java.util.concurrent.*;  // ✅ Import ExecutorService + Executors

public class Server {
    private static final int PORT = 9090;
    private static final ExecutorService pool = Executors.newFixedThreadPool(4); // limit 4 clients

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started, waiting for clients...");

        for (int i = 0; i < 4; i++) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            final int clientId = i + 1; // ✅ Make final copy

            pool.execute(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    // Send a task to client
                    out.println("TASK: compute " + clientId);

                    // Receive result
                    String result = in.readLine();
                    System.out.println("Result from client " + clientId + ": " + result);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        pool.shutdown();
        serverSocket.close();
    }
}
