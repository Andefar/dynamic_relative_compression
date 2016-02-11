import java.util.ArrayList;
import java.util.Arrays;

public class Driver {

    public static void main(String[] args) {


        String S = "bacaaabcbc";
        String S1 = "bacaaababc";
        String R = "abacaabc";

        Compressor cmp = new Compressor(R);

        ArrayList<Block> encodedS = cmp.encode(S);
        ArrayList<Block> encodedS1 = cmp.encode(S1);

        System.out.println(encodedS1);

        DynamicOperationsMerge don = new DynamicOperationsMerge(encodedS, (R + "$").toCharArray(), cmp);
        DynamicOperationsNaive donN = new DynamicOperationsNaive(encodedS, (R + "$").toCharArray(), cmp);

        System.out.println(don.getC());
        System.out.println(cmp.decode(don.getC()));

        don.replace(7, 'a');
        donN.replace(7, 'a');

        System.out.println(don.getC());
        System.out.println(cmp.decode(don.getC()));

        System.out.println(donN.getC());
        System.out.println(cmp.decode(donN.getC()));



        /*
        for (int i = 0; i < 10; i++) {
            System.out.println(don.access(i));
        }

        for (Block b : encodedS) {
            System.out.println(b.toString());
        }

        String decodedS = cmp.decode(encodedS);
        System.out.print(decodedS);
        System.out.print(S.equals(decodedS + "$"));
        */

    }
}