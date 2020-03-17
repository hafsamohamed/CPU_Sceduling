import java.util.ArrayList;
import java.util.Scanner;

class Process{
    String name;
    int color ;
    int arrivalTime ;
    int burstTime ;
    int priority;
    int waitingTime ;
    int turnAroundTime;
    public int quantum;
    public int ag;
    public int job;


    public Process() {
        this.name = null;
        this.color = 0;
        this.arrivalTime = 0;
        this.burstTime = 0;
        this.priority = 0;
        this.waitingTime=0;
        this.turnAroundTime=0;

    }

    public Process(String name, int color, int arrivalTime, int turnAroundTime, int priority,int quantum) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = turnAroundTime;
        this.priority = priority;
        this.quantum = quantum;
        ag = priority + arrivalTime + burstTime;
        job = 0;

    }


}

public class cpuScheduling {
/***********************************************SJF Scheduling*****************************************************************/
public ArrayList<Process> SortingBurst (ArrayList<Process>processes){
    Process temp = new Process();
    for (int i = 1; i < processes.size(); i++) {
        for (int j = i; j > 0; j--) {
            if (processes.get(i).burstTime <  processes.get(j-1).burstTime) {
                temp = processes.get(j);
                processes.set(j, processes.get(j - 1));
                processes.set(j - 1, temp);
            }}}

    return processes;
}

public  void SJF_Schedulling(ArrayList<Process> processes) {

    ArrayList<Process> cpuSchedul = new ArrayList<>();
    ArrayList<Process> temp = new ArrayList<>();

    processes = Sorting(processes); ///sorting by arrival time

    temp.add(processes.get(0));
    for (int i = 1; i < processes.size(); i++) {
        if (processes.get(0).arrivalTime == processes.get(i).arrivalTime) {
            temp.add(processes.get(i));
        }
    }
    temp = SortingBurst(temp);
    cpuSchedul.add(temp.get(0));
    cpuSchedul.get(0).waitingTime=0;
    cpuSchedul.get(0).turnAroundTime =cpuSchedul.get(0).burstTime;
    int n = processes.indexOf(temp.get(0));

    processes.remove(n);
    int k =1;
    int finishTime =0;
    ArrayList<Process> pros;
    ArrayList<Process> pross;
    while(!processes.isEmpty()) {

        finishTime =  finishTime+ cpuSchedul.get(cpuSchedul.size()-1).burstTime;
        pros = new ArrayList<>();
        pross = new ArrayList<>();
        for (int j = 0; j < processes.size(); j++) {
            if (finishTime >= processes.get(j).arrivalTime) {
                pros.add(processes.get(j));

            }

        }
        pros = SortingBurst(pros);

        cpuSchedul.add(pros.get(0));

        processes.remove(processes.indexOf(pros.get(0)));

        cpuSchedul.get(k).waitingTime= finishTime - cpuSchedul.get(k).arrivalTime;
        cpuSchedul.get(k).turnAroundTime = cpuSchedul.get(k).waitingTime + cpuSchedul.get(k).burstTime;

        k++;
    }
    for (int c =0 ; c < cpuSchedul.size(); c++){
        System.out.println(cpuSchedul.get(c).name +" : "+"WT ="+cpuSchedul.get(c).waitingTime +" : "+"TAT ="+cpuSchedul.get(c).turnAroundTime);
    }

    float avgWait =AverageWaiting(cpuSchedul);
    float avgTurnAround =AverageTurnAround(cpuSchedul);
    System.out.println("Average Waiting = "+avgWait +" Average TurnAround = "+avgTurnAround);
}

/***************************************************SRTF Scheduling*************************************************************/

//public int contextSwitch = 1;

public  static void waitingTime(Process proc[], int n, int wt[]){
        int arr[] = new int[n];
        for(int i=0; i<n; i++) {
            arr[i] = proc[i].burstTime;
        }
        int complete = 0;
        int systemTime = 0;
        int min = 10000;
        int shortest = 0;
        int finish_time =0;
        boolean check = false;

        while (complete != n) {

            for (int j = 0; j < n; j++)
            {

                if ((proc[j].arrivalTime <= systemTime) && (arr[j] < min) && arr[j] > 0 ){

                    min = arr[j];
                    shortest = j;
                    check = true;
                    System.out.println(proc[shortest].name);
                }

            }

            if (check == false) {
                systemTime++;
                continue;
            }


            arr[shortest]--;

            min = arr[shortest] ;
            if (min == 0)
                min = 10000;

            if (arr[shortest]== 0) {
                complete++;

                check = false;
                finish_time = systemTime ;
                //systemTime+=contextSwitch;
                wt[shortest] = finish_time  - proc[shortest].burstTime - proc[shortest].arrivalTime ;

                if (wt[shortest] < 0)
                    wt[shortest] = 0;
            }
            // Increment time
            systemTime++;

            System.out.println("Name: " + proc[shortest].name + " Work: " +(proc[shortest].burstTime - arr[shortest]) );
        }


    }

static void turnAroundTime(Process proc[], int n, int wt[], int tat[]) {
        for (int i = 0; i < n; i++)
            tat[i] = proc[i].burstTime + wt[i]  ;
    }

