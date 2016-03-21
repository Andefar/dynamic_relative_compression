
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



abstract class Compressor {


    protected char[] RA;

    public Compressor(String R) {
        this.RA = (R+"$").toCharArray();
    }

    public abstract ArrayList<Block> encode(String S);
    public abstract int indexOf(char[] C);
    public abstract String decode(ArrayList<Block> C);
    //public abstract String decodeBinTree(BinaryTree B);
    //public abstract

}