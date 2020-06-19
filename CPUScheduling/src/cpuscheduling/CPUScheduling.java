package cpuscheduling;
// Java program for implementation of RR scheduling 
import static cpuscheduling.CPUScheduling.findavgTime;
import java.awt.Dimension;
import java.util.*; 
import javax.swing.*;

//thread to create a band with 200m bursts
class Priority200Band extends Thread 
{ 
    public void run() 
    { 
        try
        { 
            //create the prioirty band
            ArrayList<Integer> Priority200m = new ArrayList<Integer>();

           //create a random number generator
           Random randomGen = new Random();
           int randomJobs = randomGen.nextInt(10)+1;
           
           //for every job created, create a burst time for those jobs
            for (int i = 0; i < randomJobs; i++)
            {
                int randomBurst = randomGen.nextInt((800 - 10) +1)+10;
                Priority200m.add(randomBurst);
            }
            //n = the size of the array list
            int n = Priority200m.size(); 

            // Time quantum 
            int quantum = 200; 
            findavgTime(n, Priority200m, quantum); 
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    } 
} 

//thread to create a band with 10m bursts
class Priority10Band extends Thread 
{ 
    public void run() 
    { 
        try
        { 
            // process id's 
            ArrayList<Integer> Priority10m = new ArrayList<Integer>();

              //create a random number generator
           Random randomGen = new Random();
           int randomJobs = randomGen.nextInt(10)+1;
            //for every job created, create a burst time for those jobs
            for (int i = 0; i < randomJobs; i++)
            {
                int randomBurst = randomGen.nextInt((800 - 10) +1)+10;
                Priority10m.add(randomBurst);
            }
             //m = the size of the array list
            int m = Priority10m.size();

            // Time quantum 
            int quantum = 10; 
            findavgTime(m, Priority10m, quantum); 
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    } 
} 


public class CPUScheduling  
{ 
    
// Method to find the waiting time for all processes 
    static void findWaitingTime(int n, List<Integer> bt, int wt[], int quantum) 
    { 
        // Make a copy of burst times bt[] to store remaining burst times. 
        int rem_bt[] = new int[n]; 
        for (int i = 0 ; i < n ; i++) 
            rem_bt[i] =  bt.get(i); 
       
        int t = 0; // Current time 
       
        // Keep traversing processes in round robin manner until all of them are not done. 
        while(true) 
        { 
            boolean done = true; 
            // Traverse all processes one by one repeatedly 
            for (int i = 0 ; i < n; i++) 
            { 
                // If burst time of a process is greater than 0 then only need to process further 
                if (rem_bt[i] > 0) 
                { 
                    done = false; // There is a pending process 
       
                    if (rem_bt[i] > quantum) 
                    { 
                        // Increase the value of t i.e. shows how much time a process has been processed 
                        t += quantum; 
       
                        // Decrease the burst_time of current process by quantum 
                        rem_bt[i] -= quantum; 
                    } 
       
                    // If burst time is smaller than or equal to quantum. Last cycle for this process 
                    else
                    { 
                        // Increase the value of t i.e. shows how much time a process has been processed 
                        t = t + rem_bt[i]; 
       
                        // Waiting time is current time minus time used by this process 
                        wt[i] = t - bt.get(i); 
       
                        // As the process gets fully executed make its remaining burst time = 0 
                        rem_bt[i] = 0; 
                    } 
                } 
            } 
       
            // If all processes are done 
            if (done == true) 
              break; 
        } 
    } 
       
    // Method to calculate turn around time 
    static void findTurnAroundTime(int n, List<Integer> bt, int wt[], int tat[]) 
    { 
        // calculating turnaround time by adding bt[i] + wt[i] 
        for (int i = 0; i < n ; i++) 
            tat[i] = bt.get(i) + wt[i]; 
    } 
       
    // Method to calculate average time 
    static void findavgTime(int n, List<Integer> bt, int quantum) throws InterruptedException 
    { 
        String output = "";
        int wt[] = new int[n], tat[] = new int[n]; 
        int total_wt = 0, total_tat = 0; 
       
        // Function to find waiting time of all processes 
        findWaitingTime(n, bt, wt, quantum); 
       
        // Function to find turn around time for all processes 
        findTurnAroundTime(n, bt, wt, tat); 
       
        // Display processes along with all details 
        output +="Processes \tBurst time \tWaiting time \tTurn around time\n"; 
       
        // Calculate total waiting time and total turn around time 
        for (int i=0; i<n; i++) 
        { 
            total_wt = total_wt + wt[i]; 
            total_tat = total_tat + tat[i]; 
            output+=" " + (i+1) + " \t" + bt.get(i) +" \t"+ wt[i] +" \t " + tat[i]+"\n"; 
        } 
        //add all output to a single varible to be loaded into the jframe
        output+="Average waiting time = " + (float)total_wt / (float)n+"\n"; 
        output+="Average turn around time = " + (float)total_tat / (float)n+"\n";
        
        JFrame jframe = new JFrame("Burst time"+quantum);
        // all the jframe setup stuff;
        JTextArea textArea = new JTextArea();
        //jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setPreferredSize(new Dimension(400, 300));
        jframe.pack();
        textArea.setText(output);
        jframe.getContentPane().add(textArea);
        jframe.setVisible(true);

    } 
      
    // Driver Method 
    public static void main(String[] args) 
    { 
        //user terminated loop
        boolean go = true;
        while(go == true)
        {
           //get the current time
            long realTimeStart = System.currentTimeMillis();
            
            //generate 8 200m bands and 4 10m bands
            for (int i=0; i<12; i++) 
            { 
                if(i<=8)
                {
                    //create a 200m thread
                    Priority200Band object200 = new Priority200Band(); 
                    object200.start(); 
                }
                if(i>8)
                {
                    //create a 10m thread
                    Priority10Band object10 = new Priority10Band();
                    object10.start();
                }
            } 
            
            
            
            //Calculate the real time it took to run these jobs
            long realTimeEnd = System.currentTimeMillis();
            realTimeEnd = realTimeEnd - realTimeStart;
            System.out.println("The total time it took to complete every job was: "+realTimeEnd+" miliseconds.");
            
            //ask if the user wishes to continue
            System.out.println("Would you like to run another batch of jobs? Enter Y for continue or N for stop.");
            Scanner scan = new Scanner(System.in);
            String sentinel = scan.nextLine();
            System.out.println("\n\n");
            //if no end program
            if(sentinel.equalsIgnoreCase("n"))
            {
                go = false;
            }
        }
        System.exit(0);
    } 
} 