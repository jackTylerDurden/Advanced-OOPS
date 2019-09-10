/*
    Name : Tanmay Deshpande
    REDID : 824646024
    Subject : Advanced Object Oriented Design and Programming
    Assignment 1
*/
import java.util.Scanner;
public class TaskManager{
    static int processQueueCapacity = 2; //default capacity of task manager is set to 2.
    static int processCount = 0;
    static int liveProcessCount = 0;
    static Scanner in = new Scanner(System.in);     
    /*Process Structure*/
    Process taskManager;       
    static Process front = null;
    static Process rear = null;
    static Process actualRear = null;
    
    public static void sortProcesses(){
        if(liveProcessCount == 0)
        return;
        Process[] processList = new Process[liveProcessCount];
        int count = 0;
        Process currentProcess = front;                        
        if(front == rear){ //only one process is in the queue.
            processList[0] = front;
        }else{
            do{
                if(currentProcess.isActive){                
                    processList[count]=currentProcess;                 
                    count++;
                }            
                currentProcess = currentProcess.next;
            }while(currentProcess != front);     
        }        

        System.out.println("Before Sorting");
        for(int i=0;i < processList.length;i++){            
            System.out.println("Process ID -> "+processList[i].PID);
            System.out.println("Process Name -> "+processList[i].name);
            System.out.println("Process Owner -> "+processList[i].owner);
            System.out.println("No. Of Threads -> "+processList[i].noOfThreads);
            System.out.println("Percent Of CPU currently used -> "+processList[i].percentOfCPUCurrentlyUsed);
            System.out.println("Total CPU Time Used -> "+processList[i].totalCPUTimeUsed);
            System.out.println("\n");
        }            
        sort(processList,0,processList.length-1);
        System.out.println("Sorted------------>>>");
        for(int i=0;i < processList.length;i++){
            System.out.println("Process ID -> "+processList[i].PID);
            System.out.println("Process Name -> "+processList[i].name);
            System.out.println("Process Owner -> "+processList[i].owner);
            System.out.println("No. Of Threads -> "+processList[i].noOfThreads);
            System.out.println("Percent Of CPU currently used -> "+processList[i].percentOfCPUCurrentlyUsed);
            System.out.println("Total CPU Time Used -> "+processList[i].totalCPUTimeUsed);
            System.out.println("\n");
        }
    }

    public static void sort(Process[] processList, int leftIndex ,int rightIndex){        
        if(leftIndex < rightIndex){
            int middle = (leftIndex + rightIndex) / 2;        
            sort(processList,leftIndex,middle);
            sort(processList,middle+1,rightIndex);
            merge(processList,leftIndex,middle,rightIndex);
        }        
    }
    private static void merge(Process[] processList, int leftIndex,int middle, int rightIndex){
        int firstHalfSize = middle - leftIndex + 1;
        int secondHalfSize = rightIndex - middle;
        Process[] firstHalf = new Process[firstHalfSize];
        Process[] secondHalf = new Process[secondHalfSize];

        for(int i=0; i<firstHalfSize;++i){
            firstHalf[i] = processList[i+leftIndex];
        }
        for(int j=0;j<secondHalfSize;++j){
            secondHalf[j] = processList[middle+1+j];
        }

        int count1 = 0, count2 = 0;
        int indexOfMergedArray = leftIndex;
        while(count1 < firstHalfSize && count2 < secondHalfSize){
            if(firstHalf[count1].PID <= secondHalf[count2].PID){
                processList[indexOfMergedArray] = firstHalf[count1];
                count1++;
            }else{
                processList[indexOfMergedArray] = secondHalf[count2];
                count2++;
            }
            indexOfMergedArray++;            
        }

        while(count1 < firstHalfSize){
            processList[indexOfMergedArray] = firstHalf[count1];
            count1++;
            indexOfMergedArray++;
        }
        while(count2 < secondHalfSize){            
            processList[indexOfMergedArray] = secondHalf[count2];
            count2++;
            indexOfMergedArray++;
        }

    }    
    public static void startProcess(Process procByUser){
        processCount++;
        liveProcessCount++;
        if(front == null && rear == null){            
            procByUser.isActive = true;
            front = rear = actualRear = procByUser;            
            return;
        }   
        
        if(processCount == (processQueueCapacity+1)){             
            increaseCapacity(2,procByUser); //the program which is using this code can decide by which factor the capacity should be increased.
        }else{            
            Process proc = rear.next;            
            if(proc == null){                
                proc = procByUser;                
                proc.isActive = true;
                rear.next = proc; //establishing next link
                proc.prev = rear; //establishing previous link                
                rear = rear.next;
                rear.next = front; //establishing circular next link
                front.prev = rear; //establishing circular prev link 
                
                //at each startProcess actualRear and Rear should point to same node.
                actualRear.next = proc;
                proc.prev = actualRear;
                actualRear = actualRear.next;
                actualRear.next = front;
                front.prev = actualRear;                                
            }else{                
                System.out.println("\n");                                                  
                rear = rear.next;                                    
                updateProcess(rear,procByUser);
                // rear.PID = procByUser.PID;
                // rear.isActive = true;                    
            }                
                        
        }        
    }

