import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.io.*; 

class pcb{
 Random rand=new Random();
 public int processId, processArrival, processCycles, hasIO, IOcycle, currentCycle=0; //add desired variables here
 public String fName;
 pcb() { 
  
  processArrival=rand.nextInt(20)+1;
  processCycles=rand.nextInt(10)+1;
  if (processCycles == 1) {hasIO = 0;}
  else {hasIO = rand.nextInt(2);}
  if (hasIO == 1){IOcycle = rand.nextInt(processCycles)+1;}
  else {IOcycle = -1;}
  

 }
 
 pcb(int arrive, int cycle, int IOc){
  processArrival = arrive;
  processCycles = cycle;
  IOcycle = IOc;
 }
 
 
 static class arrivalComparator implements Comparator<pcb>{ 
  public int compare(pcb p1, pcb p2){
   int result=p1.getArrival()-p2.getArrival();
   if (result==0){
    return p1.getId()-p2.getId();
   }
   else{
    return result;
   }
  }
 }
 
 public int getId(){return processId;}
 public int getArrival(){return processArrival;}
 public int getCycles(){return processCycles;}
 public int getIO() {return IOcycle;}
 public int getCurrentCycle(){return currentCycle;}
 public void setId(int x){processId=x;}
 public void setArrival(int x){processArrival=x;}
 public void setCycles(int x){processCycles=x;}
 public void setIO(int x){IOcycle=x;}
 public void setCurrentCycle(){currentCycle = currentCycle+1;}
 
 
    public static void main(String[] args) {
        ArrayList<pcb> myList = new ArrayList<pcb>(); //arraylist of type pcb definition
 
  
  String fileName = "data.txt";
  String input = "";
  //int numSpace = 0;
  int numLine = 0;
  
  
  try{ 
   BufferedReader reader = new BufferedReader(new FileReader(fileName));
   String line;
   StringBuffer inputBuffer = new StringBuffer();
   try{
    while(null!= (line=reader.readLine())){
     inputBuffer.append(line);
     inputBuffer.append("\n");
     numLine += 1;
    }
   }
   catch(IOException e) {System.out.println("Error. Cannot read.");}
   input = inputBuffer.toString();
   try{reader.close();}
   catch(IOException e) {System.out.println("Error. Cannot close.");}
  }
  
  catch(FileNotFoundException e) {System.out.println("Error. File not found.");}
  System.out.println(numLine);
  String lineDelim = "[\n]+";
  String[] processes = input.split(lineDelim);
  for (int procNum=0; procNum<numLine; procNum++){
   String process = processes[procNum];
   System.out.println(process);
   String procDelim = "[ ]+";
   String[] tokens = process.split(procDelim);
   int Arrival = 0;
   int Cycle = 0;
   int Io = 0;
   for (int token=0; token<=2; token++){
    //System.out.println(tokens[token]);
    Arrival = Integer.parseInt(tokens[0]);
    Cycle = Integer.parseInt(tokens[1]);
    Io = Integer.parseInt(tokens[2]);
   }
   myList.add(new pcb(Arrival, Cycle, Io));
  }
  Collections.sort(myList, new arrivalComparator());
  
  int count = 1;
  for(pcb p : myList){
   p.setId(count);
   String IOmessage = "";
   if (p.getIO() == -1){IOmessage = " and has no IO event";}
   else {IOmessage = " and has an IO event at cycle " + p.getIO();}
   System.out.println("P ID: " + p.getId() + " arrives at T=" + p.getArrival() + " and requires " + p.getCycles() + " cycles" + IOmessage);
   count++;
  }
 }
}