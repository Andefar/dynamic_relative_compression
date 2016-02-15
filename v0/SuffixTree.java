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
        this.root = new Node(null,(new char[0]),-1);

        char[] SA = S.toCharArray();
        for(int i = 0; i < SA.length; i++) {
            char[] s = Arrays.copyOfRange(SA, i,SA.length);
            //System.out.println(new String(s));
            addSuffixNaive(this.root,s,i);
        }
        prettyPrint(this.root,0);
    }

    public void prettyPrint (Node start,int depth) {
        ArrayList<Node> children = start.getChildren();

        if(children.size() == 0) {
            System.out.println("No children here");
            String repeated = new String(new char[depth*5]).replace("\0"," ");
            System.out.println(repeated + new String(start.getEdge().getLabel()));
            return;
        }

        for( Node child : children) {

            String repeated = new String(new char[depth*5]).replace("\0"," ");
            System.out.println(repeated + new String(child.getEdge().getLabel()));
            prettyPrint(child,depth+1);
        }

    }

    public void addSuffixNaive(Node start, char[] suffix, int i) {
        ArrayList<Node> children = start.getChildren();

        if(children.size() == 0) {
            //System.out.println("children == null: returning current node");
            Node leaf = new Node(start,suffix,i);
            start.addChild(leaf);
            return;
        }

        for (Node child : children) {

            boolean inserted = false;
            char[] label_arr = child.getEdge().getLabel();
            int max = Math.max(label_arr.length, suffix.length);
            if(suffix[0] != label_arr[0]) { continue; }

            for(int k = 1; k < max; k++) {
                if(suffix[k] != label_arr[k]) {
                    //change existing label before split
                    child.getEdge().setLabel(Arrays.copyOfRange(suffix,0,k));
                    //create node to represent existing suffix
                    Node l1 = new Node(child,Arrays.copyOfRange(label_arr,k-1,label_arr.length),child.id);
                    //create node to represent new suffix. Increase length of suffix array by 1 to contain '$'
                    char[] newSuffix = Arrays.copyOf(suffix,suffix.length-k+1);
                    newSuffix[newSuffix.length-1] = '$';
                    Node l2 = new Node(child,newSuffix,i);
                    //add new nodes as children to parent (named child here)
                    child.addChild(l1);
                    child.addChild(l2);
                    inserted = true;
                    break;
                } else if(k == suffix.length-1) {
                    //split
                    child.getEdge().setLabel(Arrays.copyOfRange(suffix,0,k));
                    Node l1 = new Node(child,Arrays.copyOfRange(label_arr,k-1,label_arr.length),child.id);
                    //create node to represent new suffix. Just '$' in this case.
                    Node l2 = new Node(child,new char['$'],i);
                    //add new nodes as children to parent (named child here)
                    child.addChild(l1);
                    child.addChild(l2);
                    inserted = true;
                    break;
                } else {
                    throw new IllegalArgumentException("Something disastrous happened \\[O.o]/ when matching label");
                }
            }
            if(inserted) { break; }
        }

    }

    public void insert(Node parent,char[] suffix,int index){
        Node newChild = new Node(parent,suffix,index);
        parent.addChild(newChild);
    }
}
