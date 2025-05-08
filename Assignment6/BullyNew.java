import java.util.*;

public class BullyNew {
    int coordinator;
    int max_processes;
    boolean processes[];    

    public BullyNew(int max)
    {
        max_processes = max;
        processes = new boolean[max_processes];
        coordinator = max;

        System.out.println("creating processes");
        for(int i=0;i<max;i++)
        {
            processes[i] = true;
            System.out.println("P" +(i+1) +"created");
        }
        System.out.println("Process P" + coordinator + "is the coordinator");
    }

    void displayProcesses()
    {
        for(int i=0;i<max_processes;i++)
        {
            if(processes[i]){
                System.out.println("P" + (i+1)+ "is up");
            }
            else{
                System.out.println("P" + (i+1)+ "is up");
            }
        }
        System.out.println("Process P" + coordinator + "is the coordinator");
    }

    void upProcess(int process_id)
    {
        if (!processes[process_id - 1]) {
            processes[process_id - 1] = true;
            System.out.println("Process " + process_id + " is now up.");
        }
        else 
            System.out.println("Process " + process_id + " is already up.");
    }

    void downProcess(int process_id)
    {
        if (!processes[process_id - 1]) {
            System.out.println("Process " + process_id + " is already down.");
        }
        else {
            processes[process_id - 1] = false;
            System.out.println("Process " + process_id + " is down.");
    }
}
    void runElection(int process_id)
    {
        coordinator=process_id;
        boolean keepGoing=true;

        for(int i =0;i<max_processes && keepGoing; i++)
        {
            System.out.println("Election message sent from process " + process_id + " to process " + (i+1));

            if(processes[i]){
                keepGoing=false;
                runElection(i+1);
            }
        }
    }

}

