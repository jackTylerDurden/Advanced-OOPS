import java.util.Scanner;
public class assignment1{
    static Integer processQueueCapacity = 2; //default capacity of task manager is set to 2.
    static Integer processCount = 0;
    static Scanner in = new Scanner(System.in);     
    /*Process Structure*/    
    static class Process{
        int PID;
        boolean isActive;
        String name;
        String owner;        
        int noOfThreads;
        float percentOfCPUCurrentlyUsed;
        float totalCPUTimeUsed;
        Process prev;
        Process next;

        public Process(int PID){
            this.PID = PID;            
            this.isActive = true;
            this.name = "test";
            this.owner = "test";        
            this.noOfThreads = 0;
            this.percentOfCPUCurrentlyUsed = 0;
            this.totalCPUTimeUsed = 0;            
        }
    }
    static Process front = null;
    static Process rear = null;
    static Process actualRear = null;

    public static void main(String[] args){                 
        sortQueue();
        
    }
    public static void sortQueue(){  
        enqueue(new Process(1));
        enqueue(new Process(2));
        enqueue(new Process(3));
        enqueue(new Process(4));
        enqueue(new Process(5));
        enqueue(new Process(6));   
        dequeue();     
        enqueue(new Process(7));   
        enqueue(new Process(8));   
        enqueue(new Process(9));   
        enqueue(new Process(10));   
        enqueue(new Process(11));
        // dequeue();   
        // dequeue();   
        // dequeue();   
        // dequeue();   
        // dequeue();   
        dequeue();   
        dequeue();
        enqueue(new Process(12));
        enqueue(new Process(13));   
        enqueue(new Process(14));   
        enqueue(new Process(15));   
        enqueue(new Process(16));   
        enqueue(new Process(17));   
        enqueue(new Process(18));   
        // enqueue(new Process(19));   
        System.out.println("processCOunt-------------->>>"+processCount);
        System.out.println("processQueueCapacity-------------->>>"+processQueueCapacity);
        System.out.println("rear-------->>"+rear.PID);
        System.out.println("actualRear-------->>"+actualRear.PID);        
        printQueue();
        // Process[] processList = new Process[processCount];
        // int count = 0;
        // Process currentProcess = front;                        
        // do{
        //     // if(currentProcess.isActive){    
        //         if(count < processCount-1)                            
        //             processList[count]=currentProcess;                 
        //     // }
        //     count++;
        //     currentProcess = currentProcess.next;
        // }while(currentProcess != front);                            
        // // for(int i=0;i < processList.length;i++)
        // //     System.out.println("before sorted------------>>>"+processList[i].PID);
        // sort(processList,0,processList.length-1);
        // System.out.println("Sorted------------>>>");
        // for(int i=0;i < processList.length;i++)
        //     System.out.println("Process ID -> "+processList[i].PID);
    }

    private static void sort(Process[] processList, int leftIndex ,int rightIndex){
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
    private static void createProcess(){
        Process userCreatedProcess = new Process(0);
        userCreatedProcess.isActive = true;        
        System.out.println("Please enter process Id : ");        
        userCreatedProcess.PID = in.nextInt();        
        enqueue(userCreatedProcess);
    }
    private static void enqueue(Process procByUser){
        processCount++;
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
                rear = proc;                
                rear.next = front; //establishing circular next link
                front.prev = rear; //establishing circular prev link 
                
                //at each enqueue actualRear and Rear should point to same node.
                actualRear.next = proc;
                proc.prev = actualRear;
                actualRear = proc;
                actualRear.next = front;
                front.prev = actualRear;                                
            }else{
                if(proc.PID == front.PID){ // special case - e.g. Queue size is 4 and so is the capacity and if it's dequeued then queue size becaoms 3 and capacity still remains 4. And if we try to enqueue via rear, then rear.next will always be front as a result front will be overwritten because before dequeueing the queue was full. So we need to create a last node in tbis special case.
                    // processCount++;
                    System.out.println("Eureka------->>>>!!!");
                    System.out.println("procByUser for eureka------->>>>!!!"+procByUser.PID);
                    System.out.println("rear for eureka------->>>>!!!"+rear.PID);
                    System.out.println("front for eureka------->>>>!!!"+front.PID);
                    rear.next = procByUser;
                    procByUser.prev = rear;                    
                    rear = procByUser;
                    rear.PID = procByUser.PID;
                    // rear.next = front;
                    // front.prev = rear;

                    actualRear.next = procByUser;
                    procByUser.prev = actualRear;                    
                    actualRear = procByUser;
                    actualRear.PID = procByUser.PID;
                    actualRear.next = front;
                    front.prev = actualRear;
                }else{
                    System.out.println("procByUser------------->>>"+procByUser.PID);                                  
                    System.out.println("rear------------->>>"+rear.PID);                                  
                    System.out.println("\n");                                                  
                    rear = rear.next;
                    // /rear = procByUser;
                    rear.PID = procByUser.PID;
                }                
            }            
        }        
    }

    private static void increaseCapacity(int factor,Process firstProcessAfterIncreasedCapacity){
        processQueueCapacity = (processQueueCapacity*factor);        
        int additionalCapacity = processQueueCapacity - (processCount-1);           
        Process firstProc = firstProcessAfterIncreasedCapacity;
        firstProc.isActive = true;
        rear.next = firstProc;
        firstProc.prev = rear;        
        rear = firstProc;                
        //at each enqueue actualRear and Rear should point to same node.        
        actualRear.next = firstProc;
        firstProc.prev = actualRear;
        firstProc.next = front;
        actualRear = firstProc;
        front.prev = actualRear;

        for(int i=0;i<(additionalCapacity-1);i++){                                    
            Process proc = new Process(0);
            proc.isActive = false;
            firstProc.next = proc;
            proc.prev = firstProc;
            firstProc = proc;
            firstProc.next = front;
            front.prev = firstProc;
            
            //to increase the capacity of the process queue empty nodes are queued into the queue and hence another pointer 'actualRear' is maintained to find actual rear point of the queue. Differnece between 'rear' and 'actualRear' is that 'rear' will always hold value entered by the user where 'actualRear' will be the actual rear of the queue which could be an user input or a dummy node.
            actualRear.next = proc;
            proc.prev = actualRear;
            actualRear = proc;
            actualRear.next = front;
            front.prev = actualRear;            
        }
        // System.out.println("actualRear Tanmay----------->>>"+actualRear.PID);
    }
    private static void dequeue() {        
        if(front == null){
            System.out.println("Process Queue is Empty from dequeue");
            return;
        }            
        processCount--;                
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
        // System.out.println("actual Process ID -> "+actualRear.PID);            
        // System.out.println(" rear Process ID -> "+rear.PID);            
        do{                                    
            // if(currentProcess.isActive){                                
                System.out.println("Process ID -> "+currentProcess.PID);
                System.out.print("\n");                
            // }                        
            currentProcess = currentProcess.next;            
        }while(currentProcess != front);         
    }
}
