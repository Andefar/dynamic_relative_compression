import org.junit.Test

/**
 * Created by andl on 11/02/2016.
 */
public class CompressorTest extends GroovyTestCase {

    String S0 = "bacaaabcbc";
    String S1 = "aaabcbcbcbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc";
    String ST0 = S0;
    String ST1 = S1;
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

    CompressorDPS cmp4 = new CompressorDPS(R0);
    CompressorDPS cmp5 = new CompressorDPS(R1);
    ArrayList<Block> encodedS4 = cmp4.encode(ST0);
    ArrayList<Block> encodedS5 = cmp5.encode(ST1);




    @Test
    public void testCompressionDecompression() {
        String results0 = cmp0.decodeArrayList(encodedS0);
        String results1 = cmp1.decodeArrayList(encodedS1);
        assertEquals(S0, results0);
        assertEquals(S1, results1);
    }

    @Test
    public void testCompressionDecompressionSuffix() {
        String results2 = cmp2.decodeArrayList(encodedS2);
        String results3 = cmp3.decodeArrayList(encodedS3);
        assertEquals(S0, results2);
        assertEquals(S1, results3);
    }

    @Test
    public void testCompressionDecompressionSuffixFileRead() {
        FileHandler f = new FileHandler();
        String S = f.readFromFileLine("/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/DNA_S");
        String R = f.readFromFileLine("/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/DNA_R");
        CompressorSuffix cmp = new CompressorSuffix(R);
        ArrayList<Block> encodedS = cmp.encode(S);
        String resultS = cmp.decodeArrayList(encodedS);

        assertEquals(S, resultS);

    }

    @Test
    public void testOperationsReplaceAndDeleteNaive() {

        DynamicOperationsNaive ops1 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops2 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops3 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops4 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops5 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops6 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops7 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops8 = new DynamicOperationsNaive(encodedS0, cmp0);
        ops1.replace(3,(char) 'c');
        ops2.replace(5,(char) 'b');
        ops3.replace(9,(char) 'a');
        ops4.replace(1,(char) 'c');
        ops5.delete(3);
        ops6.delete(5);
        ops7.delete(9);
        ops8.delete(1);
        String decodedS1 = cmp0.decodeArrayList(ops1.getC());
        String decodedS2 = cmp0.decodeArrayList(ops2.getC());
        String decodedS3 = cmp0.decodeArrayList(ops3.getC());
        String decodedS4 = cmp0.decodeArrayList(ops4.getC());
        String decodedS5 = cmp0.decodeArrayList(ops5.getC());
        String decodedS6 = cmp0.decodeArrayList(ops6.getC());
        String decodedS7 = cmp0.decodeArrayList(ops7.getC());
        String decodedS8 = cmp0.decodeArrayList(ops8.getC());

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

        DynamicOperationsNaive ops1 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops2 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops3 = new DynamicOperationsNaive(encodedS0, cmp0);
        DynamicOperationsNaive ops4 = new DynamicOperationsNaive(encodedS0, cmp0);
        ops1.insert(3,(char) 'c');
        ops2.insert(5,(char) 'b');
        ops3.insert(10,(char) 'a');
        ops4.insert(0,(char) 'c');
        String decodedS1 = cmp0.decodeArrayList(ops1.getC());
        String decodedS2 = cmp0.decodeArrayList(ops2.getC());
        String decodedS3 = cmp0.decodeArrayList(ops3.getC());
        String decodedS4 = cmp0.decodeArrayList(ops4.getC());

        //insert tests
        assertEquals("baccaaabcbc",decodedS1);
        assertEquals("bacaababcbc",decodedS2);
        assertEquals("bacaaabcbca",decodedS3);
        assertEquals("cbacaaabcbc",decodedS4);
    }

