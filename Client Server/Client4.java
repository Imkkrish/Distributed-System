import java.io.*;
import java.net.*;

public class Client4 {
    private static final String HOST = "localhost";
    private static final int PORT = 9090;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String task = in.readLine();
        System.out.println("Client4 received: " + task);

        // Example work: count vowels in a string
        String data = "distributed systems";
        long result = data.chars().filter(c -> "aeiou".indexOf(c) >= 0).count();

        out.println("Client4 result = " + result);

        socket.close();
    }
}
