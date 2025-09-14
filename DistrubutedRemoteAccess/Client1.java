import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5003;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(name); // Send name to server
            System.out.println("[Client1] Connected to server at " + host + ":" + port);

            Thread readerThread = new Thread(() -> {
                String line;
                try {
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("[Client1] Disconnected from server.");
                }
            });
            readerThread.start();

            while (true) {
                String msg = scanner.nextLine();
                if (msg.equalsIgnoreCase("exit")) break;
                out.println(msg);
            }
            socket.close();
        } catch (IOException e) {
            System.err.println("[Client1] Error: " + e.getMessage());
        }
    }
}
