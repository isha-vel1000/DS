import java.util.*;

public class Token {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the no of nodes:");
        int n=sc.nextInt();

        System.out.println("ring formation will be as below:");
        for(int i=0;i<n;i++)
        {
            System.out.print(i+ " ");
        }
        System.out.println("0(loop back to zero)");
        System.out.println("intially token is at node 0");

        int choice =0;
        int token =0;
        int sender, receiver;

        do{
            System.out.println("\n=====");
            System.out.println("Current token holder"+token);
            System.out.println("\n=====");

            while(true){
                System.out.println("enter the sender");
                sender=sc.nextInt();
                if (sender<0 || sender>=n) {
                    System.out.print("enter valid sender");
                }
                else
                    break;
            }

            while(true){
                System.out.println("enter the receiver");
                receiver=sc.nextInt();
                if (receiver<0 || receiver>=n) {
                    System.out.print("enter valid receiver");
                }
                else
                    break;
            }

            System.out.println("enter the data");
            int data = sc.nextInt();

            System.out.println("token passingg");
            int i = token;
            while(i!=sender){
                int next = (i+1)%n;
                System.out.println("Token passed from node"+i+"node"+next);
                i=next;
            }
            System.out.println("token at node:"+sender);

            System.out.println("sender"+ sender +"entering the critical section");
            System.out.println("sender is sending data"+ sender+"data"+data);

            System.out.println("data passingg");
             i = sender;
            while(i!=receiver){
                int next = (i+1)%n;
                System.out.println("data passed from node"+i+"node"+next);
                i=next;
            }
            System.out.println("receiver"+ receiver+"received the data"+data);

            System.out.println("sender"+ sender +"exiting the critical section");
            token=sender;
            
            System.out.println("press 1 to continue");
            choice = sc.nextInt();

        }while(choice==1);
    }
}