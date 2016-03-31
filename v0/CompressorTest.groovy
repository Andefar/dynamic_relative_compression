import org.junit.Test

/**
 * Created by andl on 11/02/2016.
 */
public class CompressorTest extends GroovyTestCase {

    // ==== SETUP ====

    /**
     * Path to DNA test files - TODO: CHANGE THIS TO YOUR OWN PATH
     */
    String S_file_path = "/Users/AndreasLauritzen/dynamic_relative_compression/DNA_S";
    String R_file_path = "/Users/AndreasLauritzen/dynamic_relative_compression/DNA_R";

    /**
     * Test strings
     */
    String S_short = "bacaaabcbc";
    String R_short = "abcaa";

    String S_long = "aaabcbcbcbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc";
    String R_long = "bcbcbcbcccabababaa";

    FileHandler fileHandler = new FileHandler();
    String S_file = fileHandler.readFromFileLine(S_file_path);
    String R_file = fileHandler.readFromFileLine(R_file_path);

    /**
     * Naive Compressors
     */
    CompressorNaive naiveCompressorShort = new CompressorNaive(R_short);
    ArrayList<Block> naiveEncodedShort = naiveCompressorShort.encode(S_short);

    CompressorNaive naiveCompressorLong = new CompressorNaive(R_long);
    ArrayList<Block> naiveEncodedLong = naiveCompressorLong.encode(S_long);

    CompressorNaive naiveCompressorFile = new CompressorNaive(R_file);
    ArrayList<Block> naiveEncodedFile = naiveCompressorFile.encode(S_file);

    /**
     * Suffix Compressors
     */
    CompressorSuffix suffixCompressorShort = new CompressorSuffix(R_short);
    ArrayList<Block> suffixEncodedShort = suffixCompressorShort.encode(S_short);

    CompressorSuffix suffixCompressorLong = new CompressorSuffix(R_long);
    ArrayList<Block> suffixEncodedLong = suffixCompressorLong.encode(S_long);

    CompressorSuffix suffixCompressorFile = new CompressorSuffix(R_file);
    ArrayList<Block> suffixEncodedFile = suffixCompressorFile.encode(S_file);

    /**
     * DPS Compressors
     */
    CompressorDPS DPSCompressorShort = new CompressorDPS(R_short);
    ArrayList<Block> DPSEncodedShort = DPSCompressorShort.encode(S_short);

    CompressorDPS DPSCompressorLong = new CompressorDPS(R_long);
    ArrayList<Block> DPSEncodedLong = DPSCompressorLong.encode(S_long);

    CompressorDPS DPSCompressorFile = new CompressorDPS(R_file);
    ArrayList<Block> DPSEncodedFile = DPSCompressorFile.encode(S_file);

    /**
     * Naive Dynamic Operations
     */



    /**
     * Suffix Dynamic Operations
     */

    DynamicOperationsMerge mergeDynamicOperationsShort = new DynamicOperationsMerge(DPSEncodedShort,DPSCompressorShort);
    DynamicOperationsMerge mergeDynamicOperationsLong = new DynamicOperationsMerge(DPSEncodedLong,DPSCompressorLong);
    DynamicOperationsMerge mergeDynamicOperationsFile = new DynamicOperationsMerge(DPSEncodedFile,DPSCompressorFile);

    /**
     * DPS Dynamic Operations
     */

    DynamicOperationsDPS DPSDynamicOperationsShort = new DynamicOperationsDPS(DPSEncodedShort,DPSCompressorShort);
    DynamicOperationsDPS DPSDynamicOperationsLong = new DynamicOperationsDPS(DPSEncodedLong,DPSCompressorLong);
    DynamicOperationsDPS DPSDynamicOperationsFile = new DynamicOperationsDPS(DPSEncodedFile,DPSCompressorFile);



    // ==== TESTS ====

