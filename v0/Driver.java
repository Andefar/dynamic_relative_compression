import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.management.*;



public class Driver {
    public static void main(String[] args) {

        String S = "ccabcbaabccba";
        String R = "cbaaaccba";
        Compressor cmpDPS = new CompressorDPS(R);
        ArrayList<Block> cp = cmpDPS.encode(S);
        DynamicOperationsSC dop = new DynamicOperationsSC(cp, cmpDPS);
        dop.getDPS().prettyPrintBinary(dop.getDPS().getRoot(),0);
        dop.replace(7, (char) 'b');
        dop.getDPS().prettyPrintBinary(dop.getDPS().getRoot(),0);

        System.out.println(dop.cmp.decodeBinTree(dop.getDPS()));

/*
        // only for test purposes;
            KMPAlgo stm = new KMPAlgo();
            // pattern
            char[] ptrn1 = "abca".toCharArray();
            char[] ptrn2= "bdabc".toCharArray();


            char[] text = "abcabdabcabeabcabdabcabd".toCharArray();
            System.out.print(" ");
            for (char c : text) {
                System.out.print(c + "   ");
            }
            System.out.println();
            // search for pattern in the string
            stm.searchSubStringS(text, ptrn1, ptrn2);

*/
        /*
         ArrayList<Block> C = new ArrayList<Block>();
         String pathS = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/data/dna.50MB";
         String pathR = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/DNA_R";
         String S, R;

             FileHandler f = new FileHandler();
             System.out.println("Reading..");
             //S = f.readFromFileLine(pathS);
             //R = f.readFromFileLine(pathR);
             S = "aaabcbcbcbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc";
             R = "bcbcbcbcccabababaa";
             System.out.println("Compressor..");
             // INSERT COMPRESSORTYPE
             Compressor cmpDPS = new CompressorDPS(R);
             ArrayList<Block> cp = cmpDPS.encode(S);
             System.out.println("Dynamic Operations..");
             // INSERT DYNAMIC OPERATIONSTYPE
             DynamicOperationsDPS dop = new DynamicOperationsDPS(cp, cmpDPS);
             System.out.println("Create Running Time..");
             RunningTime rtDPS = new RunningTime(dop);

        DynamicOperationsDPS dop1 = new DynamicOperationsDPS(cp, cmpDPS);


        TimeAndHeightTest test = new TimeAndHeightTest(dop1);
        System.out.println("Initial height:" + test.dop.getDPS().getMaxHeight());
       test.dop.getDPS().prettyPrintBinary(test.dop.getDPS().getRoot(),0);
        System.out.println("TEST: " + test.insertCharactersSingle(S, 100)[0] );
        //System.out.println("HEIGHT: " + test.insertCharactersBlock(S, 100, 50)[0] );
        System.out.println("final height:" + test.dop.getDPS().getMaxHeight());
        test.dop.getDPS().prettyPrintBinary(test.dop.getDPS().getRoot(),0);
        */
        /*

                System.out.println("Create Running Time..");
       // RunningTime rtDPS1 = new RunningTime(dop1);

    //             long[] accessTime, replaceTime, deleteTime, insertTime;


             // Try inserting a substring of the original string in the string
             // the indices are generated at random

             int insertLength = 100;
             int L = insertLength;

             int SLength = S.length();
             System.out.println("S length: "+ SLength);
             String insertPartOfS = S.substring(0, insertLength);
             int insertIndex;
             int insertNumber;
             long CpuTimeStart;
             char c;

             long insertTime = 0;


             //Insert single characters
             for (int i = 0; i < insertLength; i++){
                insertIndex = (int) (Math.random() * (double) SLength);
                c = insertPartOfS.charAt(i);
                CpuTimeStart= rtDPS.getCpuTime();
                dop.insert(insertIndex, c);
                insertTime += (rtDPS.getCpuTime() - CpuTimeStart);
             }

             System.out.println("INSERTING 200 CHARACTERS TOOK CPU: " + insertTime);

             //TimeAndHeightTest test = new TimeAndHeightTest(dop1);

             //System.out.println("TEST: " + test.insertCharactersSingle(S, 100)[1] );

             insertTime = 0;
             // Insert block of between 1 and 50 characters
             while (L > 0) {
                 insertIndex = (int) (Math.random() * (double) (SLength - 50)) ;
                 //System.out.println("Inserting: at" + insertIndex);

                 if (L <= 50){
                     //System.out.println("Inserting block of length XX  : " + insertLength);
                     for (int i = 0; i < L; i++){
                         c = insertPartOfS.charAt((insertLength - L) + i);
                         //System.out.println(" char in substring: " + ( insertLength - L + i));
                         CpuTimeStart= rtDPS.getCpuTime();
                         dop.insert(insertIndex + i, c);
                         insertTime += (rtDPS.getCpuTime() - CpuTimeStart);
                     }
                     L = 0;
                 } else {
                     insertNumber = (int) (Math.random() * 50);
                     //System.out.println("Inserting block of length: " + insertNumber);
                     for (int i = 0; i < insertNumber; i++){
                         c = insertPartOfS.charAt(( insertLength - L) + i);
                        // System.out.println(" char in substring: " + ( insertLength - L + i));
                         CpuTimeStart= rtDPS.getCpuTime();
                         dop.insert(insertIndex + i, c);
                         insertTime += (rtDPS.getCpuTime() - CpuTimeStart);
                     }
                     L = L - insertNumber;
                 }

             }

            System.out.println("Initial Height: " + dop.getDPS().getMaxHeight());
            System.out.println("INSERTING 200 CHARACTERS in blocks TOOK CPU: " + insertTime);
            System.out.println("final Height: " + dop.getDPS().getMaxHeight());*/








    }
}