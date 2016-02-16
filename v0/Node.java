import java.util.ArrayList;

/**
 * Created by andl on 11/02/2016.
 */
public class Node {

    ArrayList<Node> children = null;
    Node parent = null;
    Edge in = null;
    int id = -1;


    public Node(Node parent,char[] suffix,int id) {
        this.children = new ArrayList<Node>();
        this.in = new Edge(suffix);
        this.parent = parent;
        this.id = id;
    }
    public Node() {
        this.children = new ArrayList<Node>();
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public Edge getEdge() {
        return this.in;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void removeChildren() {
        this.children = new ArrayList<Node>();
    }

    public Node findChild (Character key) {
        return this.children.get(key);
    }



}