    // Method to calculate average time
public static void totalTime(Process proc[], int n)
    {
        int wt[] = new int[n], tat[] = new int[n];
        int  total_wt = 0, total_tat = 0;


        waitingTime(proc, n, wt);

        turnAroundTime(proc, n, wt, tat);

        System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");


        for (int i = 0; i < n; i++) {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            System.out.println(" " + proc[i].name + "\t\t\t"
                    + proc[i].burstTime + "\t\t\t " + wt[i]
                    + "\t\t\t\t" + tat[i]);
        }

        System.out.println("Average waiting time = " + (double)total_wt / (double) n);
        System.out.println("Average turn around time = " +
                (double)total_tat / (double) n);
    }

/**********************************************Priority Scheduling******************************************************************/

public static ArrayList<Model1> model = new ArrayList<>();

public ArrayList<Process> Sorting(ArrayList<Process>processes){
        Process temp = new Process();
        for (int i = 1; i < processes.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (processes.get(i).arrivalTime <  processes.get(j-1).arrivalTime) {
                    temp = processes.get(j);
                    processes.set(j, processes.get(j - 1));
                    processes.set(j - 1, temp);
                }}}


        return processes;
    }

public ArrayList<Process> SortingByPriority (ArrayList<Process>processes){
        Process temp = new Process();
        for (int i = 1; i < processes.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (processes.get(i).priority <  processes.get(j-1).priority) {
                    temp = processes.get(j);
                    processes.set(j, processes.get(j - 1));
                    processes.set(j - 1, temp);
                }}}

        return processes;
    }

public float AverageWaiting(ArrayList<Process>processes){
        float avrgWait = 0;
        float n = processes.size();
        float sum =0 ;
        for (int i =0 ; i<processes.size() ; i++){
            sum +=processes.get(i).waitingTime;
        }
        avrgWait = sum/n;
        return  avrgWait;
    }

public float AverageTurnAround(ArrayList<Process>processes){
        float avrgTurnAround =0;
        float n =processes.size();
        float sum =0 ;
        for (int i =0 ; i<processes.size() ; i++){
            sum +=processes.get(i).turnAroundTime;
        }

        avrgTurnAround = sum/n;
        return  avrgTurnAround;
    }

