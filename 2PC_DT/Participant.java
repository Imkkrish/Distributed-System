import java.io.*;
import java.net.*;
import java.util.Random;

public class Participant {
    private int port;
    private Random rand = new Random();

    public Participant(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Participant running on port " + port);
            while (true) {
                Socket socket = server.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                String phase = (String) in.readObject();

                if (phase.equals("CanCommit")) {
                    Task task = (Task) in.readObject();
                    System.out.println("Received task: " + task.getClass().getSimpleName());
                    boolean vote = rand.nextBoolean();
                    System.out.println("Voting: " + (vote ? "YES" : "NO"));
                    out.writeObject(vote);
                } else if (phase.equals("Commit")) {
                    Task task = (Task) in.readObject();
                    String result = task.execute();
                    System.out.println("Executing task → " + result);
                    out.writeObject(result);
                } else if (phase.equals("Rollback")) {
                    System.out.println("Rolling back transaction");
                    out.writeObject("ACK");
                }
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        Participant p = new Participant(port);
        p.start();
    }
}
