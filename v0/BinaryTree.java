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


    /* return sum up until and including the specified index */
    public int sum(int index){
        // if sum up until and including last element then return thev value of the root
        if (index == this.totalLeafs -1 ){
            return this.root.getValue();
        } else {
            //Recursively follow the correct path until the index above the specified and then return the sum of everything on the left
            return sumHelp(0, 0, index + 1 , 0, this.root);
        }
    }

    /* Follow path to  */
    private int sumHelp(int depth, int indexCut, int index, int sum, BinNode start){
        // the node is found
        if (index == start.getIndex()) {
            return sum;
        } else {

            // number of leafs in the current subtree if the tree was full
            int maxLeafs = (int) Math.pow((double) 2, (double) (this.height - depth));
            // index in the current subtree
            int localIndex = index - indexCut;


            if (localIndex <= ((int) (1.0/2.0 * (double) maxLeafs)) -1) { //the left subtree will always be full -> go left
                return sumHelp(depth + 1, indexCut, index, sum, start.getLeft());
            } else { // go right and update indexCut (the number of leafs to the left) and sum (to the left)
                return sumHelp(depth + 1, indexCut + (int) (1.0/2.0 * (double) maxLeafs), index, sum + start.getLeft().getValue(), start.getRight());
            }
        }

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

    public void search(int t) {}
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