public  void PrioritySchedulling(ArrayList<Process> processes) {

        ArrayList<Process> cpuSchedul = new ArrayList<>();
        ArrayList<Process> temp = new ArrayList<>();

        processes = Sorting(processes);   ///sorting by arrival time
        temp.add(processes.get(0));
        for (int i = 1; i < processes.size(); i++) {
            if (processes.get(0).arrivalTime == processes.get(i).arrivalTime) {
                temp.add(processes.get(i));
            }
        }
        temp = SortingByPriority(temp);
        cpuSchedul.add(temp.get(0));
        cpuSchedul.get(0).waitingTime=0;
        cpuSchedul.get(0).turnAroundTime =cpuSchedul.get(0).burstTime;
        int n = processes.indexOf(temp.get(0));

        processes.remove(n);
        int k =1;
        int finishTime =0;
        ArrayList<Process> pros;
        ArrayList<Process> pross;
        while(!processes.isEmpty()) {

            finishTime =  finishTime+ cpuSchedul.get(cpuSchedul.size()-1).burstTime;
            pros = new ArrayList<>();
            pross = new ArrayList<>();
            for (int j = 0; j < processes.size(); j++) {
                if (finishTime >= processes.get(j).arrivalTime) {
                    pros.add(processes.get(j));

                }

            }
            pros = SortingByPriority(pros);

            cpuSchedul.add(pros.get(0));

            processes.remove(processes.indexOf(pros.get(0)));

            cpuSchedul.get(k).waitingTime= finishTime - cpuSchedul.get(k).arrivalTime;
            cpuSchedul.get(k).turnAroundTime = cpuSchedul.get(k).waitingTime + cpuSchedul.get(k).burstTime;

            pross = SortingByPriority(processes);
            if (pross.size() > 1){
                pross.get(pross.size()-1).priority--;
            }
            k++;
        }
        for (int c =0 ; c < cpuSchedul.size(); c++){

            System.out.println(cpuSchedul.get(c).name +" : "+"WT ="+cpuSchedul.get(c).waitingTime +" : "+"TAT ="+cpuSchedul.get(c).turnAroundTime);
        }

        float avgWait =AverageWaiting(cpuSchedul);
        float avgTurnAround =AverageTurnAround(cpuSchedul);
        System.out.println("Average Waiting = "+avgWait +" Average TurnAround = "+avgTurnAround);
    }

public static void main(String[] args) {

            Scanner sn = new Scanner(System.in);
            Process process = new Process();
            int numOfProcesses=0;
            Scanner scan2 = new Scanner(System.in);
            cpuScheduling p = new cpuScheduling();
            Scanner scan = new Scanner(System.in);
            ArrayList<Process> processes = new ArrayList<>();
         while (true) {
             System.out.println("Please enter the number of processes you want to Scedule in CPU ");
             numOfProcesses = sn.nextInt();
             for (int i = 0; i < numOfProcesses; i++) {
                 System.out.println("Please enter the data of process ");
                 process = new Process();
                 System.out.print("Name: ");
                 process.name = scan.nextLine();
                 System.out.print("Color: ");
                 process.color = scan2.nextInt();
                 System.out.print("Arrival Time: ");
                 process.arrivalTime = scan2.nextInt();
                 System.out.print("Burst Time: ");
                 process.burstTime = scan2.nextInt();
                 System.out.print("Priority: ");
                 process.priority =scan2.nextInt();

                 processes.add(process);

             }
             System.out.println("Choose the way you want to make cpu scheduling");
             System.out.println("1-Shortest Job First Scheduling");
             System.out.println("2-Shortest Remaining Time First Scheduling");
             System.out.println("3-Priority Scheduling");
             System.out.println("5-Exit");
             Scanner snn =new Scanner(System.in);
             int choise = snn.nextInt();
             if (choise == 1){
                 p.SJF_Schedulling(processes);
             }
             if (choise == 2){
                 Process[] pros = new Process[numOfProcesses];
                 for (int i = 0; i < numOfProcesses; i++) {
                     pros[i] = processes.get(i);
                 }
                 p.totalTime(pros, pros.length); // context switch
             }
             if (choise == 3){
                 p.PrioritySchedulling(processes);
             }


         }
        }

}
