
public class Driver {

    public static void main(String[] args) {


        String S = "acbcbbcabbababcbsacbababaaaabcbbebcbcccabbabcababbabgaaabsacbcaaadsabbbbccccbabaafbacbcabbbcbcacbbabca";
        String R = "aaabbbcccabc";

        Compressor cmp = new Compressor(R);

        cmp.encode(S);
    }

}