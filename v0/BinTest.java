import java.util.ArrayList;

/**
 * Created by andl on 29/02/2016.
 */
public class BinTest {


    public static void main(String[] args) {
        ArrayList<Block> test = new ArrayList<Block>();
        test.add(new Block(1,2));
        test.add(new Block(2,4));
        test.add(new Block(1,1));
        test.add(new Block(3,8));
        test.add(new Block(7,4));
        test.add(new Block(3,3));
        test.add(new Block(7,4));
        test.add(new Block(3,1));
        test.add(new Block(1,2));
        test.add(new Block(2,4));
        test.add(new Block(1,1));
        test.add(new Block(3,8));
        test.add(new Block(7,4));
        test.add(new Block(3,3));
        test.add(new Block(7,4));
        test.add(new Block(3,1));
        test.add(new Block(1,2));
        test.add(new Block(2,4));
        test.add(new Block(1,1));
        test.add(new Block(3,8));
        test.add(new Block(7,4));
        test.add(new Block(3,3));
        test.add(new Block(7,4));
        test.add(new Block(3,1));



        BinaryTree bt = new BinaryTree(test);
        //bt.prettyPrintBinary(bt.root,0);



    }


}
