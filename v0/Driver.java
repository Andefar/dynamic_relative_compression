
public class Driver {

    public static void main(String[] args) {
        Compressor cmp = new Compressor();

        String C = "acbcbbcabbababcbsacbababaaaabcbbebcbcccabbabcababbabgaaabsacbcaaadsabbbbccccbabaafbacbcabbbcbcacbbabca$";
        String R = "aaabbbcccabc$";

        cmp.encode(C,R);
    }

}