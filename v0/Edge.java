/**
 * Created by andl on 11/02/2016.
 * Edge object for suffix tree
 */
public class Edge {

    //Fields
    private char[] label;

    public Edge(char[] label){
        this.label = label;
    }

    //Getters
    public char[] getLabel() { return this.label;}

    //Setters
    public void setLabel(char[] label) { this.label = label;}
}
