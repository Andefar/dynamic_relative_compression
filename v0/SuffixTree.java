import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by andl on 11/02/2016.
 */
public class SuffixTree {


    Node root;

    public SuffixTree (String S) {
        //create root node.
        this.root = new Node(null,new char['$'],-1);

        char[] SA = S.toCharArray();
        for(int i = 0; i < SA.length; i++) {
            char[] s = Arrays.copyOfRange(SA, i,SA.length);
            System.out.println(new String(s));
            addSuffixNaive(i,s);
        }
        //System.out.println(prettyPrint(this.root,""));

    }


    public void addSuffixNaive(int index, char[] suffix) {

        Node parent = findRecursive(this.root,suffix,0);

        if(parent == null) { throw new IllegalArgumentException("Something disastrous happened \\[O.o]/"); }

        insert(parent,suffix,index);

    }

    public String prettyPrint (Node start,String prev) {
        if(start.getChildren().isEmpty()) {
            return prev;
        }

        prev += (new String (start.getEdge().getLabel())) + "\n";
        for(Node c : start.getChildren()) {
            prev += "   " + prettyPrint(c,prev);
        }
        return prev;
    }

    public Node findRecursive(Node start, char[] suffix, int i) {
        ArrayList<Node> children = start.getChildren();

        if(children == null) {
            System.out.println("children == null: returning current node");
            return start;
        }

        for (Node child : children) {
            if ((new String(suffix)).equals(new String(child.getEdge().getLabel()))) {
                System.out.println("suffix matches");
                if (i+1 == suffix.length) {
                    System.out.println("found child with suffix: " + (new String(suffix)));
                    return child;
                } else {
                    int newI = i+1;
                    char[] rest = Arrays.copyOfRange(suffix,newI,suffix.length);
                    System.out.println("continue search with rest: " + (new String(rest)));
                    return findRecursive(child,rest,newI);
                }

            } else {
                System.out.println("No suffix matches");
            }
        }
        return start;
    }

    public void insert(Node parent,char[] suffix,int index){
        Node newChild = new Node(parent,suffix,index);
        parent.addChild(newChild);
    }
}
