import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class FCFS {
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
  
  public void main(ArrayList<pcb> array) {
    Scanner in = new Scanner(System.in);
    ArrayList<pcb> myList = array;
    Collections.sort(myList, new pcb.arrivalComparator());
    int time = myList.get(0).getArrival()-1;
    
    System.out.println("Enter dispatch value: ");
    int dispatch = in.nextInt();
    
    for(pcb p: myList){
      for(int i=1;i<=p.getCycles();i++){
        time += 1;
        System.out.println("T"+time+" Process "+p.getId()+" is running." );
      }
      for(int i=1;i<=dispatch;i++){
        System.out.println("T "+time+" Dispatch");
        time += 1;
        
        
      }
      System.out.println("Process "+p.getId()+" is complete.");
    }
  }
}