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

    //Constructor
    public BinNode (BinNode left, BinNode right, BinNode parent, int value, int index, int freeLayers) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.value = value;
        this.index = index;
        this.freeLayers = freeLayers;
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

    //GETTERS
    public BinNode getLeft() {
        return left;
    }

    public BinNode getRight() {
        return right;
    }

    public BinNode getParent() {
        return parent;
    }

    public int getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public int getFreeLayers() { return freeLayers;}


    //PUBLIC METHODS
    public boolean isRightChild() {
        return this.getParent().getRight() == this;
    }

    public boolean isLeftChild() {
        return this.getParent().getLeft() == this;
    }

}
