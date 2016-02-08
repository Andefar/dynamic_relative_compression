import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {


        String S = "acbcbbcabbababcbsacbababaaaabcbbebcbcccabbabcababbabgaaabsacbcaaadsabbbbccccbabaafbacbcabbbcbcacbbabca$";
        String R = "aaabbbccecabcsfdg";

        Compressor cmp = new Compressor(R);

        ArrayList<Block> encodedS = cmp.encode(S);
        String decodedS = cmp.decode(encodedS);
        System.out.print(decodedS);
        System.out.print(S.equals(decodedS + "$"));
    }

}