/**
 * Created by andl on 11/02/2016.
 * Edge object for suffix tree
 */
public class Edge {

    //Fields
    private Node source;
    private Node dest;
    private String label;

    //Constructor
    public Edge(Node source, Node dest, String label){
        this.source = source;
        this.dest = dest;
        this.label = label;
    }

    //Getters
    public Node getSource() { return this.source; }
    public Node getDest() { return this.dest;}
    public String getLabel() { return this.label;}

    //Setters
    public void setSource(Node source) { this.source = source; }
    public void setDest(Node dest) { this.dest = dest;}
    public void setLabel(String label) { this.label = label;}
}
