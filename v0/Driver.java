import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.lang.management.*;



public class Driver {

    public static void main(String[] args) {
        String pathS = "/Users/Josefinetusindfryd/Desktop/dynamic_relative_compression/data/dna.50MB";
        String pathR = "/Users/Josefinetusindfryd/Desktop/dynamic_relative_compression/DNA_R";
        String S, R;
        try{
            FileHandler f = new FileHandler();
            System.out.println("Reading..");
            S = f.readFromFileLine(pathS);
            R = f.readFromFileLine(pathR);
            System.out.println("Compressor..");
            Compressor cmp = new CompressorSuffix(R);
            System.out.println("Dynamic Operations..");
            DynamicOperations dop = new DynamicOperationsNaive(cmp.encode(S), cmp);
            System.out.println("Create Running Time..");
            RunningTime rt = new RunningTime(dop);

            long[] accessTime, replaceTime, deleteTime, insertTime;

            System.out.println("Measure running time access..");
            accessTime = rt.runTimeAccess(100000);
            System.out.println("Measure running time replace..");
            replaceTime = rt.runTimeReplace(100000, 'T');
            System.out.println("Measure running time delete..");
            deleteTime = rt.runTimeDelete(100000);
            System.out.println("Measure running time insert..");
            insertTime = rt.runTimeInsert(100000, 'A');

            System.out.println("Access CPU: " + accessTime[0]/1000000.0);
            System.out.println("Access Real: " + accessTime[1]/1000000.0);
            System.out.println("Replace CPU: " + replaceTime[0]/1000000.0);
            System.out.println("Replace Real: " + replaceTime[1]/1000000.0);
            System.out.println("Delete CPU: " + deleteTime[0]/1000000.0);
            System.out.println("Delete Real: " + deleteTime[1]/1000000.0);
            System.out.println("Insert CPU: " + insertTime[0]/1000000.0);
            System.out.println("Insert Real: " + insertTime[1]/1000000.0
            );


        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}