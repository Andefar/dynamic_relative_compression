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

    //Constructor: given the compression of S; C
    public BinaryTree(ArrayList<Block> C) {
        this.root = null;
        this.height = 0;
        //factor the insertion of the root out
        this.root = new BinNode(null,null,null,C.get(0).getLength(),0, 0);
        //Insert every block length into binary tree
        for(int i = 1; i < C.size(); i++) {
            Block b = C.get(i);
            addNode(b, this.root,i,0);
            //For debug:
            //prettyPrintBinary(this.root,0);
        }

    }

    /* ===== PUBLIC METHODS, IMPLEMENTS OPERTATIONS  ===== */

    public int sum(int i) {return -1;}
    public void update(int i,int k) {}
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
        System.out.println("updating path");
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
        System.out.println("decrementing layers");
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