    static void updateProcess(Process destination, Process src){
        destination.PID = src.PID;
        destination.noOfThreads = src.noOfThreads;
        destination.name = src.name;
        destination.owner = src.owner;
        destination.percentOfCPUCurrentlyUsed = src.percentOfCPUCurrentlyUsed;
        destination.isActive = src.isActive;
    }

    private static void increaseCapacity(int factor,Process firstProcessAfterIncreasedCapacity){
        processQueueCapacity = (processQueueCapacity*factor);        
        int additionalCapacity = processQueueCapacity - (processCount-1);           
        Process firstProc = firstProcessAfterIncreasedCapacity;
        firstProc.isActive = true;
        rear.next = firstProc;
        firstProc.prev = rear;                
        rear = rear.next;
        //at each startProcess actualRear and Rear should point to same node.        
        actualRear.next = firstProc;
        firstProc.prev = actualRear;
        firstProc.next = front;        
        actualRear = actualRear.next;
        front.prev = actualRear;

        for(int i=0;i<(additionalCapacity-1);i++){                                    
            Process proc = new Process(0,false,"","",0,0.0,0.0);
            proc.isActive = false;
            firstProc.next = proc;
            proc.prev = firstProc;            
            firstProc = firstProc.next;
            firstProc.next = front;
            front.prev = firstProc;
            
            //to increase the capacity of the process queue empty nodes are queued into the queue and hence another pointer 'actualRear' is maintained to find actual rear point of the queue. Differnece between 'rear' and 'actualRear' is that 'rear' will always hold value entered by the user where 'actualRear' will be the actual rear of the queue which could be an user input or a dummy node.
            actualRear.next = proc;
            proc.prev = actualRear;            
            actualRear = actualRear.next;
            actualRear.next = front;
            front.prev = actualRear;            
        }        
    }
    public static void killProcess() {        
        if(front == null){
            System.out.println("Process Queue is Empty from killProcess");
            return;
        }                    
        liveProcessCount--;
        if(actualRear == front){
            front = actualRear=  rear = null;
        }else{
            front = front.next;
            actualRear.next = front;            
            front.prev = actualRear;            
        }
    }

    private static void printQueue(){
        Process currentProcess = front;
        if(front == null){
            System.out.println("Process Queue is Empty");
            return;
        }        
        if(actualRear == front){            
            System.out.println("1. Process ID -> "+currentProcess.PID);            
            return;
        }
        do{                                                
            System.out.println("Process ID -> "+currentProcess.PID);
            System.out.print("\n");                            
            currentProcess = currentProcess.next;            
        }while(currentProcess != front);         
    }
}