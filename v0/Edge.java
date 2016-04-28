/**
 * Created by andl on 11/02/2016.
 * Edge object for suffix tree
 */
public class Edge {

    //Fields
    private int startR;
    private int length;
    //private char[] label;

    public Edge(int startR, int length){
        this.startR = startR;
        this.length= length;
    }

    //Getters
    public int getStartR() { return this.startR;}
    public int getLength() {return this.length; }
    //public int getEndR() { return this.endR;}

    //Setters
    public void setStartR(int startR) { this.startR = startR;}
    public void setLength(int length) { this.length = length;}
}
