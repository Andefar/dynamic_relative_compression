import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andl on 29/02/2016.
 */
public class BinTest {

    public static void main(String[] args) {

        ArrayList<Block> C = new ArrayList<Block>();
        /*
        C.add(new Block(0,5));
        C.add(new Block(1,3));
        C.add(new Block(2,8));
        C.add(new Block(3,4));
        C.add(new Block(4,3));
        C.add(new Block(5,1));
        C.add(new Block(6,1));
        C.add(new Block(7,2));
        C.add(new Block(8,7));
        C.add(new Block(9,3));
        C.add(new Block(10,5));
        C.add(new Block(11,6));
        C.add(new Block(12,3));
        C.add(new Block(13,2));
        C.add(new Block(14,4));
        C.add(new Block(15,6));
        */
        C.add(new Block(0,3));
        C.add(new Block(4,2));
        C.add(new Block(0,5));
        C.add(new Block(0,1));


        BinaryTree bt = new BinaryTree(C);

        System.out.println("i\t\tblock\tpos\t\tlen");

        for (int i = 0; i <= 11; i++) {

            int p1 = bt.searchReturnIndexSIR(i)[0];
            int p2 = bt.searchReturnIndexSIR(i)[1];
            int p3 = bt.searchReturnIndexSIR(i)[2];
            System.out.println(i + "\t\t" + p1 + "\t\t"+ p2 +"\t\t" + p3);


        }


    }
}
