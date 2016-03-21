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
    //getter for C - bruges i test
    public ArrayList<Block> getC() {
        return this.C;
    }

    //getter for dps - bruges i test
    public BinaryTree getDPS() {
        return this.dps;
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
    }

    ;

    public void insert(int index, char c) {


        int indexOfR = this.cmp.indexOf(new char[]{c});
        if (indexOfR == -1) throw new IllegalArgumentException("character is not in the reference string");

        // insert at the end of string
        if (index == super.getSLength()) {
            this.dps.insert(this.dps.getTotalLeafs(), 1, indexOfR);

            //restoreMax((this.C.size() - 2), (this.C.size() - 1));
            return;
        }

        //get all positions and distances
        int[] BS = this.dps.searchReturnIndexSIR(index);
        int startPosInR = BS[1]; //B[1] is the starting position of block BS[0] in R
        int nodeIndex = BS[0]; //BS[0] is index of the node holding the index
        int startPosInS = (nodeIndex == 0) ? 0 : this.dps.sum(nodeIndex - 1);
        int length = BS[2]; // B[2] is the length of the node holding the index
        int offSet = index - startPosInS;
        //int offsetInR = startPosInR + (index - startPosInS);

        //insert in beginning of block -> insert new node before the found node
        // also covers case, where length of node is only 1
        if (index == startPosInS) {

            this.dps.insert(nodeIndex, 1, indexOfR);

        } else {//middle of block
            // also covers if the character is inserted at the last index of the block - since the old character here will then be z

            // divide node xyz ---- x   yz
            // y is the index at which the new node must be inserted - so by inserting a new node after x the new node get the correct index
            this.dps.divide(nodeIndex, offSet);
            this.dps.insert(nodeIndex + 1, 1, indexOfR);
        }

        //restoreMax((Math.max(blockNo - 1, 0)), (Math.min(blockNo + 4, (this.C.size() - 1))));
    }

    public void delete(int index) {

        //get all positions and distances
        int[] BS = this.dps.searchReturnIndexSIR(index);
        int startPosInR = BS[1]; //B[1] is the starting position of block BS[0] in R
        int nodeIndex = BS[0]; //BS[0] is index of the node holding the index
        int startPosInS = (nodeIndex == 0) ? 0 : this.dps.sum(nodeIndex - 1);
        int length = BS[2]; // B[2] is the length of the node holding the index
        int offSet = index - startPosInS;


        //when node can be split in 3, i is in the middle
        //i not first in node, i not the last in node, length is 3 or more
        if (offSet > 0 && offSet != (length - 1) && length >= 3) {

            // divide det node xyz -> x   y   z
            // y is the node of length 1 containing the relevant index
            this.dps.divide(nodeIndex, offSet);
            this.dps.divide(nodeIndex + 1, 1);
            this.dps.delete(nodeIndex + 1);
        }
        //i is the first in the node and length is at least 2
        else if (offSet == 0 && length >= 2) {

            // divide det node yz ->   y   z
            // y is the node of length 1 containing the relevant index
            this.dps.divide(nodeIndex, 1);
            this.dps.delete(nodeIndex);

        }
        //i is the last index and length is at least 2
        else if (offSet == (length - 1) && length >= 2) {

            // divide det node xy -> x   y
            // y is the node of length 1 containing the relevant index
            this.dps.divide(nodeIndex, offSet);
            this.dps.delete(nodeIndex + 1);

        }
        //i is in a node with length 1
        else if (length == 1) {
            this.dps.delete(nodeIndex);
            //RESTOREMAX
            return; // Do something to make sure the restoremax is called in this case to!

        } else {
            throw new IllegalArgumentException("Case not covered");
        }

        //restoreMax((Math.max(blockNum - 1, 0)), (Math.min(blockNum + 4, (this.C.size() - 1))));*/

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

    private void restoreMaxBLOCK(int startBlock, int endBlock) {
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

    private void restoreMax(int startNode, int endNode) {
        StringBuilder rest = new StringBuilder();
        int index = -1;
        int[] aBS;
        int[] bBS;
        char[] a;
        for (int i = startNode; i < endNode - 1; i++) {
            // the current node could not be merged with the earlier one - rest is now empty
            // so look at the two next nodes
            if (rest.length() == 0) {
                aBS = this.dps.find(i);
                a = Arrays.copyOfRange(this.cmp.RA, aBS[0], aBS[0] + aBS[1]);
                bBS = this.dps.find(i + 1);
                char[] b = Arrays.copyOfRange(this.cmp.RA, bBS[0], bBS[0] + bBS[1]);
                rest.append(a);
                rest.append(b);
                index = this.cmp.indexOf(rest.toString().toCharArray());

                if (index > -1) {// yes can be merged. endNode must be decremented (since there is now one less node to look at)
                    this.dps.merge(i);
                    endNode--;
                    //this.C.get(i).setLength(rest.length());
                    //this.C.get(i).setPos(index);
                    //this.C.remove(i + 1);
                    continue;
                }
            } else { // The nodes before could be merged (is now at index i-1) - test if the current node can also be merged with these
                     // again endNode must be decremented (since there is now one less node to look at)
                aBS = this.dps.find(i);
                a = Arrays.copyOfRange(this.cmp.RA, aBS[0], aBS[0] + aBS[1]);
                rest.append(a);
                index = this.cmp.indexOf(rest.toString().toCharArray());
                if (index > -1) {
                    this.dps.merge(i - 1);
                    endNode--;
                    //this.C.get(i - 1).setLength(rest.length());
                    //this.C.get(i - 1).setPos(index);
                    //this.C.remove(i);
                    continue;
                }


            }
            // if the nodes could not be merges (no continue is reached) the rest must be emptied before moving on to next node
            rest.setLength(0);

        }
    }
}

