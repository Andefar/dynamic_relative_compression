import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by andl on 09/02/2016.
 */
abstract class DynamicOperations {

    ArrayList<Block> C;
    Compressor cmp;

    public DynamicOperations(ArrayList<Block> C, Compressor cmp){
        this.C = new ArrayList();
        this.C.addAll(C.stream().map(b -> new Block(b.getPos(), b.getLength())).collect(Collectors.toList()));
        this.cmp = cmp;
    }

    abstract ArrayList<Block> getC();
    public abstract char access(int index) throws IndexOutOfBoundsException;
    public abstract void replace(int i, char sub);
    public abstract void delete(int i);
    public abstract void insert(int i, char c);

    int getSLength(){
        int len = 0;
        for (Block b : this.C){
            len += b.getLength();
        }
        return len;
    }
}