    @Test
    public void testOperationsReplaceAndDeleteMerge() {

        DynamicOperationsMerge ops1 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops2 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops3 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops4 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops5 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops6 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops7 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops8 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops9 = new DynamicOperationsMerge(encodedS0, cmp0);

        ops1.replace(3,(char) 'c');
        ops2.replace(5,(char) 'b');
        ops3.replace(9,(char) 'a');
        ops4.replace(1,(char) 'c');

        ops5.delete(3);
        ops6.delete(5);
        ops7.delete(9);
        ops8.delete(1);
        ops9.delete(0);

        String decodedS1 = cmp0.decodeArrayList(ops1.getC());
        String decodedS2 = cmp0.decodeArrayList(ops2.getC());
        String decodedS3 = cmp0.decodeArrayList(ops3.getC());
        String decodedS4 = cmp0.decodeArrayList(ops4.getC());
        String decodedS5 = cmp0.decodeArrayList(ops5.getC());
        String decodedS6 = cmp0.decodeArrayList(ops6.getC());
        String decodedS7 = cmp0.decodeArrayList(ops7.getC());
        String decodedS8 = cmp0.decodeArrayList(ops8.getC());
        String decodedS9 = cmp0.decodeArrayList(ops9.getC());




        //replace tests of final string
        assertEquals("baccaabcbc",decodedS1);
        assertEquals("bacaabbcbc",decodedS2);
        assertEquals("bacaaabcba",decodedS3);
        assertEquals("bccaaabcbc",decodedS4);

        //replace tests of compression
//        assertEquals(cmp0.encode("baccaabcbc").toString(), ops1.getC().toString());
//        assertEquals(cmp0.encode("bacaabbcbc").toString(), ops2.getC().toString());
//        assertEquals(cmp0.encode("bacaaabcba").toString(), ops3.getC().toString());
//        assertEquals(cmp0.encode("bccaaabcbc").toString(), ops4.getC().toString());

        //delete tests of final string
        assertEquals("bacaabcbc",decodedS5);
        assertEquals("bacaabcbc",decodedS6);
        assertEquals("bacaaabcb",decodedS7);
        assertEquals("bcaaabcbc",decodedS8);
        assertEquals("acaaabcbc",decodedS9);

//        //delete tests of compression
//        assertEquals(cmp0.encode("bacaabcbc").toString(), ops5.getC().toString());
//        assertEquals(cmp0.encode("bacaabcbc").toString(), ops6.getC().toString());
//        assertEquals(cmp0.encode("bacaaabcb").toString(), ops7.getC().toString());
//        assertEquals(cmp0.encode("bcaaabcbc").toString(), ops8.getC().toString());
//        assertEquals(cmp0.encode("acaaabcbc").toString(), ops9.getC().toString());
    }

    @Test
    public void testOperationsInsertMerge() {

        DynamicOperationsMerge ops1 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops2 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops3 = new DynamicOperationsMerge(encodedS0, cmp0);
        DynamicOperationsMerge ops4 = new DynamicOperationsMerge(encodedS0, cmp0);
        ops1.insert(3,(char) 'c');
        ops2.insert(5,(char) 'b');
        ops3.insert(10,(char) 'a');
        ops4.insert(0,(char) 'c');
        String decodedS1 = cmp0.decodeArrayList(ops1.getC());
        String decodedS2 = cmp0.decodeArrayList(ops2.getC());
        String decodedS3 = cmp0.decodeArrayList(ops3.getC());
        String decodedS4 = cmp0.decodeArrayList(ops4.getC());

        //insert tests of final string
        assertEquals("baccaaabcbc",decodedS1);
        assertEquals("bacaababcbc",decodedS2);
        assertEquals("bacaaabcbca",decodedS3);
        assertEquals("cbacaaabcbc",decodedS4);

        //insert tests of compression
//        assertEquals(cmp0.encode("baccaaabcbc").toString(), ops1.getC().toString());
//        assertEquals(cmp0.encode("bacaababcbc").toString(), ops2.getC().toString());
//        assertEquals(cmp0.encode("bacaaabcbca").toString(), ops3.getC().toString());
//        assertEquals(cmp0.encode("cbacaaabcbc").toString(), ops4.getC().toString());
    }

    @Test
    public void testOperationsAccessDPS() {

        DynamicOperations opsTest = new DynamicOperationsDPS(encodedS4, cmp4);

        char s0 = opsTest.access(0);
        char s1 = opsTest.access(1);
        char s2 = opsTest.access(2);
        char s3 = opsTest.access(3);
        char s4 = opsTest.access(4);
        char s5 = opsTest.access(5);
        char s6 = opsTest.access(6);
        char s7 = opsTest.access(7);
        char s8 = opsTest.access(8);
        char s9 = opsTest.access(9);


        //insert tests of final string
        assertEquals((char) 'b',s0);
        assertEquals((char) 'a',s1);
        assertEquals((char) 'c',s2);
        assertEquals((char) 'a',s3);
        assertEquals((char) 'a',s4);
        assertEquals((char) 'a',s5);
        assertEquals((char) 'b',s6);
        assertEquals((char) 'c',s7);
        assertEquals((char) 'b',s8);
        assertEquals((char) 'c',s9);

    }

    @Test
    public void testOperationsRelaceDPS() {

        DynamicOperations opsTest0 = new DynamicOperationsDPS(encodedS4, cmp4);
        opsTest0.replace(3,(char) 'b'); //case 1
        opsTest0.replace(5,(char) 'c'); //case 2
        opsTest0.replace(9,(char) 'a'); //case 3
        opsTest0.replace(0,(char) 'c'); //case 4
        String decodedS0 = cmp4.decodeBinTree(opsTest0.getDPS());
        //insert tests of final string
        assertEquals("cacbacbcba",decodedS0);


        DynamicOperations opsTest1 = new DynamicOperationsDPS(encodedS4, cmp4);
        opsTest1.replace(0,(char) 'a');
        opsTest1.replace(2,(char) 'a');
        opsTest1.replace(6,(char) 'a');
        opsTest1.replace(7,(char) 'a');
        opsTest1.replace(8,(char) 'a');
        opsTest1.replace(9,(char) 'a');
        String decodedS1 = cmp4.decodeBinTree(opsTest1.getDPS());
        assertEquals("aaaaaaaaaa",decodedS1);


    }

