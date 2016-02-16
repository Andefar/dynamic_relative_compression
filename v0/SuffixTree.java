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
            System.out.println("insert " + new String(s) + " into suffix tree");
            addSuffixNaive(this.root,s,i);
        }
        System.out.println("\n\n\n");
        prettyPrint(this.root,0);
    }

    public void prettyPrint (Node start,int depth) {
        ArrayList<Node> children = start.getChildren();

        if(children.size() == 0) {
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
            char[] tmp = ((new String(suffix)) + '$').toCharArray();
            Node leaf = new Node(start,tmp,i);
            start.addChild(leaf);
            System.out.println("children.size == 0. inserted: " + new String(leaf.getEdge().getLabel()));
            return;
        }
        boolean inserted = false;

        for (Node child : children) {
            System.out.println("inspecting children with label:" + new String(child.getEdge().getLabel()));
            char[] label_arr = child.getEdge().getLabel();
            int min = Math.min(label_arr.length, suffix.length);
            if(suffix[0] != label_arr[0]) {
                System.out.println("first char of suffix did not match the first char in label");
                continue;
            }

            for(int k = 1; k < min; k++) {
                if(suffix[k] != label_arr[k]) {
                    System.out.println("label and suffix stopped matching -> split");
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
                    System.out.println("Suffix exhausted: splitting");
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
                    System.out.println("found label length == 1");
                }

            }
            if(inserted) {
                break;
            } else if(label_arr.length == 1) {
                char[] temp = ((new String(suffix))+"$").toCharArray();
                char[] childSuffix = Arrays.copyOfRange(temp,1,temp.length);
                Node leaf = new Node(child,childSuffix,i);
                child.addChild(leaf);
            }

        }

        if (!inserted) {
            Node leaf = new Node(start, suffix, i);
            start.addChild(leaf);
            System.out.println("no match. insert: " + new String(leaf.getEdge().getLabel()));
        }

    }

    public void insert(Node parent,char[] suffix,int index){
        Node newChild = new Node(parent,suffix,index);
        parent.addChild(newChild);
    }
}
