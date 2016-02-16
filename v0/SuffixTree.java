import java.lang.reflect.Array;
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
            addSuffixNaive(this.root,s,i);
            System.out.println("\nROUND " + i + " INSERTING: " + (new String(s)) + "\nTREE:\n");
            prettyPrint(this.root,0);
            System.out.println("\n\n");
        }
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
            return;
        }
        boolean inserted = false;

        for (Node child : children) {
            char[] label_arr = child.getEdge().getLabel();
            int min = Math.min(label_arr.length, suffix.length);
            if(suffix[0] != label_arr[0]) {
                continue;
            }

            for(int k = 1; k < min; k++) {
                if(suffix[k] != label_arr[k]) {
                    //change existing label before split
                    child.getEdge().setLabel(Arrays.copyOfRange(suffix,0,k));
                    //create node to represent existing suffix
                    Node l1 = new Node(child,Arrays.copyOfRange(label_arr,k,label_arr.length),child.id);

                    for (Node subChild : child.getChildren() ) {
                        l1.addChild(subChild);
                    }
                    child.removeChildren();

                    //create node to represent new suffix. Increase length of suffix array by 1 to contain '$'
                    char[] newSuffix = ((new String(suffix))+"$").toCharArray();

                    newSuffix = Arrays.copyOfRange(newSuffix,k,newSuffix.length);

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
                }

            }
            if(inserted) {
                break;
            } else if(label_arr.length == 1) {
                if(suffix.length > 1) {
                    addSuffixNaive(child, Arrays.copyOfRange(suffix, 1, suffix.length), i);
                    inserted = true;
                } else {
                    char[] temp = ((new String(suffix))+"$").toCharArray();
                    char[] childSuffix = Arrays.copyOfRange(temp,1,temp.length);
                    Node leaf = new Node(child,childSuffix,i);
                    child.addChild(leaf);
                    inserted = true;
                }
            }

        }

        if (!inserted) {
            Node leaf = new Node(start, (new String(suffix)+"$").toCharArray(), i);
            start.addChild(leaf);
        }

    }

    public void insert(Node parent,char[] suffix,int index){
        Node newChild = new Node(parent,suffix,index);
        parent.addChild(newChild);
    }
}
