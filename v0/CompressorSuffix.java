import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Josefinetusindfryd on 14/02/16.
 */

//Version of string compression using suffix tree representation of R


public class CompressorSuffix extends Compressor{

    SuffixTree stR;

    public CompressorSuffix(String R) {
        super(R);
        this.stR = new SuffixTree(this.RA);
    }


    //O(|S|^2*|R|) :Iterate through string S and foreach character use indexOf
    public ArrayList<Block> encode(String S){
        S += "$";
        char[] SA = S.toCharArray();
        ArrayList compressed = new ArrayList<Block>();

        int indexR = -1;
        int indexTemp = -1;
        int counter = 0;
        char c = SA[counter];
        char[] C = {};
        char[] temp;
        //O(|S|) : every char is considered in S
        while (c != '$'){
            indexTemp = indexR;
            temp = new char[C.length+1];
            System.arraycopy(C,0,temp,0,C.length);
            C = temp;
            C[C.length-1] = c;

            indexR = indexOf(C);

            if (indexR == -1 || c == '$'){
                if (C.length == 1){

                    C = new char[0];
                    counter += 1;
                    c = SA[counter];
                } else {
                    compressed.add((new Block(indexTemp, C.length-1)));

                    C = new char[0];
                }
            } else {
                counter += 1;
                c = SA[counter];
                if (c == '$'){
                    compressed.add((new Block(indexR, C.length)));

                }
            }

        }

        return compressed;
    }


    // Return index of beginning of C in R. If not found return -1
    // Use suffix tree of R to search
    // O(|C|) : C might be the length of R
    public int indexOf(char[] C){
        return stR.search(C);
    }

    // Decode a compressed representation, D,  of string S with reference to R
    // Evt ikke
    public String decodeArrayList(ArrayList<Block> C){
        StringBuilder sb = new StringBuilder();
        int p,l;
        char[] temp;
        for (Block b : C ){
            p = b.getPos();
            l = b.getLength();
            temp = new char[l];
            System.arraycopy(RA,p,temp,0,l);
            sb.append(new String(temp));
        }

        return sb.toString();
    }

    // Decode a representation of S compressed using partial sums binary tree
    public String decodeBinTree(BinaryTree B){
        StringBuilder sb = new StringBuilder();
        //String S = "";
        int[] BS;

        char[] temp;
        for (int i = 0; i < B.getTotalLeafs(); i++ ){
            BS = B.find(i); // BS[0] = startPositionInR, BS[1] = length
            temp = new char[BS[1]];
            System.arraycopy(RA,BS[0],temp,0,BS[1]);
            sb.append(new String(temp));
        }

        return sb.toString();
    }

}
