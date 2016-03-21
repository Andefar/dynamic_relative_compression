import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.lang.management.*;



public class Driver {
    public static void main(String[] args) {

        String S0 = "bacaaabcbc";
        //String S1 = "aaabcbcbcbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc";
        String ST0 = S0;
        //String ST1 = S1;
        String R0 = "abcaa";
        //String R1 = "acbabbababaacbcca";
        //CompressorNaive cmp0 = new CompressorNaive(R0);
        //CompressorNaive cmp1 = new CompressorNaive(R1);
        //ArrayList<Block> encodedS0 = cmp0.encode(ST0);
       // ArrayList<Block> encodedS1 = cmp1.encode(ST1);

        //CompressorSuffix cmp2 = new CompressorSuffix(R0);
        //CompressorSuffix cmp3 = new CompressorSuffix(R1);
        //ArrayList<Block> encodedS2 = cmp2.encode(ST0);
        //ArrayList<Block> encodedS3 = cmp3.encode(ST1);

        CompressorDPS cmp4 = new CompressorDPS(R0);
        //CompressorDPS cmp5 = new CompressorDPS(R1);
        ArrayList<Block> encodedS4 = cmp4.encode(ST0);
        //ArrayList<Block> encodedS5 = cmp5.encode(ST1);

        DynamicOperationsDPS ops1 = new DynamicOperationsDPS(encodedS4, cmp4);
        ops1.dps.prettyPrintBinary(ops1.dps.getRoot(), 0);
        ops1.delete(1);
        String decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        System.out.println(decodedS1);

        //ops1.dps.prettyPrintBinary(ops1.dps.getRoot(), 0);

        //ops1.insert(5,(char) 'b');
        //ops1.dps.prettyPrintBinary(ops1.dps.getRoot(), 0);

        //String decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        //System.out.println(decodedS1);
        /*
        ops3.insert(10,(char) 'a');
        ops4.insert(0,(char) 'c');
        String decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        String decodedS2 = cmp4.decodeBinTree(ops2.getDPS());
        String decodedS3 = cmp4.decodeBinTree(ops3.getDPS());
        String decodedS4 = cmp4.decodeBinTree(ops4.getDPS());

*/



        /*
        ArrayList<Block> C = new ArrayList<Block>();
        String pathS = "/Users/AndreasLauritzen/dynamic_relative_compression/dna";
         String pathR = "/Users/AndreasLauritzen/dynamic_relative_compression/DNA_R";
         String S, R;
         try{
             FileHandler f = new FileHandler();
             System.out.println("Reading..");
             S = f.readFromFileLine(pathS);
             R = f.readFromFileLine(pathR);
             System.out.println("Compressor..");
             Compressor cmp = new CompressorSuffix(R);
             ArrayList<Block> cp = cmp.encode(S);
             System.out.println("Dynamic Operations..");
             DynamicOperations dop = new DynamicOperationsMerge(cp, cmp);
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
         }*/
    }
}