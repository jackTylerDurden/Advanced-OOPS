public class Process{
    public int PID;
    public boolean isActive;
    public String name;
    public String owner;        
    public int noOfThreads;
    public double percentOfCPUCurrentlyUsed;
    public double totalCPUTimeUsed;
    public Process prev;
    public Process next;

    public Process(int PID,boolean isActive, String name, String owner, int noOfThreads, double percentOfCPUCurrentlyUsed, double totalCPUTimeUsed){
        this.PID = PID;            
        this.isActive = true;
        this.name = name;
        this.owner = owner;        
        this.noOfThreads = noOfThreads;
        this.percentOfCPUCurrentlyUsed = percentOfCPUCurrentlyUsed;
        this.totalCPUTimeUsed = totalCPUTimeUsed;            
    }
}