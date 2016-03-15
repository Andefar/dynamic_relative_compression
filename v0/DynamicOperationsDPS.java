import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by andl on 14/03/2016.
 */
public class DynamicOperationsDPS extends DynamicOperations {

    BinaryTree dps;

    //constructor
    public DynamicOperationsDPS(ArrayList<Block> C, Compressor cmp) {
        super(C, cmp);
        this.dps = new BinaryTree(C);
    }

    //TODO: Hvor bruger vi getC??
    //getter for C
    public ArrayList<Block> getC() {
        return this.C;
    }


    // Return character S[i]
    // Index out of bound exception
    public char access(int index) throws IndexOutOfBoundsException {
        int[] BS = this.dps.searchReturnIndexSIR(index);
        int startPosInR = BS[1]; //BS[0] is the block holding the index - p is the start position of that block in R
        int startPosInS = this.dps.sum(BS[0]); //B[1] is the starting position of block BS[0] in S (the original string)
        return this.cmp.RA[startPosInR + (index - startPosInS)];
    }

    public void replace(int index, char sub) {
        //get all positions and distances
        int[] BS = this.dps.searchReturnIndexSIR(index);
        int startPosInR = BS[1]; //BS[0] is the block holding the index - p is the start position of that block in R
        int startPosInS = this.dps.sum(BS[0]); //B[1] is the starting position of block BS[0] in S (the original string)
        int length = BS[2];
        int offsetInR = startPosInR + (index - startPosInS);


        char charToReplace = this.cmp.RA[startPosInR + (index - startPosInS)];
        int indexOfReplacedChar = this.cmp.indexOf(new char[]{charToReplace});

        //replace with same char
        if (charToReplace == sub) return;
        //if char doesnt exist in R it is illegal
        if(indexOfReplacedChar == -1) throw new IllegalArgumentException("character is not in the reference string");

        //when block can be split in 3, i is in the middle
        //i not first in block, i not the last in block, length is 3 or more
        if (offsetInR > 0 && offsetInR != (length - 1) && length >= 3) {

            this.dps.divide(BS[0],offsetInR);
            this.dps.divide(BS[0]+1,1);
            this.dps.updateSIR();

            /*
            divide
            divide
            update
             */

            Block first = new Block(p, offsetInRA);
            Block replace = new Block(subPosInRA, 1);
            Block last = new Block(p + offsetInRA + 1, l - (offsetInRA + 1));
            this.C.add(blockNum, last);
            this.C.add(blockNum, replace);
            this.C.add(blockNum, first);

        }
        //i is the first in the block and length is at least 2
        else if (offsetInR == 0 && length >= 2) {

            /*
            divide
            update
             */

            Block replace = new Block(offsetInR, 1);
            Block rest = new Block(p + 1, length - 1);
            this.C.add(blockNum, rest);
            this.C.add(blockNum, replace);
        }
        //i is the last index and length is at least 2
        else if (offsetInR == (length - 1) && length >= 2) {

            /*
            divide
            update
             */

            Block replace = new Block(subPosInRA, 1);
            Block preBlock = new Block(p, l - 1);
            this.C.add(blockNum, replace);
            this.C.add(blockNum, preBlock);
        }
        //replace with a single char
        else if (length == 1) {

            /*
            update
             */

            Block replace = new Block(subPosInRA, 1);
            this.C.add(blockNum, replace);

        } else {
            throw new IllegalArgumentException("Case not covered");
        }

        restoreMax((Math.max(blockNum - 1, 0)), (Math.min(blockNum + 4, (this.C.size() - 1))));

    }


