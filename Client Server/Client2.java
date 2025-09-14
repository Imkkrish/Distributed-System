import java.io.*;
import java.net.*;

public class Client2 {
    private static final String HOST = "localhost";
    private static final int PORT = 9090;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String task = in.readLine();
        System.out.println("Client2 received: " + task);

        // Example work: factorial of 5
        int result = 1;
        for (int i = 1; i <= 5; i++) result *= i;

        out.println("Client2 result = " + result);

        socket.close();
    }
}
