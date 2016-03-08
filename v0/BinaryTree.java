import javax.xml.bind.annotation.XmlElementDecl;
import java.util.ArrayList;

/**
 * Binary tree to solve Dynamic partial sums problem
 * Implements:
 *  static operations: sum(i), update(i,k), seach(t)
 *  dynamic operations: insert(i,k), delete(i,k), merge(i), divide(i,t)
 *
 * n = nodes in tree
 * Height of tree: O(log(n))
 */
public class BinaryTree {

    //Fields of the binary tree
    private BinNode root;
    private int height;
    private int totalLeafs;

    //Constructor: given the compression of S; C
    public BinaryTree(ArrayList<Block> C) {
        this.root = null;
        this.height = 0;
        this.totalLeafs = C.size();
        //factor the insertion of the root out
        this.root = new BinNode(null,null,null,C.get(0).getLength(),0, 0);
        //Insert every block length into binary tree
        for(int i = 1; i < C.size(); i++) {
            Block b = C.get(i);
            addNode(b, this.root,i,0);
        }
        //For debug:
        //prettyPrintBinary(this.root,0);

    }

    /* ===== PUBLIC METHODS, IMPLEMENTS OPERTATIONS  ===== */

    /* help */
    public BinNode test(int i){
        return findIndex(this.root, i);
    }

