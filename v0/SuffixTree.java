import java.util.ArrayList;

/**
 * Created by andl on 11/02/2016.
 */
public class SuffixTree {


    Node root;
    char[] RA;
    Node state;
    int labalOffset;

    public SuffixTree (char[] R) {
        //create root node.
        this.root = new Node(-1, 0, 0);
        this.RA = R;
        this.state = this.root;
        this.labalOffset = 0;

        for(int i = 0; i < R.length-1; i++) { //subtracting 1 from the length each time to remove the $ end character
            addSuffixNaive(this.root, R.length-1-i,i, i);
        }
        fixPointersToLeafs(this.root);
    }

    private void fixPointersToLeafs(Node start) {
        start.setLeaf(findLeftmostNode(start));
        start.getChildren().forEach(this::fixPointersToLeafs);
    }
    private Node findLeftmostNode(Node start) {
        //if leaf -> return
        if(start.isLeaf()) {
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
            char[] label = new char[child.getEdge().getLength()];
            System.arraycopy(label, 0, this.RA, child.getEdge().getStartR(), child.getEdge().getLength());

            System.out.println(repeated + new String(label) + " id: " + child.getID() + " => " +  child.getLeaf().getID());
            prettyPrint(child,depth+1);
        }

    }

    // insert a suffix starting from start
    // id = the index of the original suffix in R inserted from the root
    // startR is the start position in R of the suffix to be inserted
    public void addSuffixNaive(Node start, int length, int id, int startR) {

        ArrayList<Node> children = start.getChildren();

        // Case 1A
        if(children.size() == 0) {
            Node leaf = new Node(id, startR, length);
            start.addChild(leaf);
            return;
        }

        boolean inserted = false;

        // Case 1B
        for (Node child : children) {

            //int child_label_length = child.getEdge().getLength();
            //char[] label_arr = new char[child.getEdge().getLength()];
            //System.arraycopy(this.RA,child.getEdge().getStartR(), label_arr,0, label_arr.length);

            // can only match one child and the label of the child should not be empty
            if(child.getEdge().getLength() == 0 || this.RA[startR] != RA[child.getEdge().getStartR()]) {
                continue;
            }

            int min = Math.min(child.getEdge().getLength(), length);

            if(child.getEdge().getLength() == 1 && length == 1) {

                Node l1 = new Node(id, 0,0);
                child.addChild(l1);
                inserted = true;

            } else if(length == 1 && child.getEdge().getLength() > 1) {
                //split

                // Containing the matching caracter
                child.getEdge().setLength(1);

                // containing the rest of the original label
                Node l1 = new Node(child.getID(), child.getEdge().getStartR() + 1, child.getEdge().getLength()-1);

                // child is now intermediate node -> must have ID -1
                child.resetID();

                // move child's children to new node
                if (!child.isLeaf()) {
                    child.getChildren().forEach(l1::addChild);
                    child.removeChildren();
                }

                //create node to represent new suffix. With label (0,0) in this case
                Node l2 = new Node(id, 0, 0);

                //add new nodes as children to parent (named child here)
                child.addChild(l1);
                child.addChild(l2);
                inserted = true;
                break;

            }

            for(int k = 1; k < min; k++) {

                // Case 1B.1
                if(this.RA[startR + k] != RA[child.getEdge().getStartR()+k]) {
                    //change length of existing label before split to the part matching - can just keep the old start index in R
                    child.getEdge().setLength(k);

                    //create node to represent the rest of the old label
                    Node l1 = new Node(child.getID(), child.getEdge().getStartR()+k , child.getEdge().getLength()-k);

                    // child is now intermediate node -> must have ID -1
                    child.resetID();

                    // move child's children to new node
                    if (!child.isLeaf())  {
                        child.getChildren().forEach(l1::addChild);
                        child.removeChildren();
                    }

                    //create node to represent new suffix.
                    Node l2 = new Node(id, startR + k, length-k);

                    //add new nodes as children to parent (named child here)
                    child.addChild(l1);
                    child.addChild(l2);
                    inserted = true;
                    break;

                    // case 1B.2
                } else if(k == length-1) {
                    //split

                    //change length of existing label before split to the part matching - can just keep the old start index in R
                    child.getEdge().setLength(k+1); // the start in R can just be kept

                    //create node to represent the rest of the old label
                    Node l1 = new Node(child.getID(), child.getEdge().getStartR() + k+1, child.getEdge().getLength()-(k+1) );

                    // child is now intermediate node -> must have ID -1
                    child.resetID();

                    //add new nodes as children to parent (named child here)
                    if (!child.isLeaf()) {
                        child.getChildren().forEach(l1::addChild);
                        child.removeChildren();
                    }

                    //create node to represent new suffix. With label (0,0) in this case
                    Node l2 = new Node(id, 0, 0);
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
            } else if(child.getEdge().getLength() == 1) {
                // Case 2C.1
                if(length > 1) {
                    // if all of the suffix is not inserted yet the remaining part must be inserted starting from the child node
                    addSuffixNaive(child,length-1,id, startR+1);
                    inserted = true;
                    // Case 2C.2
                } else {
                    // The label of child and the suffix is the same - the new suffix are just represented as a new node with edge (0,0)
                    Node leaf = new Node(id, 0,0);
                    child.addChild(leaf);
                    inserted = true;

                }
            }

        }
        // Case 3A
        if (!inserted) {
            // No match in any of the children - the suffix is just inserted as child of the start-node
            Node leaf = new Node(id, startR, length);
            start.addChild(leaf);
        }
    }

    public int streamSearch(boolean reset, char c) {
        if(reset) { this.state = this.root; this.labalOffset = 0;}
        char[] label = new char[state.getEdge().getLength()];
        System.arraycopy(this.RA,state.getEdge().getStartR(), label,0, label.length);

        if(this.labalOffset == 0 || this.labalOffset == label.length) {
            for (int i = 0; i < state.getChildren().size(); i++) {
                Node child = state.getChildren().get(i);
                char[] child_label = new char[child.getEdge().getLength()];
                System.arraycopy(this.RA,child.getEdge().getStartR(), child_label,0, child_label.length);
                if (child_label.length != 0 && child_label[0] == c) {
                    this.state = child;
                    labalOffset = 1;
                    return child.getLeaf().getID();
                }
            }
            return -1;
        }
        if(label[this.labalOffset] == c) {
            this.labalOffset++;
            return state.getLeaf().getID();
        }
        return -1;
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

            //char[] childLabel = new char[child.getEdge().getLength()];
            //System.arraycopy(this.RA, child.getEdge().getStartR(), childLabel, 0,  child.getEdge().getLength() );

            // label of the current child is empty move to next child
            //can only match with one edge -> check children is constant (because alphabet in constant)
            if (child.getEdge().getLength() == 0 || C[0] != this.RA[child.getEdge().getStartR()]) {
                continue;
            }

            for (int i = 1; i < C.length; i++) {
                if (i == child.getEdge().getLength()) {
                    char[] temp = new char[C.length-i];
                    System.arraycopy(C,i,temp,0,C.length-i);
                    return searchHelp(child, temp);
                } else if (C[i] != this.RA[child.getEdge().getStartR()+i]) {
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
