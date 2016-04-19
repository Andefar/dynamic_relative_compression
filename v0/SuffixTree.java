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
        this.root = new Node((new char[0]),-1);

        char[] SA = S.toCharArray();
        for(int i = 0; i < SA.length; i++) {
            char[] temp = new char[SA.length-i];
            System.arraycopy(SA,i,temp,0,SA.length-i);
            addSuffixNaive(this.root,temp,i);
        }
        fixPointersToLeafs(this.root);
        //prettyPrint(this.root,0);
    }

    private void fixPointersToLeafs(Node start) {
        start.setLeaf(findLeftmostNode(start));
        start.getChildren().forEach(this::fixPointersToLeafs);
    }
    private Node findLeftmostNode(Node start) {
        //if leaf -> return
        if(start.getChildren().size() == 0) {
            return start;
        }
        return findLeftmostNode(start.getChildren().get(0));
    }

    public void prettyPrint (Node start,int depth) {
        ArrayList<Node> children = start.getChildren();

        if(children.size() == 0) {
            return;
        }

        for( Node child : children) {
            String repeated = new String(new char[depth*5]).replace("\0"," ");
            System.out.println(repeated + new String(child.getEdge().getLabel()) + " id: " + child.getID() + " => " +  child.getLeaf().getID());
            prettyPrint(child,depth+1);
        }

    }

    public void addSuffixNaive(Node start, char[] suffix, int i) {

        ArrayList<Node> children = start.getChildren();

        // Case 1A
        if(children.size() == 0) {
            char[] temp = new char[suffix.length+1];
            System.arraycopy(suffix,0,temp,0,suffix.length);
            temp[temp.length-1] = '$';
            Node leaf = new Node(temp,i);
            start.addChild(leaf);
            return;
        }

        boolean inserted = false;

        // Case 1B
        for (Node child : children) {

            char[] label_arr = child.getEdge().getLabel();
            // can only match one child
            if(suffix[0] != label_arr[0]) {
                continue;
            }

            int min = Math.min(label_arr.length, suffix.length);

            if(label_arr.length == 1 && suffix.length == 1) {

                Node l1 = new Node("$".toCharArray(),i);
                child.addChild(l1);
                inserted = true;

            } else if(suffix.length == 1 && label_arr.length > 1) {
                //split

                child.getEdge().setLabel(new char[]{suffix[0]});

                char[] temp = new char[label_arr.length-(1)];
                System.arraycopy(label_arr,1,temp,0,label_arr.length-(1));
                Node l1 = new Node(temp,child.getID());


                // child is now intermediate node -> must have ID -1
                child.resetID();

                //create node to represent new suffix. Just '$' in this case.

                child.getChildren().forEach(l1::addChild);
                child.removeChildren();

                char[] l2suffix = {'$'};

                Node l2 = new Node(l2suffix,i);
                //add new nodes as children to parent (named child here)
                child.addChild(l1);
                child.addChild(l2);
                inserted = true;
                break;

            }

            for(int k = 1; k < min; k++) {

                // Case 1B.1
                if(suffix[k] != label_arr[k]) {
                    //change existing label before split
                    char[] temp = new char[k];
                    System.arraycopy(suffix,0,temp,0,k);
                    child.getEdge().setLabel(temp);
                    //create node to represent existing suffix

                    temp = new char[label_arr.length-k];
                    System.arraycopy(label_arr,k,temp,0,label_arr.length-k);

                    Node l1 = new Node(temp,child.getID());

                    // child is now intermediate node -> must have ID -1
                    child.resetID();

                    child.getChildren().forEach(l1::addChild);
                    child.removeChildren();

                    //create node to represent new suffix. Increase length of suffix array by 1 to contain '$'
                    temp = new char[suffix.length-k+1];
                    System.arraycopy(suffix,k,temp,0,suffix.length-k);
                    temp[temp.length-1] = '$';
                    Node l2 = new Node(temp,i);

                    //add new nodes as children to parent (named child here)
                    child.addChild(l1);
                    child.addChild(l2);
                    inserted = true;
                    break;
                    // case 1B.2
                } else if(k == suffix.length-1) {
                    //split
                    char[] temp = new char[k+1];
                    System.arraycopy(suffix,0,temp,0,k+1);
                    child.getEdge().setLabel(temp);

                    temp = new char[label_arr.length-(k+1)];
                    System.arraycopy(label_arr,k+1,temp,0,label_arr.length-(k+1));
                    Node l1 = new Node(temp,child.getID());


                    // child is now intermediate node -> must have ID -1
                    child.resetID();

                    //create node to represent new suffix. Just '$' in this case.

                    child.getChildren().forEach(l1::addChild);
                    child.removeChildren();

                    char[] l2suffix = {'$'};

                    Node l2 = new Node(l2suffix,i);
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
                    char[] temp = new char[suffix.length-1];
                    System.arraycopy(suffix,1,temp,0,suffix.length-1);
                    addSuffixNaive(child,temp,i);
                    inserted = true;
                    // Case 2C.2
                } else {

                    char[] temp = new char[suffix.length+1-1];
                    System.arraycopy(suffix,1,temp,0,suffix.length-1+1);
                    Node leaf = new Node(temp,i);
                    child.addChild(leaf);
                    inserted = true;

                }
            }

        }
        // Case 3A
        if (!inserted) {
            char[] temp = new char[suffix.length+1];
            System.arraycopy(suffix,0,temp,0,suffix.length);
            temp[temp.length-1] = '$';
            Node leaf = new Node(temp, i);
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
  //      char[] childLabel;

        for (Node child: startNode.getChildren()) {

            char[] childLabel = child.getEdge().getLabel();

            //can only match with one edge -> check children is constant (because alphabet in constant)
            if (C[0] != childLabel[0]) {
                continue;
            }

            for (int i = 1; i < C.length; i++) {
                if (i == childLabel.length) {
                    char[] temp = new char[C.length-i];
                    System.arraycopy(C,i,temp,0,C.length-i);
                    return searchHelp(child, temp);
                } else if (C[i] != childLabel[i]) {
                    return -1;
                }
            }

            if (child.getID() != -1) {
                return child.getID();
            } else {
                return child.getLeaf().getID();
            }
        }
    return -1;
    }
}