    /**
     * Decompression tests on all three test strings. File read/write also included.
     */
    @Test
    public void testNaiveCompressionDecompression() {
        String resultShort = naiveCompressorShort.decodeArrayList(naiveEncodedShort);
        String resultLong  = naiveCompressorLong.decodeArrayList(naiveEncodedLong);
        String resultFile  = naiveCompressorFile.decodeArrayList(naiveEncodedFile);

        assertEquals(S_short, resultShort);
        assertEquals(S_long, resultLong);
        assertEquals(S_file, resultFile);
    }

    @Test
    public void testSuffixCompressionDecompression() {
        String resultShort = suffixCompressorShort.decodeArrayList(suffixEncodedShort);
        String resultLong = suffixCompressorLong.decodeArrayList(suffixEncodedLong);
        String resultFile = suffixCompressorFile.decodeArrayList(suffixEncodedFile);

        assertEquals(S_short, resultShort);
        assertEquals(S_long, resultLong);
        assertEquals(S_file, resultFile);
    }

    @Test
    public void testDPSCompressionDecompressionArrayList() {
        String resultShort = DPSCompressorShort.decodeArrayList(DPSEncodedShort);
        String resultLong = DPSCompressorLong.decodeArrayList(DPSEncodedLong);
        String resultFile = DPSCompressorFile.decodeArrayList(DPSEncodedFile);

        assertEquals(S_short, resultShort);
        assertEquals(S_long, resultLong);
        assertEquals(S_file, resultFile);
    }

    @Test
    public void testDPSCompressionDecompressionBinaryTree() {
        BinaryTree binaryTreeShort = new BinaryTree(DPSEncodedShort);
        BinaryTree binaryTreeLong = new BinaryTree(DPSEncodedLong);
        BinaryTree binaryTreeFile = new BinaryTree(DPSEncodedFile);

        String resultShort = DPSCompressorShort.decodeBinTree(binaryTreeShort);
        String resultLong = DPSCompressorLong.decodeBinTree(binaryTreeLong);
        String resultFile = DPSCompressorFile.decodeBinTree(binaryTreeFile);

        assertEquals(S_short, resultShort);
        assertEquals(S_long, resultLong);
        assertEquals(S_file, resultFile);
    }

    //Naive operations tests
    @Test
    public void testNaiveOperationsAccess () {

        DynamicOperationsNaive naiveDynamicOperationsShort = new DynamicOperationsNaive(DPSEncodedShort,DPSCompressorShort);
        DynamicOperationsNaive naiveDynamicOperationsLong = new DynamicOperationsNaive(DPSEncodedLong,DPSCompressorLong);
        DynamicOperationsNaive naiveDynamicOperationsFile = new DynamicOperationsNaive(DPSEncodedFile,DPSCompressorFile);

        assertEquals(naiveDynamicOperationsShort.access(0),(char) 'b');
        assertEquals(naiveDynamicOperationsLong.access(0),(char) 'a');
        assertEquals(naiveDynamicOperationsFile.access(0),(char) 'G');
        assertEquals(naiveDynamicOperationsShort.access(2),(char) 'c');
        assertEquals(naiveDynamicOperationsLong.access(2),(char) 'a');
        assertEquals(naiveDynamicOperationsFile.access(2),(char) 'T');
        assertEquals(naiveDynamicOperationsShort.access(9),(char) 'c');
        assertEquals(naiveDynamicOperationsLong.access(9),(char) 'b');
        assertEquals(naiveDynamicOperationsFile.access(9),(char) 'G');


    }

