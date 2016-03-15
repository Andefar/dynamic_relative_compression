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
            //System.out.println("\nROUND " + i + " INSERTING: " + (new String(s)) + "\nTREE:\n");
            //prettyPrint(this.root,0);
            //System.out.println("\n\n");
        }
    }

    public void prettyPrint (Node start,int depth) {
        ArrayList<Node> children = start.getChildren();

        if(children.size() == 0) {
            return;
        }

        for( Node child : children) {
            String repeated = new String(new char[depth*5]).replace("\0"," ");
            System.out.println(repeated + new String(child.getEdge().getLabel()) + child.getID());
            prettyPrint(child,depth+1);
        }

    }

    public void addSuffixNaive(Node start, char[] suffix, int i) {
        ArrayList<Node> children = start.getChildren();

        // Case 1A
        if(children.size() == 0) {
            char[] tmp = ((new String(suffix)) + '$').toCharArray();
            Node leaf = new Node(start,tmp,i);
            start.addChild(leaf);
            return;
        }

        boolean inserted = false;

        // Case 1B
        for (Node child : children) {

            char[] label_arr = child.getEdge().getLabel();

            if(suffix[0] != label_arr[0]) {
                continue;
            }

            int min = Math.min(label_arr.length, suffix.length);

            for(int k = 1; k < min; k++) {

                // Case 1B.1
                if(suffix[k] != label_arr[k]) {
                    //change existing label before split
                    child.getEdge().setLabel(Arrays.copyOfRange(suffix,0,k));

                    //create node to represent existing suffix
                    Node l1 = new Node(child,Arrays.copyOfRange(label_arr,k,label_arr.length),child.id);

                    // child is now intermediate node -> must have ID -1
                    child.resetID();

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
                    // case 1B.2
                } else if(k == suffix.length-1) {
                    //split
                    child.getEdge().setLabel(Arrays.copyOfRange(suffix,0,k+1));

                    Node l1 = new Node(child,Arrays.copyOfRange(label_arr,k+1,label_arr.length),child.id);

                    // child is now intermediate node -> must have ID -1
                    child.resetID();

                    //create node to represent new suffix. Just '$' in this case.

                    for (Node subChild : child.getChildren() ) {
                        l1.addChild(subChild);
                    }
                    child.removeChildren();

                    char[] l2suffix = {'$'};

                    Node l2 = new Node(child,l2suffix,i);
                    //add new nodes as children to parent (named child here)
                    child.addChild(l1);
                    child.addChild(l2);
                    inserted = true;
                    break;
                }

            }
            if(inserted) {
                break;
                // Case 2C
            } else if(label_arr.length == 1) {
                // Case 2C.1
                if(suffix.length > 1) {
                    addSuffixNaive(child, Arrays.copyOfRange(suffix, 1, suffix.length), i);
                    inserted = true;
                    // Case 2C.2
                } else {
                    char[] temp = ((new String(suffix))+"$").toCharArray();
                    char[] childSuffix = Arrays.copyOfRange(temp,1,temp.length);
                    Node leaf = new Node(child,childSuffix,i);
                    child.addChild(leaf);
                    inserted = true;
                }
            }

        }
        // Case 3A
        if (!inserted) {
            Node leaf = new Node(start, (new String(suffix)+"$").toCharArray(), i);
            start.addChild(leaf);
        }

    }

    // Search for string in the suffixtree
    // return the index in R at which the string starts
    // if string not found return -1

    public int search(char[] C){

        if (C.length == 0){
            throw new IllegalArgumentException("Searching for empty string");
        }
        return searchHelp(this.root, C);
    }

    private int searchHelp(Node startNode, char[] C){
        char[] childLabel;

        for (Node child: startNode.getChildren()) {

            childLabel = child.getEdge().getLabel();
            if (C[0] != childLabel[0]) {
                continue;
            }

            for (int i = 1; i < C.length; i++) {
                if (i == childLabel.length) {
                    return searchHelp(child, Arrays.copyOfRange(C, i, C.length));
                } else if (C[i] != childLabel[i]) {
                    return -1;
                }
            }

            if (child.getID() != -1) {
                return child.getID();
            } else {
                return searchID(child);
            }

        }
    return -1;

    }

    //return the leftmost ID
    private int searchID(Node node){

        if (node.getID() != -1){
            return node.getID();
        } else {
            return searchID(node.getChildren().get(0));
        }
    }
    /*
    public void insert(Node parent,char[] suffix,int index){
        Node newChild = new Node(parent,suffix,index);
        parent.addChild(newChild);
    }*/
}
