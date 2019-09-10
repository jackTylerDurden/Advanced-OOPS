/*
    Name : Tanmay Deshpande
    REDID : 824646024
    Subject : Advanced Object Oriented Design and Programming
    Assignment 1
*/
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TaskManagerTest{
    
    @Test
    public void testProcessEnqueue(){        
        Process proc1 = new Process(63,true,"Adobe","tanmay_linux",4,10.1,12.1);
        Process proc2 = new Process(42,true,"Compiz_Remastered","tanmay_linux",4,10.1,12.1);
        TaskManager.startProcess(proc1);
        TaskManager.startProcess(proc2);        
        assertEquals("Compiz_Remastered",TaskManager.rear.name);         
    }

    @Test
    public void testProcessDequeue(){        
        Process proc3 = new Process(23,true,"Adobe","tanmay_linux",4,10.1,12.1);
        Process proc4 = new Process(62,true,"WINE(WINE is not an emulator)","tanmay_linux",4,10.1,12.1);
        TaskManager.startProcess(proc3);
        TaskManager.startProcess(proc4);        
        assertEquals("WINE(WINE is not an emulator)",TaskManager.rear.name);        
        TaskManager.killProcess();
        assertEquals("WINE(WINE is not an emulator)",TaskManager.front.name);
    }

    @Test
    public void testSortProcesses(){          
        Process proc4 = new Process(21,true,"Adobe","tanmay_linux",4,10.1,12.1);
        Process proc5 = new Process(2,true,"Nautilus","tanmay_linux",5,10.1,12.1);
        Process proc6 = new Process(12,true,"Gparted","tanmay_linux",7,10.1,12.1);
        Process proc7 = new Process(32,true,"ActiveDirectory","tanmay_linux",8,10.1,12.1);
        Process proc8 = new Process(42,true,"Chrome","tanmay_linux",4,10.1,12.1);
        Process proc9 = new Process(98,true,"Firefox","tanmay_linux",4,10.1,12.1);
        Process proc10 = new Process(52,true,"SublimeText","tanmay_linux",4,10.1,12.1);
        Process proc11 = new Process(24,true,"VScode","tanmay_linux",4,10.1,12.1);
        Process proc12 = new Process(28,true,"Ubuntu Software Center","tanmay_linux",4,10.1,12.1);
        TaskManager.startProcess(proc4);
        TaskManager.startProcess(proc5);        
        TaskManager.startProcess(proc6);
        TaskManager.startProcess(proc7);        
        TaskManager.startProcess(proc8);
        TaskManager.startProcess(proc9);        
        TaskManager.startProcess(proc9);
        TaskManager.startProcess(proc10);        
        TaskManager.startProcess(proc11);        
        TaskManager.startProcess(proc12);        
        TaskManager.sortProcesses();
        assertEquals(16,TaskManager.processQueueCapacity);
        assertEquals(11,TaskManager.liveProcessCount);        
    }
}