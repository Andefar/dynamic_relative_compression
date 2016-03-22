import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.management.*;



public class Driver {
    public static void main(String[] args) {


         ArrayList<Block> C = new ArrayList<Block>();
         String pathS = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/data/dna.50MB";
         String pathR = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/DNA_R";
         String S, R;
         try{
             FileHandler f = new FileHandler();
             System.out.println("Reading..");
             S = f.readFromFileLine(pathS);
             R = f.readFromFileLine(pathR);
             System.out.println("Compressor..");
             // INSERT COMPRESSORTYPE
             Compressor cmpDPS = new CompressorDPS(R);
             ArrayList<Block> cp = cmpDPS.encode(S);
             System.out.println("Dynamic Operations..");
             // INSERT DYNAMIC OPERATIONSTYPE
             DynamicOperations dop = new DynamicOperationsDPS(cp, cmpDPS);
             System.out.println("Create Running Time..");
             RunningTime rtDPS = new RunningTime(dop);

    //             long[] accessTime, replaceTime, deleteTime, insertTime;


             // Try inserting a substring of the original string in the string
             // the indices are generated at random
             int insertLength = 200;
             int SLength = S.length();
             String insertPartOfS = S.substring(0, insertLength);
             int insertIndex;

             long CpuTimeStart;
             char c;

             long insertTime = 0;

             for (int i = 0; i < insertLength; i++){
                insertIndex = (int) (Math.random() * (double) SLength);
                c = insertPartOfS.charAt(i);

                CpuTimeStart= rtDPS.getCpuTime();
                dop.insert(insertIndex, c);
                insertTime += rtDPS.getCpuTime() - CpuTimeStart;
             }

             System.out.println("INSERTING 200 CHARACTERS TOOK CPU: " + insertTime/1000000.0);

         } catch (IOException e) {
             e.printStackTrace();
         }
    }
}