    /* return the node withe the specified index */
    private BinNode findIndex(BinNode start, int index){
        if (index < 0 || index >= this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");

        // tree has only one node
        if(start.getIndex() == index) {
            return start;
        }
        BinNode found = findIndexHelp(start,0);
        // the node woth the specified index is found
        if (found.getIndex() == index){ return found; }
        // continue search in left subtree
        if (index < found.getIndex() ) {
            return findIndex(start.getLeft(), index);
        // continue search in right subtree
        } else {
            return findIndex(start.getRight(), index);
        }

    }

    /* find predecessor of the current node
     * turn left the first time and then right until a leaf is reached */
    private BinNode findIndexHelp(BinNode start, int steps){
        // leaf is reached
        if (start.getIndex() != -1){
            return start;
        }
        // first time - turn left
        if (steps == 0){
            return findIndexHelp(start.getLeft(), steps + 1);
        // turn right
        } else {
            return findIndexHelp(start.getRight(), steps + 1);
        }
    }

    // sum of everything to the left, when moving down a layer in the whole tree - changed by findIndexSum
    private int totalSum = 0;
    // sum of everything to the left when following a path from a node to a leaf - changed by findIndexHelpSum
    private int tempSum = 0;

    /* return sum up until and including the specified index */
    public int sum(int index){
        totalSum = 0;
        tempSum = 0;
        if (index < 0 || index > this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");
        // if last element then return value of the root
        if (index == this.totalLeafs -1 ){
            return this.root.getValue();
        }
        else {
            // Recursively follow the correct path until the index above the specified
            // keep track of the sums to the left when moving right in the tree
            return findIndexSum(this.root, index + 1);
        }
    }

    /* return sum of everything to the left of index */
    private int findIndexSum(BinNode start, int index){
        if (index < 0 || index >= this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");

        // tree has only one node - is probably never used, since the case is covered in sum()
        if(start.getIndex() == index) {
            return totalSum + tempSum;
        }

        // Find the leaf approximatly in the middle of subtree from current node
        BinNode found = findIndexHelpSum(start,0);
        // the node with the specified index is found
        if (found.getIndex() == index){ return totalSum + tempSum; }
        // continue search in left subtree
        // no update of totalSums necessary
        // tempSum reset to reflect the sum in the current search
        if (index < found.getIndex() ) {
            tempSum = 0;
            return findIndexSum(start.getLeft(), index);
            // continue search in right subtree
            // add the left child value to totalSum.
            // tempSum reset to reflect the sum in the current search
        } else {
            totalSum += start.getLeft().getValue();
            tempSum = 0;
            return findIndexSum(start.getRight(), index);
        }

    }

    /* find leaf approximately in the middle of subtree starting from current node
     * turn left the first time and then right until a leaf is reached */
    private BinNode findIndexHelpSum(BinNode start, int steps){
        // leaf is reached
        if (start.getIndex() != -1){
            return start;
        }
        // first time - turn left
        if (steps == 0){
            return findIndexHelpSum(start.getLeft(), steps + 1);
            // turn right
        } else {
            tempSum += start.getLeft().getValue();
            return findIndexHelpSum(start.getRight(), steps + 1);
        }
    }

    public void updateNew(int i,int k) {
        if (i < 0 || i > this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");
        if (k < 0) throw new IllegalArgumentException("Delta must be positive");
        //k = delta
        BinNode found = findIndex(this.root, i);
        updatePath(found, k);
    }


    public void update(int i,int k) {
        if (i < 0 || i > this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");
        if (k < 0) throw new IllegalArgumentException("Delta must be positive");
        //k = delta
        updateHelp(0,0,i,k,this.root);
    }

    public void updateHelp(int depth,int indexCut,int indexUpdate, int k, BinNode start){
        //Increment the current note with delta
        start.setValue(start.getValue()+k);
        //return when the leaf is reached
        if(start.getIndex() == indexUpdate) {
            return;
        }
        //calculate max number of leaf in the subtree
        int maxLeafs = (int) Math.pow((double) 2, ((double) (this.height-depth)));
        //get offset from the start of the subtree
        int localIndex = indexUpdate - indexCut;

        //go left if the offset is left subtree (less or equal to the middle index)
        if(localIndex <= ((int) ((double) maxLeafs/((double)2))-1)) {
            //left subtree is always full therefore no cut is needed
            updateHelp(depth+1,indexCut,indexUpdate,k,start.getLeft());
        } else {
            //find the new cut and go right.
            updateHelp(depth+1,indexCut+((int) ((double) maxLeafs/((double)2))),indexUpdate,k,start.getRight());
        }
    }

    // return index at which the sum first exceeds the value t
    public int search(int t) {
        if (t < 0 || t > this.root.getValue()) throw new IllegalArgumentException("Sum to seach for is out of bounds");
        // start search in root
        return searchHelp(t, 0, this.root);
    }

    public int searchHelp(int t, int sum, BinNode start){
        // correct index is reached
        if (start.getIndex() != -1) {
            return start.getIndex();
        // continue search in the right subtree - add the sum of the left subtree to the sum
        } else if (start.getLeft().getValue() + sum < t){
            return searchHelp(t, sum + start.getLeft().getValue(), start.getRight());
        // continue search in the left subtree
        } else {
           return searchHelp(t, sum, start.getLeft());
        }
    }
    public void insert(int i,int k) {}
    public void delete(int i, int k) {}
    public void merge(int i) {}
    public void divide(int i,int t) {}

    /* ===== PRIVATE METHODS TO BUILD THE BINARY TREE ===== */

   /* Given a block, root of the binary tree and a start depth, inserts
    * an node into the tree. Used be build the tree one block by block. */
    private void addNode(Block b, BinNode start, int index,int depth) {

        // Case 0: Current node is a leaf
        if (start.getRight() == null && start.getLeft() == null) {

            int flCopy;
            int flInsert;
            if (start.getParent() == null) {
                //Case 0.A: The leaf is the root node
                flCopy = 0;
                flInsert = 0;
                this.height++;
            } else {
                //Case 0.B: The leaf is not the root node
                flCopy = start.getFreeLayers() - 1;
                flInsert = start.getFreeLayers() - 1;
            }
            //Splitting the node to the left and inserts new to the right.
            BinNode copy = new BinNode(null, null, start, start.getValue(), start.getIndex(), flCopy);
            BinNode insert = new BinNode(null, null, start, b.getLength(), index, flInsert);
            start.setRight(insert);
            start.setLeft(copy);
            start.setIndex(-1);
            updatePath(start, b.getLength());

        }
        // Case 1: The current binary tree is full tree (characterized by no free layers)
        else if (start.getRight().getFreeLayers() == 0 && start.getLeft().getFreeLayers() == 0) {
            //Split the root downwards to the left and insert the new node to the right.
            BinNode copy = new BinNode(start, null, null, start.getValue(), -1, -1);
            this.height += 1;
            copy.setLeft(start);
            copy.setRight((new BinNode(null, null, copy, b.getLength(), index, this.height - (depth + 1))));
            start.setParent(copy);
            updatePath(copy, b.getLength());
            this.root = copy;
        }
        // Case 2: Subtree starting at current node as root is full (characterized by the same amount of
        //         free layers)
        else if (start.getRight().getFreeLayers() == start.getLeft().getFreeLayers()){
            BinNode copy = new BinNode(start, null, null, start.getValue(), -1, -1);
            start.getParent().setRight(copy);
            copy.setParent(start.getParent());
            start.setParent(copy);
            copy.setRight((new BinNode(null, null, copy, b.getLength(), index, this.height - (depth + 1))));
            // update free layers to the left
            decrementLayers(start, 1);
            updatePath(copy,b.getLength());
        }
        // Case 3: Continue searching in the right subtree. Check is not necessary.
        else if (start.getRight().getFreeLayers() > start.getLeft().getFreeLayers()) {
            addNode(b, start.getRight(), index, depth + 1);
        } else {
            throw new IllegalArgumentException("Error in the binary tree build process: Case not covered");
        }
    }

    /*
    * Updates sums along the path to the root,
    * starting from the parent of the inserted node.
    */
    private void updatePath(BinNode start, int length) {
        //if parent is null, it must be root
        start.setValue(start.getValue() + length);
        start.setFreeLayers(Math.max(start.getLeft().getFreeLayers(), start.getRight().getFreeLayers()));
        if(start.getParent() != null) {
            updatePath(start.getParent(),length);
        }
    }

    /* Decrements the freeLayers counter when a whole subtree is moved
     * downwards when splitting. */
    private void decrementLayers(BinNode start, int no) {
        start.setFreeLayers(start.getFreeLayers() - no);
        if(start.getRight() != null){
            decrementLayers(start.getRight(), no);
        }
        if(start.getLeft() != null){
            decrementLayers(start.getLeft(), no);
        }
    }

    /* Prints the binary tree recursively */
    private void prettyPrintBinary (BinNode start,int depth) {
        BinNode left = start.getLeft();
        BinNode right = start.getRight();
        String repeated = new String(new char[depth*5]).replace("\0"," ");
        System.out.println(repeated + start.getValue());
        if(left == null && right == null) { return; }
        prettyPrintBinary(left,depth+1);
        prettyPrintBinary(right,depth+1);

    }
}
