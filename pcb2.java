import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Scanner;

class pcb2{
  public static void main(String[] args) {
    
    ArrayList<pcb2> myList = new ArrayList<pcb2>();
    int timeQuantum = 2;
    int dispatch = 2;
    
    Scanner reader = new Scanner(System.in);
    System.out.println("Enter number of processes: "); //numProcess defined by user for now
    int n = reader.nextInt();
    System.out.println("Enter time quantum value: ");
    timeQuantum = reader.nextInt();
    System.out.println("Enter dispatch value: ");
    dispatch = reader.nextInt();
    
    for(int i=0; i<n; i++){
      myList.add(new pcb2());
    }
    
    System.out.println("Processes List:");
    for(pcb2 pcb21: myList)
    {
      String message = "P ID: " + pcb21.getId() + " arrives at T=" + pcb21.arrivalTime() + " and requires " + pcb21.getCycles() + " cycles";
      if (pcb21.getIO() == -1){message += " and has no IO event";}
      else {message += " and has an IO event at cycle " + pcb21.getIO();}
      System.out.println(message);
    }
    System.out.println();
    
    int totalCycles = 0;
    for(pcb2 pcb21: myList)
    {
      totalCycles += pcb21.getCycles();
    }
    
    int time2 = 0;
    int over2 = 0;
    int totalCycles2 = 0;
    for(pcb2 pcb21: myList)
    {
      totalCycles2 += pcb21.getCycles();
    }
   
    Collections.sort(myList, new arrivalComparator()); //sorting by arrival
    int time = myList.get(0).arrivalTime();
    int over = 0;
    int check = 0;
    
    System.out.println();
    System.out.println("Round Robin (Quantum 2)");
    while(totalCycles > 0)
    {
      for(int i = 0; i < myList.size(); i++)
      {         
        if(myList.get(i).getCycles()>timeQuantum && myList.get(i).arrivalTime()-time < 1){ //if cycle time is greater than the time quantum, move the process to the wait queue, subtract the quantum from cycles remaining
          
          if(myList.get(i).arrivalTime()-time == 0 && over < myList.size() && over != 0)
          {
            time = myList.get(i).arrivalTime()+1;
          }
          
          int penalty = 0;
          while(penalty != dispatch)
          {
            System.out.println("T" + time + " Dispatch Penalty");
            time++;
            totalCycles++;
            penalty++;
          }
          
          //Round Robin Quantum with I/O
          int quantumMax = 0;
          System.out.println(" P ID: " + myList.get(i).getId() + " is now active.");
          while(quantumMax != timeQuantum)
          {
            myList.get(i).setCurrentCycle();
            if(myList.get(i).getCurrentCycle() == myList.get(i).getIO())
            {
              System.out.println("T" + time + " I/O...");
              totalCycles++;
              myList.get(i).setCycles(myList.get(i).getCycles()-1);
            }
            else
            {
              System.out.println("T" + time + " P ID: " + myList.get(i).getId() + " is running.");
            }
            time++;
            quantumMax++;
          }
          
          System.out.println(" Max Quantum " + timeQuantum + " reached");
          totalCycles -= timeQuantum;
          myList.get(i).setCycles(myList.get(i).getCycles()-timeQuantum);
          pcb2 tail = myList.get(i);
          myList.remove(myList.get(i));
          myList.add(myList.size(),tail);
        }
        
        else if(myList.get(i).getCycles()<=timeQuantum && myList.get(i).arrivalTime()-time < 1)
        {
          //Dispatcher
          int penalty = 0;
          while(penalty != dispatch)
          {
            System.out.println("T" + time + " Dispatch Penalty");
            time++;
            totalCycles++;
            penalty++;
          }
          
          int cyclesLeft = 0;
          while(cyclesLeft != myList.get(i).getCycles())
          {
            System.out.println("T" + time + " P ID: " + myList.get(i).getId() + " is running.");
            time += 1;
            cyclesLeft++;
          }
          System.out.println(" P ID: " + myList.get(i).getId() + " is complete");
          myList.remove(myList.get(i));
          over++;
        }  
      } 
    }
  }

  Random rand=new Random();
  public int processId, processArrival, processCycles, processIO, currentCycle; //add desired variables here
  pcb2() {
    processId=rand.nextInt(25-5)+5; 
    processArrival=rand.nextInt(25-5)+5;
    processCycles=rand.nextInt(((processId*5)-0)+0);
    processIO=rand.nextInt(processArrival-(processArrival-1));
    currentCycle=0;
    while(processIO == 0)
    {
      processIO=rand.nextInt(processArrival);
    }
  }
  static class arrivalComparator implements Comparator<pcb2>{ 
    public int compare(pcb2 p1, pcb2 p2){
      int result=p1.arrivalTime()-p2.arrivalTime();
      if (result==0){
        return p1.getId()-p2.getId();
      }
      else{
        return result;
      }
    }
  }
  public int arrivalTime(){
    return processArrival;
  }
  
  public int getId(){
    return processId;
  }
  
  public int getCycles(){
    return processCycles;
  }
  
  public int getCurrentCycle() {
    return currentCycle;
  }
  
  public int setCurrentCycle() {
    currentCycle = currentCycle+1;
    return currentCycle;
  }
  
  public int setCycles(int cyclesLeft)
  {
    processCycles = cyclesLeft;
    return processCycles;
  }
  
  public int getIO()
  {
    return processIO;
  }
}