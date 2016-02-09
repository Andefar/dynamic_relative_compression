import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {


        String S = "bacaaabcbc$";
        String R = "abcaa";

        Compressor cmp = new Compressor(R);

        ArrayList<Block> encodedS = cmp.encode(S);

//        System.out.println("BEFORE REPLACE:");
//        for (Block b : encodedS) {
//            System.out.println(b.toString());
//        }
        System.out.println("0: " + S);

        DynamicOperationsNaive ops1 = new DynamicOperationsNaive(encodedS,R.toCharArray());
        DynamicOperationsNaive ops2 = new DynamicOperationsNaive(encodedS,R.toCharArray());
        DynamicOperationsNaive ops3 = new DynamicOperationsNaive(encodedS,R.toCharArray());
        DynamicOperationsNaive ops4 = new DynamicOperationsNaive(encodedS,R.toCharArray());

        DynamicOperationsNaive ops5 = new DynamicOperationsNaive(encodedS,R.toCharArray());
        DynamicOperationsNaive ops6 = new DynamicOperationsNaive(encodedS,R.toCharArray());
        DynamicOperationsNaive ops7 = new DynamicOperationsNaive(encodedS,R.toCharArray());
        DynamicOperationsNaive ops8 = new DynamicOperationsNaive(encodedS,R.toCharArray());
        DynamicOperationsNaive ops9 = new DynamicOperationsNaive(encodedS,R.toCharArray());

        ops1.replace(3,'c');
        ops2.replace(5,'b');
        ops3.replace(9,'a');
        ops4.replace(1,'c');

        ops5.delete(3);
        ops6.delete(5);
        ops7.delete(9);
        ops8.delete(1);

//        System.out.println("AFTER REPLACE");
//        for (Block b : ops.getC()) {
//            System.out.println(b.toString());
//        }


        String decodedS1 = cmp.decode(ops1.getC());
        String decodedS2 = cmp.decode(ops2.getC());
        String decodedS3 = cmp.decode(ops3.getC());
        String decodedS4 = cmp.decode(ops4.getC());

        String decodedS5 = cmp.decode(ops5.getC());
        String decodedS6 = cmp.decode(ops6.getC());
        String decodedS7 = cmp.decode(ops7.getC());
        String decodedS8 = cmp.decode(ops8.getC());

        System.out.println("1: " + decodedS1);
        System.out.println("2: " + decodedS2);
        System.out.println("3: " + decodedS3);
        System.out.println("4: " + decodedS4);
        System.out.println("5: " + decodedS5);
        System.out.println("6: " + decodedS6);
        System.out.println("7: " + decodedS7);
        System.out.println("8: " + decodedS8);



                                 //"bacaaabcbc"
        //replace tests
        System.out.println("1: " + "baccaabcbc".equals(decodedS1));
        System.out.println("2: " + "bacaabbcbc".equals(decodedS2));
        System.out.println("3: " + "bacaaabcba".equals(decodedS3));
        System.out.println("4: " + "bccaaabcbc".equals(decodedS4));

        //delete tests
        System.out.println("5: " + "bacaabcbc".equals(decodedS5));
        System.out.println("6: " + "bacaabcbc".equals(decodedS6));
        System.out.println("7: " + "bacaaabcb".equals(decodedS7));
        System.out.println("9: " + "bcaaabcbc".equals(decodedS8));



        System.out.println("delete test");
        for (int m = 0; m <= 9; m++) {
            ops9.delete(0);
            System.out.println(cmp.decode(ops9.getC()));
        }


    }

}