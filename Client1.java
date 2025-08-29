import java.io.*;
import java.net.*;

public class Client1 {
    private static final String HOST = "localhost";
    private static final int PORT = 9090;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String task = in.readLine();
        System.out.println("Client1 received: " + task);

        // Example work: sum of numbers
        int result = 0;
        for (int i = 1; i <= 10; i++) result += i;

        out.println("Client1 result = " + result);

        socket.close();
    }
}
