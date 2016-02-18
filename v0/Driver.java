import java.util.ArrayList;
import java.util.Arrays;

public class Driver {

    public static void main(String[] args) {

        SuffixTree test = new SuffixTree("bananada");
        String s = "s";
        //System.out.println(test.search(s.toCharArray()));
        CompressorSuffix suf = new CompressorSuffix("bananada");
        System.out.println("banadanandandnanan".equals(suf.decode(suf.encode("banadanandandnanan"))));


    }

}