import java.util.ArrayList;

/**
 * Created by andl on 29/02/2016.
 */
public class BinaryTree {
    /*
    Needs to implement
    - sum
    - search
    - update

    - insert
    - delete
    - merge
    - divide
    */

    BinNode root;
    int height;

    public BinaryTree(ArrayList<Block> C) {
        this.root = null;
        this.height = 0;


        this.root = new BinNode(null,null,null,C.get(0).getLength(),0);

        for(int i = 1; i < C.size(); i++) {
            Block b = C.get(i);
            addNode(b, this.root,i,0);
            prettyPrintBinary(this.root,0);
        }

    }

    public void addNode(Block b, BinNode start, int index,int depth) {
        if (depth < this.height) {

            if (start.getRight() != null) {
                System.out.println("right child exists - keep going right!");
                BinNode sisterLeft = start.getParent().getLeft().getLeft();
                if(sisterLeft == null) {
                    //split
                    BinNode insert = new BinNode(null,null,null,b.getLength(),index);
                    BinNode copy = new BinNode(start.getParent(),insert,start.getParent().getParent(),start.getParent().getValue(),-1);
                    start.getParent().getParent().setRight(copy);
                    start.getParent().setParent(copy);
                    insert.setParent(copy);
                    //updatePath();
                } else {
                    addNode(b, start.getRight(), index, depth + 1);
                }

            } else {
                System.out.println("right child do not exist - split and insert");
                //Split
                BinNode copy = new BinNode(null,null,start,start.getValue(),start.getIndex());
                start.setLeft(copy);
                start.setIndex(-1);
                start.setRight(new BinNode(null,null,start,b.getLength(),index));
                //update path to root with new sums
                updatePath(start,b.getLength());

            }

        } else {
            System.out.println("tree full - split root");
            //split root and update height
            BinNode copy = new BinNode(this.root,null,null,this.root.getValue(),-1);
            this.root = copy;

            //add right child
            //add right child value to root value.
            BinNode rightChild = new BinNode(null,null,this.root,b.getLength(),index);
            this.root.setRight(rightChild);
            this.root.setValue(this.root.getValue() + b.getLength());
            this.height += 1;

        }
    }
    /*
    Updates sums along the path to the root, starting from the parent of the inserted node
     */
    private void updatePath(BinNode start, int length) {
        System.out.println("updating path");
        //if parent is null, it must be root
        start.setValue(start.getValue() + length);
        if(start.getParent() != null) {
            updatePath(start.getParent(),length);
        }
    }

    public void prettyPrintBinary (BinNode start,int depth) {
        BinNode left = start.getLeft();
        BinNode right = start.getRight();


        String repeated = new String(new char[depth*5]).replace("\0"," ");
        System.out.println(repeated + start.getValue());


        if(left == null && right == null) {
            return;
        }
        prettyPrintBinary(right,depth+1);
        prettyPrintBinary(left,depth+1);


    }

}
