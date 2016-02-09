import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {


        String S = "bacaaabcbc$";
        String R = "abcaa";

        Compressor cmp = new Compressor(R);

        ArrayList<Block> encodedS = cmp.encode(S);

        for (Block b : encodedS) {
            System.out.println(b.toString());
        }

        //String decodedS = cmp.decode(encodedS);
        //System.out.print(decodedS);
        //System.out.print(S.equals(decodedS + "$"));
    }

}