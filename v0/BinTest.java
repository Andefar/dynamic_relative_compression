import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andl on 29/02/2016.
 */
public class BinTest {

    public static void main(String[] args) {
//        String pathS = "/Users/AndreasLauritzen/dynamic_relative_compression/dna";
//        String pathR = "/Users/AndreasLauritzen/dynamic_relative_compression/DNA_R";
//        String S, R;
//        try{
//            FileHandler f = new FileHandler();
//            System.out.println("Reading..");
//            S = f.readFromFileLine(pathS);
//            R = f.readFromFileLine(pathR);
//            System.out.println("Compressing..");
//            Compressor cmp = new CompressorSuffix(R);
//            ArrayList<Block> cp = cmp.encode(S);
//            System.out.println("Building binary tree..");
//            BinaryTree bt = new BinaryTree(cp);
//            System.out.println("Done!");
//
//            bt.update(2,5);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ArrayList<Block> C = new ArrayList<Block>();
        C.add(new Block(2,5));
        C.add(new Block(2,3));
        C.add(new Block(2,8));
        C.add(new Block(2,4));
        C.add(new Block(2,3));
        C.add(new Block(2,1));
        C.add(new Block(2,1));
        C.add(new Block(2,2));
        C.add(new Block(2,7));
        C.add(new Block(2,3));
        C.add(new Block(2,5));
        C.add(new Block(2,6));
        C.add(new Block(2,3));


        BinaryTree bt = new BinaryTree(C);
        bt.update(10,1);



    }
}
