import java.util.HashMap;
import java.util.Map;

/**
 * Created by andl on 11/02/2016.
 */
public class Node {

    Map<Character, Node> children;


    public Node() {
        this.children = new HashMap<>();

    }

    public Map<Character,Node> getChildren() {
        return this.children;
    }

    public void addChild (Character key, Node child) {
        this.children.put(key,child);
    }

    public Node findChild (Character key) {
        return this.children.get(key);
    }

}
