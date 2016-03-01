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

    public BinNode (BinNode left, BinNode right, BinNode parent, int value, int index, int freeLayers) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.value = value;
        this.index = index;
        this.freeLayers = freeLayers;
    }

    public void setLeft(BinNode left) {
        this.left = left;
    }

    public void setRight(BinNode right) {
        this.right = right;
    }

    public void setValue(int value) {
        this.value = value;
    }

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

    public void setIndex(int index) {
        this.index = index;
    }

    public void setParent(BinNode parent) {
        this.parent = parent;
    }

    public int getFreeLayers() { return freeLayers;}

    public void setFreeLayers(int freeLayers) {this.freeLayers = freeLayers;}
}
