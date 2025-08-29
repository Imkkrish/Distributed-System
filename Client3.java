import java.io.*;
import java.net.*;

public class Client3 {
    private static final String HOST = "localhost";
    private static final int PORT = 9090;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String task = in.readLine();
        System.out.println("Client3 received: " + task);

        // Example work: reverse a string
        String data = "distributed";
        String result = new StringBuilder(data).reverse().toString();

        out.println("Client3 result = " + result);

        socket.close();
    }
}
