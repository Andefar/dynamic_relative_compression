import java.util.ArrayList;

/**
 * Created by andl on 11/02/2016.
 */
public class Node {

    private ArrayList<Node> children = null;
    private Edge edge = null;
    private int id = -1;
    private Node leaf = null;

    public Node(char[] suffix,int id) {
        this.children = new ArrayList<>();
        this.edge = new Edge(suffix);
        this.id = id;
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }
    public Edge getEdge() {
        return this.edge;
    }
    public int getID() {return this.id;}
    public Node getLeaf () {return this.leaf;}

    public void setLeaf (Node leaf) {this.leaf = leaf;}



    public void addChild(Node child) {
        this.children.add(child);
    }
    public void removeChildren() {
        this.children = new ArrayList<>();
    }
    public void resetID() {
        this.id = -1;
    }


}
