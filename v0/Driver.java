import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.management.*;

public class Driver {
        public static void main(String[] args) {
                //PLEASE RUN THIS WITH 8GB MAX HEAP SPACE
                System.out.println("Max heap size = " + Runtime.getRuntime().maxMemory()/(1024*1024) + "MB");

                //String pathS = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/data/dna.50MB";
                //String pathR = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/DNA_R";
                //String pathSave = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/";
                String pathS = "/Users/AndreasLauritzen/dynamic_relative_compression/dna.400mb";
                String pathR = "/Users/AndreasLauritzen/dynamic_relative_compression/DNA_S2";
                String pathSave = "/Users/AndreasLauritzen/dynamic_relative_compression/";

                //Read the source file
                String S=null, R=null;
                FileHandler f = new FileHandler();
                try {
                        S = f.readFromFileLine(pathS);
                        R = f.readFromFileLine(pathR);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                //Create Compressor
                long t0 = System.nanoTime();
                Compressor cmpDPS = new CompressorDPS(R);
                long time0 = System.nanoTime() - t0;
                System.out.println("Created suffix tree in\t\t\t " + time0/1000000000.0 +" seconds");

                //Encode the source string
                long t1 = System.nanoTime();
                ArrayList<Block> cp = cmpDPS.encode(S);
                long time1 = System.nanoTime() - t1;
                System.out.println("Encoded string in\t\t\t " + time1/1000000000.0 +" seconds");

                //Create DynamicOperations
                long t2 = System.nanoTime();
                DynamicOperationsDPS dop1 = new DynamicOperationsDPS(cp, cmpDPS);
                long time2 = System.nanoTime() - t2;
                System.out.println("Created structures in\t " + time2/1000000000.0 +" seconds");


                //Write Compressed file
                long t3 = System.nanoTime();
                f.stringToFile(dop1.getDPS(),"compressed",pathSave);
                long time3 = System.nanoTime() - t3;
                System.out.println("Wrote file in\t\t\t " + time3/1000000000.0 +" seconds");

        /*
        TimeAndHeightTest test = new TimeAndHeightTest(dop1);
        System.out.println("Initial height of binary tree: " + test.dop.getDPS().getMaxHeight());
        System.out.println("final height of binary tree:" + test.dop.getDPS().getMaxHeight());
        */

        }
}