    @Test
    public void testOperationsInsertDPS() {

        DynamicOperationsDPS ops1 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops2 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops3 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops4 = new DynamicOperationsDPS(encodedS4, cmp4);

        ops1.insert(10,(char) 'a');
        ops1.insert(0,(char) 'a');
        ops1.insert(2,(char) 'c');
        ops1.insert(3,(char) 'a');
        ops1.insert(1,(char) 'c');




        ops2.insert(5,(char) 'b');
        ops3.insert(10,(char) 'a');
        ops4.insert(0,(char) 'c');
        String decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        String decodedS2 = cmp4.decodeBinTree(ops2.getDPS());
        String decodedS3 = cmp4.decodeBinTree(ops3.getDPS());
        String decodedS4 = cmp4.decodeBinTree(ops4.getDPS());

        //insert tests of final string
        assertEquals("acbcaacaaabcbca",decodedS1);
        assertEquals("bacaababcbc",decodedS2);
        assertEquals("bacaaabcbca",decodedS3);
        assertEquals("cbacaaabcbc",decodedS4);

        //insert tests of compression
//        assertEquals(cmp0.encode("baccaaabcbc").toString(), ops1.getC().toString());
//        assertEquals(cmp0.encode("bacaababcbc").toString(), ops2.getC().toString());
//        assertEquals(cmp0.encode("bacaaabcbca").toString(), ops3.getC().toString());
//        assertEquals(cmp0.encode("cbacaaabcbc").toString(), ops4.getC().toString());
    }

    @Test
    public void testOperationsDeleteDPS() {

        DynamicOperationsDPS ops1 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops2 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops3 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops4 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops5 = new DynamicOperationsDPS(encodedS4, cmp4);

        "bacaaabcbc";

        ops1.delete(3); //case 1
        ops1.delete(5); //case 1
        ops1.delete(7); //case 3
        ops1.delete(0); //case 4
        ops1.delete(1); //case 2
        ops1.delete(4);
        ops1.delete(3);


        ops2.delete(5);
        ops3.delete(9);
        ops4.delete(1);
        ops5.delete(0);

        String decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        String decodedS2 = cmp4.decodeBinTree(ops2.getDPS());
        String decodedS3 = cmp4.decodeBinTree(ops3.getDPS());
        String decodedS4 = cmp4.decodeBinTree(ops4.getDPS());
        String decodedS5 = cmp4.decodeBinTree(ops5.getDPS());

        //delete tests of final string
        assertEquals("aaa",decodedS1);
        assertEquals("bacaabcbc",decodedS2);
        assertEquals("bacaaabcb",decodedS3);
        assertEquals("bcaaabcbc",decodedS4);
        assertEquals("acaaabcbc",decodedS5);

    }


    @Test
    public void testOperationsReplaceDPS() {

        DynamicOperationsDPS ops1 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops2 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops3 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops4 = new DynamicOperationsDPS(encodedS4, cmp4);
        DynamicOperationsDPS ops5 = new DynamicOperationsDPS(encodedS4, cmp4);

        ops1.replace(0, (char) 'a');
        String decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        assertEquals("aacaaabcbc",decodedS1);

        ops1.replace(2, (char) 'a');
        decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        assertEquals("aaaaaabcbc",decodedS1);

        ops1.replace(6, (char) 'a');
        decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        assertEquals("aaaaaaacbc",decodedS1);

        ops1.replace(7, (char) 'a');
        decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        assertEquals("aaaaaaaabc",decodedS1);

        ops1.replace(8, (char) 'a');
        decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        assertEquals("aaaaaaaaac",decodedS1);

        ops1.replace(9, (char) 'a');
        decodedS1 = cmp4.decodeBinTree(ops1.getDPS());
        assertEquals("aaaaaaaaaa",decodedS1);



        ops2.delete(5);
        ops3.delete(9);
        ops4.delete(1);
        ops5.delete(0);


        String decodedS2 = cmp4.decodeBinTree(ops2.getDPS());
        String decodedS3 = cmp4.decodeBinTree(ops3.getDPS());
        String decodedS4 = cmp4.decodeBinTree(ops4.getDPS());
        String decodedS5 = cmp4.decodeBinTree(ops5.getDPS());

        //delete tests of final string
        //assertEquals("aaaaaaaaaa",decodedS1);
        assertEquals("bacaabcbc",decodedS2);
        assertEquals("bacaaabcb",decodedS3);
        assertEquals("bcaaabcbc",decodedS4);
        assertEquals("acaaabcbc",decodedS5);

    }

    @Test
    public void testFileHandlerSaveAndRead() {

        ArrayList<Block> toSave = encodedS1;
        FileHandler fh = new FileHandler();
        fh.toFile(toSave,"compressed.cmp");
        ArrayList<Block> fromFile = new ArrayList<>();

        try { fromFile = fh.read("compressed.cmp");}
        catch (IOException e) { e.printStackTrace();}

        String orig = cmp1.decodeArrayList(encodedS1);
        String res = cmp1.decodeArrayList(fromFile);
        assertEquals(orig,S1,res);

    }

}