    @Test
    public void testNaiveOperationsInsert () {

        DynamicOperationsNaive naiveDynamicOperationsShort = new DynamicOperationsNaive(naiveEncodedShort,naiveCompressorShort);
        DynamicOperationsNaive naiveDynamicOperationsLong = new DynamicOperationsNaive(naiveEncodedLong,naiveCompressorLong);
        DynamicOperationsNaive naiveDynamicOperationsFile = new DynamicOperationsNaive(naiveEncodedFile,naiveCompressorFile);

        naiveDynamicOperationsShort.insert(0,(char) 'a');
        naiveDynamicOperationsLong.insert(0,(char) 'a');
        naiveDynamicOperationsFile.insert(0,(char) 'A');
        naiveDynamicOperationsShort.insert(2,(char) 'b');
        naiveDynamicOperationsLong.insert(2,(char) 'b');
        naiveDynamicOperationsFile.insert(2,(char) 'G');
        naiveDynamicOperationsShort.insert(9,(char) 'c');
        naiveDynamicOperationsLong.insert(9,(char) 'c');
        naiveDynamicOperationsFile.insert(9,(char) 'C');

        assertEquals("abbacaaabccbc",naiveCompressorShort.decodeArrayList(naiveDynamicOperationsShort.getC()));
        assertEquals("aabaabcbccbcbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc",naiveCompressorLong.decodeArrayList(naiveDynamicOperationsLong.getC()));
        assertEquals("AGGATCAATCGAGGTGGA",naiveCompressorFile.decodeArrayList(naiveDynamicOperationsFile.getC()).substring(0,18));

    }

    @Test
    public void testNaiveOperationsReplace () {

        DynamicOperationsNaive naiveDynamicOperationsShort = new DynamicOperationsNaive(naiveEncodedShort,naiveCompressorShort);
        DynamicOperationsNaive naiveDynamicOperationsLong = new DynamicOperationsNaive(naiveEncodedLong,naiveCompressorLong);
        DynamicOperationsNaive naiveDynamicOperationsFile = new DynamicOperationsNaive(naiveEncodedFile,naiveCompressorFile);

        naiveDynamicOperationsShort.replace(1,(char) 'b');
        naiveDynamicOperationsLong.replace(1,(char) 'b');
        naiveDynamicOperationsFile.replace(1,(char) 'G');
        naiveDynamicOperationsShort.replace(3,(char) 'c');
        naiveDynamicOperationsLong.replace(3,(char) 'c');
        naiveDynamicOperationsFile.replace(3,(char) 'A');
        naiveDynamicOperationsShort.replace(7,(char) 'a');
        naiveDynamicOperationsLong.replace(7,(char) 'a');
        naiveDynamicOperationsFile.replace(7,(char) 'A');

        assertEquals("bbccaababc",naiveCompressorShort.decodeArrayList(naiveDynamicOperationsShort.getC()));
        assertEquals("abaccbcacbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc",naiveCompressorLong.decodeArrayList(naiveDynamicOperationsLong.getC()));
        assertEquals("GGTAAATAAGG",naiveCompressorFile.decodeArrayList(naiveDynamicOperationsFile.getC()).substring(0,11));


    }
    @Test
    public void testNaiveOperationsDelete () {

    }

    //Merge operations tests
    @Test
    public void testMergeOperationsAccess () {

        DynamicOperationsMerge mergeDynamicOperationsShort = new DynamicOperationsMerge(DPSEncodedShort,DPSCompressorShort);
        DynamicOperationsMerge mergeDynamicOperationsLong = new DynamicOperationsMerge(DPSEncodedLong,DPSCompressorLong);
        DynamicOperationsMerge mergeDynamicOperationsFile = new DynamicOperationsMerge(DPSEncodedFile,DPSCompressorFile);

        assertEquals(mergeDynamicOperationsShort.access(0),(char) 'b');
        assertEquals(mergeDynamicOperationsLong.access(0),(char) 'a');
        assertEquals(mergeDynamicOperationsFile.access(0),(char) 'G');
        assertEquals(mergeDynamicOperationsShort.access(2),(char) 'c');
        assertEquals(mergeDynamicOperationsLong.access(2),(char) 'a');
        assertEquals(mergeDynamicOperationsFile.access(2),(char) 'T');
        assertEquals(mergeDynamicOperationsShort.access(9),(char) 'c');
        assertEquals(mergeDynamicOperationsLong.access(9),(char) 'b');
        assertEquals(mergeDynamicOperationsFile.access(9),(char) 'G');

    }

