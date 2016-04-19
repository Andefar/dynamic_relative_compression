/**
 * Created by andl on 29/02/2016.
 */
public class BinNode {
    private BinNode left;
    private BinNode right;
    private BinNode parent;
    private int length;
    private int index; //not needed when build methods is fixed
    private int freeLayers; //not needed when build methods is fixed
    private int leafsUnder;
    private int startPositionInR;

    //Constructor
    public BinNode (BinNode left, BinNode right, BinNode parent, int length, int index, int freeLayers, int leafsUnder, int startPositionInR) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.length = length;
        this.index = index;
        this.freeLayers = freeLayers;
        this.leafsUnder = leafsUnder;
        this.startPositionInR = startPositionInR;
    }

    //SETTERS
    public void setLeft(BinNode left) {
        this.left = left;
    }

    public void setRight(BinNode right) {
        this.right = right;
    }

    public void setParent(BinNode parent) { this.parent = parent; }

    public void setLength(int length) {
        this.length = length;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setFreeLayers(int freeLayers) { this.freeLayers = freeLayers; }

    public void setLeafsUnder(int leafsUnder) { this.leafsUnder = leafsUnder; }

    public void setStartPositionInR(int startPositionInR) {this.startPositionInR = startPositionInR;}

    //GETTERS
    public BinNode getLeft() { return this.left; }

    public BinNode getRight() { return this.right; }

    public BinNode getParent() {return this.parent; }

    public int getLength() { return this.length; }

    public int getIndex() { return this.index; }

    public int getFreeLayers() { return this.freeLayers;}

    public int getLeafsUnder() { return this.leafsUnder;}

    public int getStartPositionInR() {return this.startPositionInR;}



    //PUBLIC BOOLEAN METHODS
    public boolean isRightChild() {
        if (this.isRoot()) {return false;}
        else {return this.getParent().getRight() == this;}
    }

    public boolean isLeftChild() {
        if (this.isRoot()) {return false;}
        else {return this.getParent().getLeft() == this;}
    }

    public boolean isLeaf() { return (this.getLeft() == null && this.getRight() == null);}

    public boolean isRoot() { return this.getParent() == null;}

    public BinNode getSibling() {
        if (this.getParent() == null) {
            return null;
        }
        if (this.isLeftChild()){
            return this.getParent().getRight();
        } else {
            return this.getParent().getLeft();
        }
    }

}
