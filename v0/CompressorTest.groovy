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
    CompressorNaive cmp0 = new CompressorNaive(R0);
    CompressorNaive cmp1 = new CompressorNaive(R1);
    ArrayList<Block> encodedS0 = cmp0.encode(ST0);
    ArrayList<Block> encodedS1 = cmp1.encode(ST1);

    CompressorSuffix cmp2 = new CompressorSuffix(R0);
    CompressorSuffix cmp3 = new CompressorSuffix(R1);
    ArrayList<Block> encodedS2 = cmp2.encode(ST0);
    ArrayList<Block> encodedS3 = cmp3.encode(ST1);

    @Test
    public void testCompressionDecompression() {
        String results0 = cmp0.decode(encodedS0);
        String results1 = cmp1.decode(encodedS1);
        assertEquals(S0, results0);
        assertEquals(S1, results1);
    }

    @Test
    public void testCompressionDecompressionSuffix() {
        String results2 = cmp2.decode(encodedS2);
        String results3 = cmp3.decode(encodedS3);
        assertEquals(S0, results2);
        assertEquals(S1, results3);
    }

    @Test
    public void testCompressionDecompressionSuffixFileRead() {
        FileHandler f = new FileHandler();
        String S = f.readFromFile("/Users/Josefinetusindfryd/Desktop/dynamic_relative_compression/data/DNA_S");
        String R = f.readFromFile("/Users/Josefinetusindfryd/Desktop/dynamic_relative_compression/data/DNA_R");
        CompressorSuffix cmp = new CompressorSuffix(R);
        ArrayList<Block> encodedS = cmp.encode(S);
        String resultS = cmp.decode(encodedS);

        assertEquals(S, resultS);

    }

    @Test
    public void testOperationsReplaceAndDeleteNaive() {

        DynamicOperationsNaive ops1 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops2 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops3 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops4 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops5 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops6 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops7 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops8 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
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
    public void testOperationsInsertNaive() {

        DynamicOperationsNaive ops1 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops2 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops3 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsNaive ops4 = new DynamicOperationsNaive(encodedS0, R0.toCharArray(), cmp0);
        ops1.insert(3,(char) 'c');
        ops2.insert(5,(char) 'b');
        ops3.insert(10,(char) 'a');
        ops4.insert(0,(char) 'c');
        String decodedS1 = cmp0.decode(ops1.getC());
        String decodedS2 = cmp0.decode(ops2.getC());
        String decodedS3 = cmp0.decode(ops3.getC());
        String decodedS4 = cmp0.decode(ops4.getC());

        //insert tests
        assertEquals("baccaaabcbc",decodedS1);
        assertEquals("bacaababcbc",decodedS2);
        assertEquals("bacaaabcbca",decodedS3);
        assertEquals("cbacaaabcbc",decodedS4);
    }

    @Test
    public void testOperationsReplaceAndDeleteMerge() {

        DynamicOperationsMerge ops1 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops2 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops3 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops4 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops5 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops6 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops7 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops8 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops9 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        ops1.replace(3,(char) 'c');
        ops2.replace(5,(char) 'b');
        ops3.replace(9,(char) 'a');
        ops4.replace(1,(char) 'c');

        ops5.delete(3);
        ops6.delete(5);
        ops7.delete(9);
        ops8.delete(1);
        ops9.delete(0);

        String decodedS1 = cmp0.decode(ops1.getC());
        String decodedS2 = cmp0.decode(ops2.getC());
        String decodedS3 = cmp0.decode(ops3.getC());
        String decodedS4 = cmp0.decode(ops4.getC());
        String decodedS5 = cmp0.decode(ops5.getC());
        String decodedS6 = cmp0.decode(ops6.getC());
        String decodedS7 = cmp0.decode(ops7.getC());
        String decodedS8 = cmp0.decode(ops8.getC());
        String decodedS9 = cmp0.decode(ops9.getC());

        //replace tests of final string
        assertEquals("baccaabcbc",decodedS1);
        assertEquals("bacaabbcbc",decodedS2);
        assertEquals("bacaaabcba",decodedS3);
        assertEquals("bccaaabcbc",decodedS4);

        //replace tests of compression
        assertEquals(cmp0.encode("baccaabcbc").toString(), ops1.getC().toString());
        assertEquals(cmp0.encode("bacaabbcbc").toString(), ops2.getC().toString());
        assertEquals(cmp0.encode("bacaaabcba").toString(), ops3.getC().toString());
        assertEquals(cmp0.encode("bccaaabcbc").toString(), ops4.getC().toString());

        //delete tests of final string
        assertEquals("bacaabcbc",decodedS5);
        assertEquals("bacaabcbc",decodedS6);
        assertEquals("bacaaabcb",decodedS7);
        assertEquals("bcaaabcbc",decodedS8);
        assertEquals("acaaabcbc",decodedS9);

        //delete tests of compression
        assertEquals(cmp0.encode("bacaabcbc").toString(), ops5.getC().toString());
        assertEquals(cmp0.encode("bacaabcbc").toString(), ops6.getC().toString());
        assertEquals(cmp0.encode("bacaaabcb").toString(), ops7.getC().toString());
        assertEquals(cmp0.encode("bcaaabcbc").toString(), ops8.getC().toString());
        assertEquals(cmp0.encode("acaaabcbc").toString(), ops9.getC().toString());
    }

    @Test
    public void testOperationsInsertMerge() {

        DynamicOperationsMerge ops1 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops2 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops3 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        DynamicOperationsMerge ops4 = new DynamicOperationsMerge(encodedS0, R0.toCharArray(), cmp0);
        ops1.insert(3,(char) 'c');
        ops2.insert(5,(char) 'b');
        ops3.insert(10,(char) 'a');
        ops4.insert(0,(char) 'c');
        String decodedS1 = cmp0.decode(ops1.getC());
        String decodedS2 = cmp0.decode(ops2.getC());
        String decodedS3 = cmp0.decode(ops3.getC());
        String decodedS4 = cmp0.decode(ops4.getC());

        //insert tests of final string
        assertEquals("baccaaabcbc",decodedS1);
        assertEquals("bacaababcbc",decodedS2);
        assertEquals("bacaaabcbca",decodedS3);
        assertEquals("cbacaaabcbc",decodedS4);

        //insert tests of compression
        assertEquals(cmp0.encode("baccaaabcbc").toString(), ops1.getC().toString());
        assertEquals(cmp0.encode("bacaababcbc").toString(), ops2.getC().toString());
        assertEquals(cmp0.encode("bacaaabcbca").toString(), ops3.getC().toString());
        assertEquals(cmp0.encode("cbacaaabcbc").toString(), ops4.getC().toString());
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
