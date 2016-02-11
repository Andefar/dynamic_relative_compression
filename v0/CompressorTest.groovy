import org.junit.Test

/**
 * Created by andl on 11/02/2016.
 */
public class CompressorTest extends GroovyTestCase {

    String S0 = "bacaaabcbc";
    String S1 = "aaabcbcbcbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc";
    String ST0 = S0 + "\$";
    String ST1 = S1 + "\$";
    String R0 = "abcaa";
    String R1 = "acbabbababaacbcca";
    Compressor cmp0 = new Compressor(R0);
    Compressor cmp1 = new Compressor(R1);
    ArrayList<Block> encodedS0 = cmp0.encode(ST0);
    ArrayList<Block> encodedS1 = cmp1.encode(ST1);

    @Test
    public void testCompressionDecompression() {
        String results0 = cmp0.decode(encodedS0);
        String results1 = cmp1.decode(encodedS1);
        assertEquals(S0, results0);
        assertEquals(S1, results1);
    }

    @Test
    public void testOperationsReplaceAndDelete() {

        DynamicOperationsNaive ops1 = new DynamicOperationsNaive(encodedS0, R0.toCharArray());
        DynamicOperationsNaive ops2 = new DynamicOperationsNaive(encodedS0, R0.toCharArray());
        DynamicOperationsNaive ops3 = new DynamicOperationsNaive(encodedS0, R0.toCharArray());
        DynamicOperationsNaive ops4 = new DynamicOperationsNaive(encodedS0, R0.toCharArray());
        DynamicOperationsNaive ops5 = new DynamicOperationsNaive(encodedS0, R0.toCharArray());
        DynamicOperationsNaive ops6 = new DynamicOperationsNaive(encodedS0, R0.toCharArray());
        DynamicOperationsNaive ops7 = new DynamicOperationsNaive(encodedS0, R0.toCharArray());
        DynamicOperationsNaive ops8 = new DynamicOperationsNaive(encodedS0, R0.toCharArray());
        ops1.replace(3,(char) 'c');
        ops2.replace(5,(char) 'b');
        ops3.replace(9,(char) 'a');
        ops4.replace(1,(char) 'c');
        ops5.delete(3);
        ops6.delete(5);
        ops7.delete(9);
        ops8.delete(1);
        String decodedS1 = cmp0.decode(ops1.getC());
        String decodedS2 = cmp0.decode(ops2.getC());
        String decodedS3 = cmp0.decode(ops3.getC());
        String decodedS4 = cmp0.decode(ops4.getC());
        String decodedS5 = cmp0.decode(ops5.getC());
        String decodedS6 = cmp0.decode(ops6.getC());
        String decodedS7 = cmp0.decode(ops7.getC());
        String decodedS8 = cmp0.decode(ops8.getC());

        //replace tests
        assertEquals("baccaabcbc",decodedS1);
        assertEquals("bacaabbcbc",decodedS2);
        assertEquals("bacaaabcba",decodedS3);
        assertEquals("bccaaabcbc",decodedS4);
        //delete tests
        assertEquals("bacaabcbc",decodedS5);
        assertEquals("bacaabcbc",decodedS6);
        assertEquals("bacaaabcb",decodedS7);
        assertEquals("bcaaabcbc",decodedS8);

    }

    @Test
    public void testFileHandlerSaveAndRead() {

        ArrayList<Block> toSave = encodedS1;
        FileHandler fh = new FileHandler();
        fh.toFile(toSave,"compressed.cmp");
        ArrayList<Block> fromFile = new ArrayList<>();

        try { fromFile = fh.read("compressed.cmp");}
        catch (IOException e) { e.printStackTrace();}

        String orig = cmp1.decode(encodedS1);
        String res = cmp1.decode(fromFile);
        assertEquals(orig,S1,res);

    }

}
