/**
 * Created by andl on 29/02/2016.
 */
public class BinNode {
    private BinNode left;
    private BinNode right;
    private BinNode parent;
    private int value;
    private int index;
    private int freeLayers;
    private int nodesUnder;

    //Constructor
    public BinNode (BinNode left, BinNode right, BinNode parent, int value, int index, int freeLayers, int nodesUnder) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.value = value;
        this.index = index;
        this.freeLayers = freeLayers;
        this.nodesUnder = nodesUnder;
    }

    //SETTERS
    public void setLeft(BinNode left) {
        this.left = left;
    }

    public void setRight(BinNode right) {
        this.right = right;
    }

    public void setParent(BinNode parent) { this.parent = parent; }

    public void setValue(int value) {
        this.value = value;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setFreeLayers(int freeLayers) { this.freeLayers = freeLayers; }

    public void setNodesUnder(int nodesUnder) { this.nodesUnder = nodesUnder; }

    //GETTERS
    public BinNode getLeft() {
        return this.left;
    }

    public BinNode getRight() {
        return this.right;
    }

    public BinNode getParent() {
        return this.parent;
    }

    public int getValue() {
        return this.value;
    }

    public int getIndex() {
        return this.index;
    }

    public int getFreeLayers() { return this.freeLayers;}

    public int getNodesUnder() { return this.nodesUnder;}


    //PUBLIC METHODS
    public boolean isRightChild() {
        return this.getParent().getRight() == this;
    }

    public boolean isLeftChild() {
        return this.getParent().getLeft() == this;
    }

}
