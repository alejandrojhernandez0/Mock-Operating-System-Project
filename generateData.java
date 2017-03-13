import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


class generateData{

 public static void main(String[] args) {
  Random rand=new Random();
  int processArrival, processCycles, hasIO, IOcycle; 
  //int numProc = rand.nextInt(11) + 5;
  int numProc = 4;
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