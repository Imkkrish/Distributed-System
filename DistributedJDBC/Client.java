package DistributedJDBC;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5004);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String inputLine;
        System.out.println("Connected to server. Type commands (e.g., INSERT 110 xyz) or EXIT to quit:");
        while (true) {
            inputLine = userInput.readLine();
            out.println(inputLine);
            if (inputLine.equals("EXIT")) break;
            String response = in.readLine();
            System.out.println("Server: " + response);
        }
        socket.close();
    }
}
