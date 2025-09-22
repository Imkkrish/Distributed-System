import java.io.*;
import java.net.*;

public class Coordinator {
    private String[] hosts;
    private int[] ports;

    public Coordinator(String[] hosts, int[] ports) {
        this.hosts = hosts;
        this.ports = ports;
    }

    public void startTransaction(Task task) {
        System.out.println("=== 2PC + Code Migration Transaction Start ===");

        boolean allYes = true;

        // Phase 1: CanCommit?
        System.out.println("--- Phase 1: CanCommit? ---");
        for (int i = 0; i < hosts.length; i++) {
            try (Socket socket = new Socket(hosts[i], ports[i]);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                out.writeObject("CanCommit");
                out.writeObject(task);
                boolean vote = (boolean) in.readObject();
                System.out.println("Participant " + (i+1) + " voted: " + (vote ? "YES" : "NO"));
                if (!vote) allYes = false;

            } catch (Exception e) {
                e.printStackTrace();
                allYes = false;
            }
        }

        // Phase 2: Commit or Rollback
        String decision = allYes ? "Commit" : "Rollback";
        System.out.println("--- Phase 2: " + decision + " ---");

        for (int i = 0; i < hosts.length; i++) {
            try (Socket socket = new Socket(hosts[i], ports[i]);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                out.writeObject(decision);
                if (allYes) {
                    out.writeObject(task);
                    String result = (String) in.readObject();
                    System.out.println("Participant " + (i+1) + " result: " + result);
                } else {
                    String ack = (String) in.readObject();
                    System.out.println("Participant " + (i+1) + " ACK: " + ack);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("=== Transaction End ===");
    }

    public static void main(String[] args) {
        String[] hosts = {"participant1", "participant2", "participant3"};
        int[] ports = {5001, 5002, 5003};

        Coordinator c = new Coordinator(hosts, ports);

        Task task;
        if (Math.random() < 0.5) {
            task = new AddTask(10, 20);
        } else {
            task = new PrintTask("Hello from coordinator!");
        }

        c.startTransaction(task);
    }
}
