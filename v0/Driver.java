import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.lang.management.*;



public class Driver {

    public static void main(String[] args) {

      ArrayList<Block> C = new ArrayList<Block>();
      C.add(new Block(0,5));
      C.add(new Block(0,3));
      C.add(new Block(0,8));
      C.add(new Block(0,4));
      C.add(new Block(0,3));
      C.add(new Block(0,1));
      C.add(new Block(0,1));
      C.add(new Block(0,2));
      C.add(new Block(0,7));
      C.add(new Block(0,3));
      C.add(new Block(0,5));
      C.add(new Block(0,6));
      C.add(new Block(0,3));
      C.add(new Block(0,2));
      C.add(new Block(0,4));
      C.add(new Block(0,6));



        BinaryTree B = new BinaryTree(C);
        for (int t = 0; t <= 63; t++){
          System.out.println(B.search(t));
        }


       // B.prettyPrintBinary(B.root, 0);



       /* String pathS = "/Users/AndreasLauritzen/dynamic_relative_compression/dna";
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
        }

*/

    }


}