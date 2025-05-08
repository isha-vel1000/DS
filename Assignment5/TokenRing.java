import java.util.*;

public class TokenRing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Getting the number of nodes in the ring
        System.out.print("Enter the number of nodes you want in the ring: ");
        int n = sc.nextInt();

        // Displaying the ring formation
        System.out.println("Ring Formed is as below: ");
        for (int i = 0; i < n; i++) {
            System.out.print(i + " ");
        }
        System.out.println("0 (loop back to Node 0)");
        System.out.println("Note: Initially, the Token is at Node 0");

        int choice = 0;
        int token = 0;
        int sender, receiver;

        do {
            System.out.println("\n===============================");
            System.out.println("Current Token Holder: Node " + token);
            System.out.println("===============================");

            // Getting sender with validation
            while (true) {
                System.out.print("Enter Sender: ");
                sender = sc.nextInt();
                if (sender < 0 || sender >= n)
                    System.out.println("Enter valid sender between 0 and " + (n - 1));
                else
                    break;
            }

            // Getting receiver with validation
            while (true) {
                System.out.print("Enter Receiver: ");
                receiver = sc.nextInt();
                if (receiver < 0 || receiver >= n)
                    System.out.println("Enter valid receiver between 0 and " + (n - 1));
                else
                    break;
            }

            System.out.print("Enter Data to Send: ");
            int data = sc.nextInt();

            // Token Passing
            System.out.println("\n--- Token Passing ---");
            int i = token;
            while (i != sender) {
                int next = (i + 1) % n;
                System.out.println("Token moved from Node " + i + " to Node " + next);
                i = next;
            }
            System.out.println("Token is now at Node " + sender);

            // --- Critical Section Entry ---
            System.out.println("\n[Node " + sender + " has entered the Critical Section]");
            System.out.println("Sender is " + sender + ", Sending Data: " + data);

            // Data Passing
            System.out.println("\n--- Data Passing ---");
            i = sender;
            while (i != receiver) {
                int next = (i + 1) % n;
                System.out.println("Data moved from Node " + i + " to Node " + next);
                i = next;
            }
            System.out.println("Receiver: " + receiver + ", Received the data: " + data);

            // --- Critical Section Exit ---
            System.out.println("[Node " + sender + " has exited the Critical Section]");

            // Update token position
            token = sender;

            // Ask user if they want to send more data
            System.out.print("\nDo you want to send data again? If yes Enter 1, If no Enter 0: ");
            choice = sc.nextInt();

        } while (choice == 1);

        sc.close();
    }
}
