import java.util.ArrayList;

/**
 * Created by andl on 09/02/2016.
 */
abstract class DynamicOperations {

    protected ArrayList<Block> C;
    protected char[] RA;
    protected Compressor cmp;

    public DynamicOperations(ArrayList<Block> C, char[] RA, Compressor cmp){
        this.C = (ArrayList<Block>) C.clone();
        this.RA = RA.clone();
        this.cmp = cmp;
    }

    public abstract ArrayList<Block> getC();
    public abstract char access(int index) throws IndexOutOfBoundsException;
    public abstract void replace(int i, char sub);
    public abstract void delete(int i);
    public abstract void insert(int i, char c);

    public int getSLength(){
        int len = 0;

        for (Block b : this.C){
            len += b.getLength();
        }

        return len;
    }
}
