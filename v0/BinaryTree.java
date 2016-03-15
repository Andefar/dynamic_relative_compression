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
    //TODO: Fix the way we build the tree
    public BinaryTree(ArrayList<Block> C) {
        this.root = null;
        this.height = 0;
        this.totalLeafs = C.size();
        //factor the insertion of the root out
        this.root = new BinNode(null,null,null,C.get(0).getLength(),0, 0,0, C.get(0).getPos());
        //Insert every block length into binary tree
        for(int i = 1; i < C.size(); i++) {
            Block b = C.get(i);
            addNode(this.root,b,i,0);
        }
        initLeafsUnder(this.root);
        //For debug:
        //prettyPrintBinary(this.root,0);

    }

    /** ===== FIX leafsUnder property (workaround)  =====  */
    public int initLeafsUnder (BinNode start ) {
        if(start.isLeaf()) {
            return 1;
        } else {
            int tot = initLeafsUnder(start.getLeft()) + initLeafsUnder(start.getRight());
            start.setLeafsUnder(tot);
            return tot;
        }

    }

    //TODO: fjern denne her senere. Brugt i tests
    public BinNode getRoot() {return this.root;}

    /** ===== PUBLIC METHODS, IMPLEMENTS OPERTATIONS  ===== */

    /** All operations throws illegalArgumentException if the arguments are out of bound or
     * some value is negative. These functions will call their related helper functions
     * when needed. This setup is used because of the need for recursive methods.
     */

    /** Return the sum of entries up to and incl index i */
    public int sum(int index){
        if (index == this.totalLeafs - 1) return this.root.getValue();
        if (index < 0 || index > this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");
        return sumHelp(this.root,index+1,0);
    }

    /** Updates an entry by increment by k (delta) */
    public void update(int i, int k){
        if (i < 0 || i > this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");
        if (k < 0) throw new IllegalArgumentException("Delta must be positive");
        updateHelp(this.root,i,k);
    }

    /** Updates an entry by increment by k (delta) */
    public void updateSIR(int i, int newValue){
        if (i < 0 || i > this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");
        if (newValue < 0) throw new IllegalArgumentException("Delta must be positive");
        updateSIRHelp(this.root,i,newValue);
    }

     /** Search for index where sum(i) < index <= sum(i+1)
      * Return the index */
    public int search(int t) {
        if (t < 0 || t > this.root.getValue()) throw new IllegalArgumentException("Sum to search for is out of bounds");
        // start search in root
        return searchHelp(this.root,t, 0)[0];
    }

    /** Search for index where sum(i) < index <= sum(i+1)
     * Return the index and the startPositionInR */
    public int[] searchReturnIndexSIR(int t) {
        if (t < 0 || t > this.root.getValue()) throw new IllegalArgumentException("Sum to search for is out of bounds");
        // start search in root
        return searchHelp(this.root,t, 0);
    }

     /** Insert new entry with value k at i. */
    public void insert(int i , int k, int startPosInR) {
        if (i < 0 || i > this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");
        if (k < 0) throw new IllegalArgumentException("Inserted value must be positive");
        // Find the leaf which will be split. Update leafsUnder and Sums all the way down.
        BinNode leafToSplit = insertHelp(this.root,i,k);
        // Split it and insert new node in front
        BinNode copy = new BinNode(null,null,leafToSplit,leafToSplit.getValue()-k,leafToSplit.getIndex()+1,0,0,leafToSplit.getStartPositionInR());
        BinNode insert = new BinNode(null,null,leafToSplit,k,leafToSplit.getIndex(),0,0, startPosInR);
        leafToSplit.setStartPositionInR(-1);
        leafToSplit.setRight(copy);
        leafToSplit.setLeft(insert);
        //update leafsUnder for the leaf that has been split
        leafToSplit.setLeafsUnder(2);
    }

    public void delete(int i){
        if (i < 0 || i > this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");

        //Find the node to delete
        BinNode deleted = deleteHelp(this.root, i);
        BinNode parent = deleted.getParent();

        deleted.getSibling().setParent(parent.getParent());

        if (parent.isLeftChild()){
            parent.getParent().setLeft(deleted.getSibling());
        } else {
            parent.getParent().setRight(deleted.getSibling());
        }

        updatePath(parent.getParent(), - deleted.getValue());

        parent.setParent(null);
        parent.setRight(null);
        parent.setLeft(null);



    }

    //TODO: implement these

    public void merge(int i) {
        if (i < 0 || i >= this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");

        // FIND THE CONSECUTIVE NODE
        BinNode deleted = deleteHelp(this.root, i + 1);

        // DELETE THE FOUND NODE
        BinNode parent = deleted.getParent();

        deleted.getSibling().setParent(parent.getParent());

        if (parent.isLeftChild()){
            parent.getParent().setLeft(deleted.getSibling());
        } else {
            parent.getParent().setRight(deleted.getSibling());
        }

        updatePath(parent.getParent(), - deleted.getValue());

        parent.setParent(null);
        parent.setRight(null);
        parent.setLeft(null);

        // DELETION COMPLETED

        // FIND THE NODE TO UPDATE
        BinNode updateNode = findIndex(this.root, i);

        // UPDATE THE UPDATE NODE WITH THE DELETED VALUE
        updateNode.setValue(updateNode.getValue() + deleted.getValue());
        updatePath(updateNode.getParent(), deleted.getValue());

    }
    public void divide(int i,int t) {
        //exception if i is out of bounds
        if (i < 0 || i > this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");

        // Find the leaf which will be split. Update leafsUnder on the the way down.
        BinNode leafToSplit = divideHelp(this.root,i);

        // if t is more or equal to the value then one value will be <= 0 which is not allowed
        if (t >= leafToSplit.getValue()) throw new IllegalArgumentException("divide value t must be less than the nodes value");

        //create left and right children. update pointers, leafsUnder, totalLeafs
        BinNode left = new BinNode(null,null,leafToSplit,t,0,0,0,leafToSplit.getStartPositionInR());
        BinNode right = new BinNode(null,null,leafToSplit,leafToSplit.getValue()-t,0,0,0, leafToSplit.getStartPositionInR()+t);
        leafToSplit.setRight(right);
        leafToSplit.setLeft(left);
        leafToSplit.setLeafsUnder(2);
        leafToSplit.setStartPositionInR(-1);
        totalLeafs++;

    }


    /** ===== PRIVATE HELPER METHODS, IMPLEMENTS OPERTATIONS  ===== */

    /** Recursive method. Searching for the leaf given its index and return the sum up to and incl index.
     * Turn left: do not add sum of sister subtree
     * Turn right: add sum of sister subtree
     */
    private int sumHelp(BinNode start, int index,int sum) {
        //Return the sum if we hit the leaf where index matches or just a leaf.
        if(start.isLeaf()) {
            return sum;
        }
        //Continue in left subtree if the index we search for is less how many leaf there are below left child
        if(index < start.getLeft().getLeafsUnder() || index == 0 ) {
            //Turn left
            return sumHelp(start.getLeft(),index,sum);
        } else {
            //Turn right and subtract the offset of the index. Also increment the sum.
            return sumHelp(start.getRight(),index-start.getLeft().getLeafsUnder(),sum+start.getLeft().getValue());
        }
    }

    /** Recursive method. Searching for the leaf given its index. Add the value k to all nodes
     * on the path to the leaf to maintain property
     */
    private void updateHelp(BinNode start, int index, int k) {
        //update all sums on the path down to the leaf
        start.setValue(start.getValue()+k);

        if(start.isLeaf()) {
            return;
        }
        //Continue in left subtree if the index we search for is less how many leaf there are below
        if(index < start.getLeft().getLeafsUnder() || index == 0 ) {
            //Turn left
            updateHelp(start.getLeft(),index,k);
        } else {
            //Turn right and subtract the offset of the index
            updateHelp(start.getRight(),index-Math.max(start.getLeft().getLeafsUnder(),1),k);
        }
    }

    private void updateSIRHelp(BinNode start, int index, int newPos) {

        if(start.isLeaf()) {
            start.setStartPositionInR(newPos);
            return;
        }
        //Continue in left subtree if the index we search for is less how many leaf there are below
        if(index < start.getLeft().getLeafsUnder() || index == 0 ) {
            //Turn left
            updateHelp(start.getLeft(),index,newPos);
        } else {
            //Turn right and subtract the offset of the index
            updateHelp(start.getRight(),index-Math.max(start.getLeft().getLeafsUnder(),1),newPos);
        }
    }

    /** Recursive method. Searching for index i, where t (given) sum(i) < t <= sum(i+1)
     */
    private int[] searchHelp(BinNode start,int t, int index){
        // correct index is reached
        if (start.isLeaf()) {
            return new int[] {index, start.getStartPositionInR(),start.getValue()};
        // continue search in the left subtree
        } else if (t <= start.getLeft().getValue()){
            return searchHelp(start.getLeft(),t, index);
        // continue search in the right subtree
        // - add the number of leafs in the left subtree to the index (or 1 if the left subtree is leaf
        // - subtract the value of the left child from the value to search for in the right subtree
        } else {
           return searchHelp(start.getRight(),t - start.getLeft().getValue(), index + Math.max(start.getLeft().getLeafsUnder(),1));
        }
    }

    /** Finds the nodes that has to be split and update sums and leafsUnder on the way down to the leaf.
     * Doesn't increment the leaf itself (this is handled in insert())
     */
    private BinNode insertHelp(BinNode start, int index, int k) {
        //increment sum on the way down (incl leaf)
        start.setValue(start.getValue()+k);
        // return if leaf or index matches
        if(start.isLeaf()) {
            return start;
        }

        //increment leafsUnder (excl leaf: incremented in insert() )
        start.setLeafsUnder(start.getLeafsUnder()+1);

        //continue in the correct subtree. Increment the tmpDepth that has to be used in insert()
        if(index < start.getLeft().getLeafsUnder() || index == 0 ) {
            return insertHelp(start.getLeft(),index,k);
        } else {
            return insertHelp(start.getRight(),index - Math.max(start.getLeft().getLeafsUnder(),k),1);
        }
    }

    private BinNode deleteHelp(BinNode start, int index){

        if(start.isLeaf()) {
            return start;
        }

        //decrement leafsUnder (excl leaf: incremented in insert() )
        start.setLeafsUnder(start.getLeafsUnder()-1);

        //continue in the correct subtree. Increment the tmpDepth that has to be used in insert()
        if(index < start.getLeft().getLeafsUnder() || index == 0 ) {
            return deleteHelp(start.getLeft(),index);
        } else {
            return deleteHelp(start.getRight(),index - Math.max(start.getLeft().getLeafsUnder(),1)) ;
        }
    }

    /** divideHelp
     */
    private BinNode divideHelp(BinNode start, int index) {

        if(start.isLeaf()) {
            return start;
        }
        //increment leafsUnder (excl leaf: incremented in divide(); )
        start.setLeafsUnder(start.getLeafsUnder()+1);

        //continue in the correct subtree. Increment the tmpDepth that has to be used in insert()
        if(index < start.getLeft().getLeafsUnder() || index == 0 ) {
            return divideHelp(start.getLeft(),index);
        } else {
            return divideHelp(start.getRight(),index-start.getLeft().getLeafsUnder());
        }
    }



    /** ===== PRIVATE METHODS TO BUILD/SCAN THE BINARY TREE ===== */

    /** return the node withe the specified index */
    private BinNode findIndex(BinNode start, int index){
        if (index < 0 || index >= this.totalLeafs) throw new IllegalArgumentException("Index is out of bounds");
        // tree has only one node or a leaf has been found
        if(start.isLeaf()) {
            return start;
        }
        if(index < start.getLeft().getLeafsUnder() || index == 0 ) {
            return findIndex(start.getLeft(),index);
        } else {
            return findIndex(start.getRight(),index-Math.max(start.getLeft().getLeafsUnder(),1));
        }
    }

   /** Given a block, root of the binary tree and a start depth, inserts
    * an node into the tree. Used be build the tree one block by block. */
    private void addNode( BinNode start, Block b, int index, int depth) {

        // Case 0: Current node is a leaf
        if (start.isLeaf()) {

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
            BinNode copy = new BinNode(null, null, start, start.getValue(), start.getIndex(), flCopy,0, start.getStartPositionInR());
            BinNode insert = new BinNode(null, null, start, b.getLength(), index, flInsert,0, b.getPos());
            start.setRight(insert);
            start.setLeft(copy);
            start.setIndex(-1);
            start.setStartPositionInR(-1);
            updatePath(start, b.getLength());

        }
        // Case 1: The current binary tree is full tree (characterized by no free layers)
        else if (start.getRight().getFreeLayers() == 0 && start.getLeft().getFreeLayers() == 0) {
            //Split the root downwards to the left and insert the new node to the right.
            BinNode copy = new BinNode(start, null, null, start.getValue(), -1, -1,0, -1);
            this.height += 1;
            //copy.setLeft(start);
            copy.setRight((new BinNode(null, null, copy, b.getLength(), index, this.height - (depth + 1),0, b.getPos())));
            start.setParent(copy);
            updatePath(copy, b.getLength());
            this.root = copy;
        }
        // Case 2: Subtree starting at current node as root is full (characterized by the same amount of
        //         free layers)
        else if (start.getRight().getFreeLayers() == start.getLeft().getFreeLayers()){
            BinNode copy = new BinNode(start, null, null, start.getValue(), -1, -1,0,-1);
            start.getParent().setRight(copy);
            copy.setParent(start.getParent());
            start.setParent(copy);
            copy.setRight((new BinNode(null, null, copy, b.getLength(), index, this.height - (depth + 1),0,b.getPos())));
            // update free layers to the left
            decrementLayers(start, 1);
            updatePath(copy,b.getLength());
        }
        // Case 3: Continue searching in the right subtree. Check is not necessary.
        else if (start.getRight().getFreeLayers() > start.getLeft().getFreeLayers()) {
            addNode( start.getRight(),b, index, depth + 1);
        } else {
            throw new IllegalArgumentException("Error in the binary tree build process: Case not covered");
        }
    }

    /**
    * Updates sums along the path to the root,
    * starting from the parent of the inserted node.
    */
    private void updatePath(BinNode start, int length) {
        //Increment sum and freelayers
        start.setValue(start.getValue() + length);
        start.setFreeLayers(Math.max(start.getLeft().getFreeLayers(), start.getRight().getFreeLayers()));
        //stop when the methods hits the root.
        if(!(start.isRoot())) {
            updatePath(start.getParent(),length);
        }
    }

    /** Decrements the freeLayers counter when a whole subtree is moved
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

    /** Prints the binary tree recursively */
    //TODO: set til private senere
    public void prettyPrintBinary (BinNode start,int depth) {
        BinNode left = start.getLeft();
        BinNode right = start.getRight();
        String repeated = new String(new char[depth*5]).replace("\0"," ");
        //System.out.println(repeated + start.getValue());
        System.out.println(repeated + start.getValue() + " SiR:" + start.getStartPositionInR());
        if(left == null && right == null) { return; }

        System.out.print(" right");
        prettyPrintBinary(right,depth+1);
        System.out.print(" left ");
        prettyPrintBinary(left,depth+1);


    }
}
