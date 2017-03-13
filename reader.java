import java.util.Scanner;

public class verify {
  
  public static void main(String[] args) {
    boolean keepGoing = true;
    while(keepGoing){
      Scanner in = new Scanner(System.in);
      
      System.out.println("Type in anything to check for integers --> ");
      String input = in.nextLine();
      try{
        Integer.parseInt(input);
        System.out.println("This is an integer.");
      }
      catch(Exception e){
        System.out.println("Not an integer");
      }
      keepGoing = false;
    }
  }
  
}

import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


class generateData{
  
  public static void main(String[] args) {
    Random rand=new Random();
    int processId, processArrival, processCycles, hasIO, IOcycle; 
    int numProc = rand.nextInt(11) + 5;
    try {
      File file = new File("data.txt");
      if (!file.exists()) {file.createNewFile();}
      
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      for (int i=0; i<=numProc; i++){
        processArrival=rand.nextInt(20)+1;
        processCycles=rand.nextInt(10)+1;
        if (processCycles == 1) {hasIO = 0;}
        else {hasIO = rand.nextInt(2);}
        if (hasIO == 1){IOcycle = rand.nextInt(processCycles)+1;}
        else {IOcycle = -1;}
        String content = "";
        if (i==numProc){content = processArrival + " " + processCycles + " " + IOcycle;}
        else {content = processArrival + " " + processCycles + " " + IOcycle + "\n";}
        bw.write(content);
      }
      bw.close();
      
      System.out.println("Done");
    }
    catch(IOException e) {e.printStackTrace();}
    
    
    
    //add desired variables here
    
    
    
    
  }
}
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

class pcb{
  Random rand=new Random();
  public int processId, processArrival, processCycles, hasIO, IOcycle; //add desired variables here
  pcb() {
    //String s = "dwe";
    //processId=rand.nextInt(25-5)+5; 
    
    processArrival=rand.nextInt(20)+1;
    processCycles=rand.nextInt(10)+1;
    if (processCycles == 1) {hasIO = 0;}
    else {hasIO = rand.nextInt(2);}
    if (hasIO == 1){IOcycle = rand.nextInt(processCycles)+1;}
    else {IOcycle = -1;}
    String phrase = "1 5 3\n2 4 5";
    String lineDelim = "[\n]+";
    String[] processes = phrase.split(lineDelim);
    for (int procNum=0; procNum<=1; procNum++){
      String process = processes[procNum];
      String procDelim = "[ ]+";
      String[] tokens = process.split(procDelim);
      for (int token=0; token<=3; token++){
        System.out.println(tokens[token]);
      }
    }
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
  
  public void setId(int x){processId=x;}
  
  
  public static void main(String[] args) {
    ArrayList<pcb> myList = new ArrayList<pcb>(); //arraylist of type pcb definition
    //int numP = 10;
    //for(int i=0; i<numP; i++){ //create 10 pcbs
    //  myList.add(new pcb());
    //}
    myList.add(new pcb()); // randomly generate a pcb
    myList.add(new pcb(100,50,25)); // manually generate a pcb
    Collections.sort(myList, new arrivalComparator()); //apply the sort defined in the comparator
    
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
