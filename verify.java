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

