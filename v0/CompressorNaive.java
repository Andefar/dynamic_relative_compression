import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Josefinetusindfryd on 14/02/16.
 */

//Initial version of string compression - naive


public class CompressorNaive extends Compressor{

    public CompressorNaive(String R) {
        super(R);
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

        //O(|S|) : every char is considered in S
        while (c != '$'){
            indexTemp = indexR;
            C = Arrays.copyOf(C, C.length+1);
            C[C.length-1] = c;

            indexR = indexOf(C,RA);

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
// O(|R|^2) : C might be the length of R
    public int indexOf(char[] C, char[] R){
        int index = -1;

        for (int i = 0; i < R.length; i++){

            for (int j = 0; j < C.length; j++){

                if (i+j < R.length && C[j] != R[i+j] ){
                    break;
                } else if (j == C.length -1){
                    index = i;
                }
            }
            if (index != -1){ return index;};
        }
        return -1;
    }



    // Decode a compressed representation, D,  of string S with reference to R
    // Evt ikke
    public String decode(ArrayList<Block> C){
        String S = "";
        int p;
        int l;

        for (Block b : C ){
            p = b.getPos();
            l = b.getLength();
            S += new String((Arrays.copyOfRange(RA, p, p+l)));
            //S += (Arrays.copyOfRange(RA, p, p+l)).toString();
            //System.out.println((Arrays.copyOfRange(RA, p, p+l)));
        }

        return S;
    }

}