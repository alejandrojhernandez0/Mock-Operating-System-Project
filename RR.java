import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class RR {
  
  public void main(ArrayList<pcb> array, int availableMemory) {
    
    ArrayList<pcb> myList = array;
    
    Scanner reader = new Scanner(System.in);
    System.out.println("Enter time quantum value: ");//User Input for Quantum
    int timeQuantum = reader.nextInt();
    int dispatch = 1; //Constant dispatch value
    
    //Process List display
    System.out.println("Processes List:");
    for(pcb pcb21: myList)
    {
      //Display of process information
      String message = "P ID: " + pcb21.getId() + " arrives at T=" + pcb21.getArrival() + " and requires " + pcb21.getCycles() + " cycles";
      if (pcb21.getIO() == -1){message += " and has no IO event";}
      else {message += " and has an IO event at cycle " + pcb21.getIO();}
      System.out.println(message);
    }
    System.out.println();
    
    
    //Variable tracking total cycles in entire set of processes
    //This later takes into account the dispatcher and the quantum penalties
    //Variable gets acculumated by number of cycles in every process
    int totalCycles = 0;
    for(pcb pcb21: myList)
    {
      totalCycles += pcb21.getCycles();
    }
    
    //Sorting pcb and setting up time variables in the running of the processes
    Collections.sort(myList, new pcb.arrivalComparator()); //sorting by arrival
    int time = myList.get(0).getArrival();
    int over = 0;
    int first = 0;
    
    System.out.println();
    System.out.println("Round Robin (Quantum "+ timeQuantum+")");
    
    //Runs the processes
    while(totalCycles > 0 || myList.size() > 0)
    {
      for(int i = 0; i < myList.size(); i++)//Goes through the pcb
      {         
        if(myList.get(i).getCycles()>timeQuantum){ //if cycle time is greater than the time quantum, move the process to the wait queue, subtract the quantum from cycles remaining
          
          //Exception for the first process, starts at the cycle the first process begins at
          if(myList.get(i).getArrival()-time == 0 && over < myList.size() && over != 0)
          {
            time = myList.get(i).getArrival()+1;
          }
          if(myList.get(i).getArrival()-time > 1)
          {
            time = myList.get(i).getArrival();
          }
          
          //Dispatch penalty is deployed
          int penalty = 0;
          if (over == 0){time=time-dispatch;}//Time variable exception for the first process deployed so that
                                             //the time is correctly adjusted
          //Goes through the penalty, penalty takes up time slots
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
          //Goes through process under the timeQuantum parameters
          while(quantumMax != timeQuantum)
          {
            myList.get(i).setCurrentCycle();
            //Checking for I/O on the cycle
            if(myList.get(i).getCurrentCycle() == myList.get(i).getIO())
            {
              //I/O is running, takes up a time slot
              System.out.println("T" + time + " I/O...");
              totalCycles++;
              myList.get(i).setCycles(myList.get(i).getCycles()+1);
            }
            else //If no I/O it runs the process
            {
              System.out.println("T" + time + " P ID: " + myList.get(i).getId() + " is running.");
            }
            time++;
            quantumMax++;
          }
          
          //Process is done because of the quantum being reached
          //and it is moved to the back of the queue in the pcb
          if (over == 0){time=time+dispatch;}
          System.out.println(" Max Quantum " + timeQuantum + " reached");
          totalCycles -= timeQuantum;
          myList.get(i).setCycles(myList.get(i).getCycles()-timeQuantum);
          pcb tail = myList.get(i);
          myList.remove(myList.get(i));
          myList.add(myList.size(),tail);
          i--;
        }
        
        //If the number of cycles the process currently has is less than the time quantum
        else if(myList.get(i).getCycles()<=timeQuantum)
        {
          //Dispatcher    
          first++;
          int penalty = 0;
          if(first == 1){time=time-dispatch;}
          while(penalty != dispatch)
          {
            System.out.println("T" + time + " Dispatch Penalty");
            time++;
            totalCycles++;
            penalty++;
          }
          
          //Run the process for however many cycles it has left
          System.out.println(" P ID: " + myList.get(i).getId() + " is now active.");
          int cyclesLeft = 0;
          int stop = 0;
          System.out.println("\nAvailable memory space: " +availableMemory + " memory units.");
          while(cyclesLeft != myList.get(i).getCycles())
          {
            myList.get(i).setCurrentCycle();
            //If I/O event happens in one of the last cycles...
            if(myList.get(i).getCurrentCycle() == myList.get(i).getIO())
            {
              System.out.println("T" + time + " I/O...");
              totalCycles++;
              myList.get(i).setCycles(myList.get(i).getCycles()+1);
            }
            else //If no I/O event the process runs through its last cycles
            {
              System.out.println("T" + time + " P ID: " + myList.get(i).getId() + " is running.");
              time++;           
            }
            cyclesLeft++;
          }
          System.out.println(" P ID: " + myList.get(i).getId() + " is complete");
          
          //Memory data display
          availableMemory += myList.get(i).getMemory();
          
          System.out.println(myList.get(i).getMemory()+" memory units freed.");
          System.out.println("Available memory space: " +availableMemory + " memory units.");
          
          //Process is removed from pcb once it is done with all of its cycles
          myList.remove(myList.get(i));
          over++;
          i--;
        }
      }
      break; //Break when entire set of processes is complete and return to menu
    }
  }
}
