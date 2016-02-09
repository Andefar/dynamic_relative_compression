import java.util.ArrayList;

/**
 * Created by andl on 09/02/2016.
 */
abstract class DynamicOperations {

    protected ArrayList<Block> C;
    protected char[] RA;

    public DynamicOperations(ArrayList<Block> C, char[] RA){
        this.C = (ArrayList<Block>) C.clone();
        this.RA = RA.clone();
    }

    public abstract ArrayList<Block> getC();
    public abstract char access(int index);
    public abstract void replace(int i, char sub);
    public abstract void delete(int i);
    public abstract void insert(int i, char c);

}