    @Test
    public void testMergeOperationsInsert () {


        DynamicOperationsMerge mergeDynamicOperationsShort = new DynamicOperationsMerge(naiveEncodedShort,naiveCompressorShort);
        DynamicOperationsMerge mergeDynamicOperationsLong = new DynamicOperationsMerge(naiveEncodedLong,naiveCompressorLong);
        DynamicOperationsMerge mergeDynamicOperationsFile = new DynamicOperationsMerge(naiveEncodedFile,naiveCompressorFile);

        mergeDynamicOperationsShort.insert(0,(char) 'a');
        mergeDynamicOperationsLong.insert(0,(char) 'a');
        mergeDynamicOperationsFile.insert(0,(char) 'A');
        mergeDynamicOperationsShort.insert(2,(char) 'b');
        mergeDynamicOperationsLong.insert(2,(char) 'b');//TODO: fix this!!
        mergeDynamicOperationsFile.insert(2,(char) 'G');
        mergeDynamicOperationsShort.insert(9,(char) 'c');
        mergeDynamicOperationsLong.insert(9,(char) 'c');//TODO: fix this!!
        mergeDynamicOperationsFile.insert(9,(char) 'C');

        assertEquals("abbacaaabccbc",naiveCompressorShort.decodeArrayList(mergeDynamicOperationsShort.getC()));
        assertEquals("aabaabcbcbccbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc",naiveCompressorLong.decodeArrayList(mergeDynamicOperationsLong.getC()));
        assertEquals("AGGATCAATCGAGGTGGA",naiveCompressorFile.decodeArrayList(mergeDynamicOperationsFile.getC()).substring(0,18));


    }

    @Test
    public void testMergeOperationsReplace () {

        DynamicOperationsMerge mergeDynamicOperationsShort = new DynamicOperationsMerge(naiveEncodedShort,naiveCompressorShort);
        DynamicOperationsMerge mergeDynamicOperationsLong = new DynamicOperationsMerge(naiveEncodedLong,naiveCompressorLong);
        DynamicOperationsMerge mergeDynamicOperationsFile = new DynamicOperationsMerge(naiveEncodedFile,naiveCompressorFile);

        mergeDynamicOperationsShort.replace(1,(char) 'b');
        mergeDynamicOperationsLong.replace(1,(char) 'b');
        mergeDynamicOperationsFile.replace(1,(char) 'G');
        mergeDynamicOperationsShort.replace(3,(char) 'c');
        mergeDynamicOperationsLong.replace(3,(char) 'c');
        mergeDynamicOperationsFile.replace(3,(char) 'A');
        mergeDynamicOperationsShort.replace(7,(char) 'a');
        mergeDynamicOperationsLong.replace(7,(char) 'a');
        mergeDynamicOperationsFile.replace(7,(char) 'A');

        assertEquals("bbccaababc",naiveCompressorShort.decodeArrayList(mergeDynamicOperationsShort.getC()));
        assertEquals("abaccbcacbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc",naiveCompressorLong.decodeArrayList(mergeDynamicOperationsLong.getC()));
        assertEquals("GGTAAATAAGG",naiveCompressorFile.decodeArrayList(mergeDynamicOperationsFile.getC()).substring(0,11));


    }
    @Test
    public void testMergeOperationsDelete () {

    }

    //DPS operations tests
    @Test
    public void testDPSOperationsAccess () {

        DynamicOperationsDPS DPSDynamicOperationsShort = new DynamicOperationsDPS(DPSEncodedShort,DPSCompressorShort);
        DynamicOperationsDPS DPSDynamicOperationsLong = new DynamicOperationsDPS(DPSEncodedLong,DPSCompressorLong);
        DynamicOperationsDPS DPSDynamicOperationsFile = new DynamicOperationsDPS(DPSEncodedFile,DPSCompressorFile);

        assertEquals(DPSDynamicOperationsShort.access(0),(char) 'b');
        assertEquals(DPSDynamicOperationsLong.access(0),(char) 'a');
        assertEquals(DPSDynamicOperationsFile.access(0),(char) 'G');
        assertEquals(DPSDynamicOperationsShort.access(2),(char) 'c');
        assertEquals(DPSDynamicOperationsLong.access(2),(char) 'a');
        assertEquals(DPSDynamicOperationsFile.access(2),(char) 'T');
        assertEquals(DPSDynamicOperationsShort.access(9),(char) 'c');
        assertEquals(DPSDynamicOperationsLong.access(9),(char) 'b');
        assertEquals(DPSDynamicOperationsFile.access(9),(char) 'G');

    }