    public void insert(int index, char c) {
        int indexOfR = -1;
        for (int i = 0; i < this.cmp.RA.length; i++) {
            if (this.cmp.RA[i] == c) {
                indexOfR = i;
                break;
            }
        }

        // insert at the end of string
        if (index == super.getSLength()) {
            this.C.add(new Block(indexOfR, 1));
            restoreMax((this.C.size() - 2), (this.C.size() - 1));
            return;
        }

        int[] BS = this.getBlockandStartPos(index);
        int blockNo = BS[0];
        int startPosInR = this.C.get(blockNo).getPos();
        int length = this.C.get(blockNo).getLength();
        int startPosInS = BS[1];

        if (index == startPosInS) { //insert in beginning of block

            this.C.remove(blockNo);
            this.C.add(blockNo, new Block(startPosInR, length));
            this.C.add(blockNo, new Block(indexOfR, 1));

        } else if (index == startPosInS + length - 1) { //insert at the end of block

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

        restoreMax((Math.max(blockNo - 1, 0)), (Math.min(blockNo + 4, (this.C.size() - 1))));
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
        if (offsetInRA > 0 && offsetInRA != (l - 1) && l >= 3) {

            Block first = new Block(p, offsetInRA);
            Block last = new Block(p + offsetInRA + 1, l - (offsetInRA + 1));
            this.C.add(blockNum, last);
            this.C.add(blockNum, first);

        }
        //i is the first in the block and length is at least 2
        else if (offsetInRA == 0 && l >= 2) {

            Block rest = new Block(p + 1, l - 1);
            this.C.add(blockNum, rest);

        }
        //i is the last index and length is at least 2
        else if (offsetInRA == (l - 1) && l >= 2) {

            Block preBlock = new Block(p, l - 1);
            this.C.add(blockNum, preBlock);
        }
        //replace with a single char
        else if (l == 1) {
            restoreMax((Math.max(blockNum - 1, 0)), (Math.min(blockNum + 4, (this.C.size() - 1))));
            return;
        } else {
            throw new IllegalArgumentException("Case not covered");
        }

        restoreMax((Math.max(blockNum - 1, 0)), (Math.min(blockNum + 4, (this.C.size() - 1))));

    }

    private void mergeBlocks(int startBlock, int endBlock) {
        ArrayList<Block> blocksC = new ArrayList<>();

        for (int i = startBlock; i <= endBlock; i++) {
            blocksC.add(this.C.get(startBlock));
            this.C.remove(startBlock); //delete old block from compressed rep
        }

        String partOfS = cmp.decode(blocksC);
        blocksC.clear();
        blocksC = cmp.encode(partOfS);

        for (int i = blocksC.size() - 1; i >= 0; i--) {
            this.C.add(startBlock, blocksC.get(i));
        }
    }

    private void restoreMax(int startBlock, int endBlock) {
        StringBuilder rest = new StringBuilder();
        int index = -1;
        char[] a;
        for (int i = startBlock; i < endBlock - 1; i++) {
            if (rest.length() == 0) {
                a = Arrays.copyOfRange(this.cmp.RA, this.C.get(i).getPos(), this.C.get(i).getPos() + this.C.get(i).getLength());
                char[] b = Arrays.copyOfRange(this.cmp.RA, this.C.get(i + 1).getPos(), this.C.get(i + 1).getPos() + this.C.get(i + 1).getLength());
                rest.append(a);
                rest.append(b);
                index = this.cmp.indexOf(rest.toString().toCharArray());
                if (index > -1) {
                    this.C.get(i).setLength(rest.length());
                    this.C.get(i).setPos(index);
                    this.C.remove(i + 1);
                    continue;
                } else {
                    a = Arrays.copyOfRange(this.cmp.RA, this.C.get(i).getPos(), this.C.get(i).getPos() + this.C.get(i).getLength());
                    rest.append(a);
                    index = this.cmp.indexOf(rest.toString().toCharArray());
                    if (index > -1) {
                        this.C.get(i - 1).setLength(rest.length());
                        this.C.get(i - 1).setPos(index);
                        this.C.remove(i);
                        continue;
                    }


                }
                rest.setLength(0);

            }
        }
    }
}
