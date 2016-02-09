import java.util.ArrayList;

/**
 * Created by andl on 08/02/2016.
 */

public class DynamicOperationsNaive extends DynamicOperations {

    public DynamicOperationsNaive(ArrayList<Block> C, char[] RA){
        super(C,RA);
    }

    public ArrayList<Block> getC() { return this.C; }


    // Return character S[i]
    // Index out of bound exception
    public char access(int index) throws IndexOutOfBoundsException {

        int[] BS = this.getBlockandStartPos(index);

        int p = this.C.get(BS[0]).getPos(); //BS[0] is the block holding the index - p is the start position of that block in R
        int start = BS[1]; //B[1] is the starting position of block BS[0] in S (the original string)

        return this.RA[p + (index - start)];
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
        //get all positions and distances
        int[] t = this.getBlockandStartPos(i);
        int blockNum = t[0];
        int blockPosInS = t[1];
        Block b = this.C.get(blockNum);
        int l = b.getLength();
        int p = b.getPos();
        int offsetInRA = i - blockPosInS;
        int indexInRA = p+offsetInRA;
        int charToReplace = RA[indexInRA];

        //replace with same char
        if(charToReplace == sub) { return;}

        //find the occurrence of sub char in RA
        int subPosInRA = -1;
        for (int k = 0; k < RA.length; k++) {
            if (RA[k] == sub) {
                subPosInRA = k;
            }
        }
        //remove the old block
        this.C.remove(blockNum);


        //when block can be split in 3, i is in the middle
        //i not first in block, i not the last in block, length is 3 or more
        if (offsetInRA > 0 && offsetInRA != (l-1)  && l >= 3) {

            Block first = new Block(p,offsetInRA);
            Block replace = new Block(subPosInRA,1);
            Block last = new Block(p+offsetInRA+1,l-(offsetInRA+1));
            this.C.add(blockNum,last);
            this.C.add(blockNum,replace);
            this.C.add(blockNum,first);

        }
        //i is the first in the block and length is at least 2
        else if (offsetInRA == 0 && l >= 2) {

            Block replace = new Block(subPosInRA,1);
            Block rest = new Block(p+1,l-1);
            this.C.add(blockNum,rest);
            this.C.add(blockNum,replace);
        }
        //i is the last index and length is at least 2
        else if (offsetInRA == (l-1) && l >= 2) {

            Block replace = new Block(subPosInRA,1);
            Block preBlock = new Block(p,l-1);
            this.C.add(blockNum,replace);
            this.C.add(blockNum,preBlock);
        }
        //replace with a single char
        else if (l == 1) {

            Block replace = new Block(subPosInRA,1);
            this.C.add(blockNum,replace);

        } else {
            throw new IllegalArgumentException("Case not covered");
        }

    }
    public void insert(int index, char c) {
        int[] BS = this.getBlockandStartPos(index);
        int blockNo = BS[0];
        int startPosInR = this.C.get(blockNo).getPos();
        int length = this.C.get(blockNo).getLength();
        int startPosInS = BS[1];
        int indexOfR = -1;

        for (int i = 0; i < RA.length; i++){
            if (RA[i] == c) {
                indexOfR = i;
                break;
            }
        }

        if ( index == startPosInS) { //insert in beginning of block

            this.C.remove(blockNo);
            this.C.add(blockNo, new Block(startPosInR, length));
            this.C.add(blockNo, new Block(indexOfR, 1));

        } else if ( index == startPosInS + length - 1){ //insert at the end of block

            this.C.remove(blockNo);
            this.C.add(blockNo, new Block(indexOfR, 1));
            this.C.add(blockNo, new Block(startPosInR, length));

        } else { //middle of block
            int endPosInS = startPosInS + length - 1;
            int firstBlockLength = index - startPosInS;
            int lastBlockLength = endPosInS - index + 1;

            this.C.remove(blockNo);

            this.C.add(blockNo, new Block(startPosInR + firstBlockLength, lastBlockLength));
            this.C.add(blockNo, new Block(indexOfR, 1));
            this.C.add(blockNo, new Block(startPosInR, firstBlockLength));
        }

    }

    public void delete(int i) {

        int[] t = this.getBlockandStartPos(i);
        int blockNum = t[0];
        int blockPosInS = t[1];
        Block b = this.C.get(blockNum);
        int l = b.getLength();
        int p = b.getPos();
        int offsetInRA = i - blockPosInS;

        //remove the old block
        this.C.remove(blockNum);

        //when block can be split in 3, i is in the middle
        //i not first in block, i not the last in block, length is 3 or more
        if (offsetInRA > 0 && offsetInRA != (l-1)  && l >= 3) {

            Block first = new Block(p,offsetInRA);
            Block last = new Block(p+offsetInRA+1,l-(offsetInRA+1));
            this.C.add(blockNum,last);
            this.C.add(blockNum,first);

        }
        //i is the first in the block and length is at least 2
        else if (offsetInRA == 0 && l >= 2) {

            Block rest = new Block(p+1,l-1);
            this.C.add(blockNum,rest);

        }
        //i is the last index and length is at least 2
        else if (offsetInRA == (l-1) && l >= 2) {

            Block preBlock = new Block(p,l-1);
            this.C.add(blockNum,preBlock);
        }
        //replace with a single char
        else if (l == 1) {
            return;
        } else {
            throw new IllegalArgumentException("Case not covered");
        }

    }

}
