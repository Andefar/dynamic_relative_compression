import java.util.ArrayList;

/**
 * Created by andl on 08/02/2016.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicOperationsNaive {

    private ArrayList<Block> C;
    private char[] RA;

    public DynamicOperationsNaive(ArrayList<Block> C, char[] RA){
        this.C = C;
        this.RA = RA;
    }

    public ArrayList<Block> getC() { return this.C; }


    // Return character S[i]
    // Index out of bound exception
    public char access(int index) {
        if (index >=0) {
            int start = 0;

            // loop through all blocks to find the block containing the specified index
            for (Block b : this.C) {

                if (index <= (start + b.getLength() - 1)) { // current block contains the element to be accessed
                    return RA[b.getPos() + (index - start)];
                }

                start += b.getLength();
            }
        }

        throw new IndexOutOfBoundsException("Index must be positive and may not exceed the number of elements in S");
    }



    // Return the block which contains the specified position and the start position of that block in the original string
    public int[] getBlockandStartPos(int index) {
        if (index >=0) {
            int start = 0, j = 0;

            // loop through all blocks to find the block containing the specified index
            for (Block b : this.C) {

                if (index <= (start + b.getLength() - 1)) { // current block is match
                    return new int[]{j, start};
                }

                start += b.getLength();
                j += 1;
            }
        }

        throw new IndexOutOfBoundsException("Index must be positive and may not exceed the number of elements in S");
    }




    public void replace(int i, char sub) {
        int pos = 0;

        for (int j = 0; j < this.C.size(); j++) {

            Block b = this.C.get(j);
            int l = b.getLength();
            int p = b.getPos();
            int curLen = pos + l;

            if (curLen < i) { continue;}
            else {


                int distPosToChar = l - (curLen - i);
                int subPosInRA = -1;
                for (int k = 0; k < RA.length; k++) {
                    if (RA[k] == sub) {
                        subPosInRA = k;
                    }
                }
                Block first = new Block(p,distPosToChar - 1);
                Block replace = new Block(subPosInRA,1);
                Block last = new Block(p+distPosToChar,curLen - i);
                this.C.remove(j);
                this.C.add(j,last);
                this.C.add(j,replace);
                this.C.add(j,first);

            }
        }

    }
    public void insert(int i, char c) {}
    public void delete(int i) {}

}
