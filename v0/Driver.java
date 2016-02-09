import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {


        String S = "bacaaabcbc$";
        String R = "abcaa";

        Compressor cmp = new Compressor(R);

        ArrayList<Block> encodedS = cmp.encode(S);

        System.out.println("BEFORE REPLACE:");
        for (Block b : encodedS) {
            System.out.println(b.toString());
        }

        DynamicOperationsNaive ops = new DynamicOperationsNaive(encodedS,R.toCharArray());

        System.out.println(ops.access(6));

        //System.out.println("AFTER REPLACE");
        //for (Block b : ops.getC()) {
        //    System.out.println(b.toString());
        //}

        //String decodedS = cmp.decode(encodedS);
        //System.out.print(decodedS);
        //System.out.print(S.equals(decodedS + "$"));
    }

}