    @Test
    public void testDPSOperationsInsert () {

        DynamicOperationsDPS DPSDynamicOperationsShort = new DynamicOperationsDPS(DPSEncodedShort,DPSCompressorShort);
        DynamicOperationsDPS DPSDynamicOperationsLong = new DynamicOperationsDPS(DPSEncodedLong,DPSCompressorLong);
        DynamicOperationsDPS DPSDynamicOperationsFile = new DynamicOperationsDPS(DPSEncodedFile,DPSCompressorFile);

        DPSDynamicOperationsShort.insert(0,(char) 'a');
        //DPSDynamicOperationsLong.insert(0,(char) 'a');
        DPSDynamicOperationsFile.insert(0,(char) 'A');
        DPSDynamicOperationsShort.insert(2,(char) 'b');
        DPSDynamicOperationsLong.insert(2,(char) 'b');
        DPSDynamicOperationsFile.insert(2,(char) 'G');
        DPSDynamicOperationsShort.insert(9,(char) 'c');
        DPSDynamicOperationsLong.insert(9,(char) 'c');
        DPSDynamicOperationsFile.insert(9,(char) 'C');

        assertEquals("abbacaaabccbc",DPSCompressorShort.decodeBinTree(DPSDynamicOperationsShort.getDPS()));
        assertEquals("aabaabcbccbcbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc",DPSCompressorLong.decodeBinTree(DPSDynamicOperationsLong.getDPS()));
//        assertEquals("AGGATCAATCGAGGTGGACACCAGAGGCGGGGACTTGTAAATAACACTGGGCTGTAGGAGTGATGGGGTTCACCTCTAATTCTAAGATGGCTAGATAATGCATCTTTCAGGGTTGTGCTTCTATCTAGAAGGTAGAGCTGTGGTCGTTCAATAAAAGTCCTCAAGAGGTTGGTTAATACGCATGTTTAATAGTACAGTATGGTGACTATAGTCAACAATAATTTATTGTACATTTTTAAATAGCTAGAAGAAAAGCATTGGGAAGTTTCCAACATGAAGAAAAGATAAATGGTCAAGGGAATGGATATCCTAATTACCCTGATTTGATCATTATGCATTATATACATGAATCAAAATATCACACATACCTTCAAACTATGTACAAATATTATATACCAATAAAAAATCATCATCATCATCTCCATCATCACCACCCTCCTCCTCATCACCACCAGCATCACCACCATCATCACCACCACCATCATCACCACCACCACTGCCATCATCATCACCACCACTGTGCCATCATCATCACCACCACTGTCATTATCACCACCACCATCATCACCAACACCACTGCCATCGTCATCACCACCACTGTCATTATCACCACCACCATCACCAACATCACCACCACCATTATCACCACCATCAACACCACCACCCCCATCATCATCATCACTACTACCATCATTACCAGCACCACCACCACTATCACCACCACCACCACAATCACCATCACCACTATCATCAACATCATCACTACCACCATCACCAACACCACCATCATTATCACCACCACCACCATCACCAACATCACCACCATCATCATCACCACCATCACCAAGACCATCATCATCACCATCACCACCAACATCACCACCATCACCAACACCACCATCACCACCACCACCACCATCATCACCACCACCACCATCATCATCACCACCACCGCCATCATCATCGCCACCACCATGACCACCACCATCACAACCATCACCACCATCACAACCACCATCATCACTATCGCTATCACCACCATCACCATTACCACCACCATTACTACAACCATGACCATCACCACCATCACCACCACCATCACAACGATCACCATCACAGCCACCATCATCACCACCACCACCACCACCATCACCATCAAACCATCGGCATTATTATTTTTTTAGAATTTTGTTGGGATTCAGTATCTGCCAAGATACCCATTCTTAAAACATGAAAAAGCAGCTGACCCTCCTGTGGCCCCCTTTTTGGGCAGTCATTGCAGGACCTCATCCCCAAGCAGCAGCTCTGGTGGCATACAGGCAACCCACCACCAAGGTAGAGGGTAATTGAGCAGAAAAGCCACTTCCTCCAGCAGTTCCCTGTCTGAGCTGCTGTCCTTGGACTTGAAGAAGCTTCTGGAACATGCTGGGGAGGAAGGAAGACATTTCACTTATTGAGTGGCCTGATGCAGAACAGAGACCCAGCTGGTTCACTCTAGTTCGGACTAAAACTCACCCCTGTCTATAAGCATCAGCCTCGGCAGGATGCATTTCACATTTGTGATCTCATTTAACCTCCACAAAGACCCAGAAGGGTTGGTAACATTATCATACCTAGGCCTACTATTTTAAAAATCTAACACCCATGCAGCCCGGGCACTGAAGTGGAGGCTGGCCACGGAGAGAGCCAGGCAATCACTGGCTTTTCCTTAGACAGAGAGCTGGTTCCTAGGAGAAGAAGCTCCAGGCTGGGGTCCAGGCTATGACCCAACTGTTCAGTTTTGCAACATCCAGCATGGCTGCCTGATCAGGGGTGCATATGTCAGAGGAGCCTTCAGCTGGGAAGTGCTGACAAATGACCCAGACCTGACCTGCCCGATGCCAAGGCCTCCTTTAGTACATCCCATGGAGGACACTTGAGACAAAGTCACAGCTCAGCCCGTTGATTTCCCATGCTCTGACTGTGCGGTGCAGCAGGACCCCTAGCAGGCAGCATGTGTTCAAGGCAGCGATATCCAAATGCTATGAATTGCTGTCCTGATGGTTATTTTCCTGCATACAGTAGAGCTGATCC",DPSCompressorFile.decodeBinTree(DPSDynamicOperationsFile.getDPS()));


    }

    @Test
    public void testDPSOperationsReplace () {


        DynamicOperationsDPS DPSDynamicOperationsShort = new DynamicOperationsDPS(DPSEncodedShort,DPSCompressorShort);
        DynamicOperationsDPS DPSDynamicOperationsLong = new DynamicOperationsDPS(DPSEncodedLong,DPSCompressorLong);
        DynamicOperationsDPS DPSDynamicOperationsFile = new DynamicOperationsDPS(DPSEncodedFile,DPSCompressorFile);

        DPSDynamicOperationsShort.replace(1,(char) 'b');
        DPSDynamicOperationsLong.replace(1,(char) 'b');
        DPSDynamicOperationsFile.replace(1,(char) 'G');
        DPSDynamicOperationsShort.replace(3,(char) 'c');//TODO: fix this!
        DPSDynamicOperationsLong.replace(3,(char) 'c');
        DPSDynamicOperationsFile.replace(3,(char) 'A');//TODO: fix this!
        DPSDynamicOperationsShort.replace(7,(char) 'a');
        DPSDynamicOperationsLong.replace(7,(char) 'a');//TODO: fix this!
        DPSDynamicOperationsFile.replace(7,(char) 'A');//TODO: fix this!

        assertEquals("bbccaababc",DPSCompressorShort.decodeBinTree(DPSDynamicOperationsShort.getDPS()));
        assertEquals("abaccbcacbcababacbbabcacbacbabcbaabcbabcaabcabcabcbcbcbcbcccabababaabcbcbcbcbacacabacbacbacccccccababaaaaaabbcbcbcbababc",DPSCompressorLong.decodeBinTree(DPSDynamicOperationsLong.getDPS()));
        assertEquals("GGTAAATAAGG",DPSCompressorFile.decodeBinTree(DPSDynamicOperationsFile.getDPS()).substring(0,11));

    }
    @Test
    public void testDPSOperationsDelete () {

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
