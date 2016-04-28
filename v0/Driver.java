

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
    public static void main(String[] args) {

        boolean debugDPS = true;
        boolean debugKMP = false;
        boolean debugNaive = false;
        boolean debugCmpTime = false;
        boolean debugEncodeTime = true;
        boolean decodeS = true;

        //String pathS = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/data/dna.50MB";
        //String pathR = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/DNA_R";
        //String pathSave = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/";

        List<String> Spaths = new ArrayList<>();
        //Spaths.add("data/dna_clean.1mb");
        //Spaths.add("data/dna_clean.5mb");
        //Spaths.add("data/dna_clean.10mb");
        //Spaths.add("data/dna_clean.50mb");
        Spaths.add("data/dna_clean.100mb");
        //Spaths.add("data/dna_clean.200mb");
        //Spaths.add("data/dna_clean.400mb");

        List<String> refs = new ArrayList<>();
        //refs.add("data/R2000.txt");
        //refs.add("data/R4500.txt");
        refs.add("data/R9990.txt");
        //refs.add("data/R20000.txt");
        //refs.add("data/R29850.txt");
        //refs.add("data/R49750.txt");

        List<String> comps = new ArrayList<>();
        //comps.add("Naive");
        //comps.add("Suffix");
        comps.add("DPS");

        List<String> dops = new ArrayList<>();
        //dops.add("Naive");
        //dops.add("KMP");
        dops.add("DPS");

        FileHandler f = new FileHandler();
        for (String cmp : comps) {
            for(String ref : refs) {
                for (String Spath : Spaths) {
                    String R = null, S = null;
                    try {
                        S = f.readFromFileLine(Spath);
                        R = f.readFromFileLine(ref);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("============================ TEST! ============================\n" +
                            "Parameters: |S| = " + S.length() + "\t|R| = " + (R.length()-1) + "\t Compressor = " + cmp );
                    System.out.println("---------------------------------------------------------------");
                    System.out.println();
                    //Creating the compressor
                    Compressor compressor;
                    switch (cmp) {
                        case "Naive":
                            long tc00 = System.nanoTime();
                            compressor = new CompressorNaive(R);
                            long timec00 = System.nanoTime() - tc00;
                            if(debugCmpTime) System.out.println("Created naive-compressor in\t\t" + timec00 / 1000000000.0 + " seconds");
                            break;
                        case "Suffix":
                            long tc01 = System.nanoTime();
                            compressor = new CompressorSuffix(R);
                            long timec01 = System.nanoTime() - tc01;
                            if(debugCmpTime) System.out.println("Created suffix-tree in\t\t" + timec01 / 1000000000.0 + " seconds");
                            break;
                        case "DPS":
                            long tc02 = System.nanoTime();
                            compressor = new CompressorDPS(R);
                            long timec02 = System.nanoTime() - tc02;
                            if(debugCmpTime) System.out.println("Created suffix-tree in\t\t" + timec02 / 1000000000.0 + " seconds");
                            break;
                        default:
                            throw new IllegalArgumentException("Wrong compressor!");
                    }

                    //Encode
                    ArrayList<Block> cp;
                    long t1 = System.nanoTime();
                    cp = compressor.encode(S);
                    long time1 = System.nanoTime() - t1;
                    if(debugEncodeTime) System.out.println("Encoded string in\t\t\t\t" + time1 / 1000000000.0 + " seconds");

                    int totalLen = 10000;
                    int steps = 100;
                    for(String dopString : dops) {
                        DynamicOperations dop;
                        switch (dopString) {
                            case "DPS":
                                //Create DynamicOperationsDPS
                                long t00 = System.nanoTime();
                                DynamicOperationsDPS dopDPS = new DynamicOperationsDPS(cp, compressor);
                                long time00 = System.nanoTime() - t00;
                                if(debugDPS) System.out.println("Created DPS structure in\t\t" + time00 / 1000000000.0 + " seconds");
                                if(decodeS) {
                                    //Decode DPS
                                    String decoded0;
                                    BinaryTree bt = dopDPS.getDPS();
                                    long t01 = System.nanoTime();
                                    decoded0 = compressor.decodeBinTree(bt);
                                    long time01 = System.nanoTime() - t01;
                                    if (debugDPS) {
                                        System.out.println("Decoded (DPS) string in\t\t\t" + time01 / 1000000000.0 + " seconds");
                                        System.out.println(decoded0.equals(S) ? "OK (DPS)" : "FAILED (DPS)");
                                    }
                                }
                                dop = dopDPS;
                                break;
                            case "KMP":
                                //Create DynamicOperationsKMP
                                long t10 = System.nanoTime();
                                DynamicOperationsKMP dopKMP = new DynamicOperationsKMP(cp, compressor);
                                long time10 = System.nanoTime() - t10;
                                if(debugKMP) System.out.println("Created DPS structure in\t\t" + time10 / 1000000000.0 + " seconds");
                                if(decodeS) {
                                    //Decode KMP
                                    String decoded1;
                                    ArrayList compressionList = dopKMP.getC();
                                    long t11 = System.nanoTime();
                                    decoded1 = compressor.decodeArrayList(compressionList);
                                    long time11 = System.nanoTime() - t11;
                                    if (debugKMP) {
                                        System.out.println("Decoded (Naive) string in\t\t" + time11 / 1000000000.0 + " seconds");
                                        System.out.println(decoded1.equals(S) ? "OK (KMP)" : "FAILED (KMP)");
                                    }
                                }
                                dop = dopKMP;
                                break;
                            case "Naive":
                                //Create DynamicOperationsNaive
                                long t20 = System.nanoTime();
                                DynamicOperationsNaive dopNaive = new DynamicOperationsNaive(cp, compressor);
                                long time20 = System.nanoTime() - t20;
                                if(debugNaive) System.out.println("Created Naive structure in\t\t" + time20 / 1000000000.0 + " seconds");
                                if(decodeS) {
                                    //Decode Naive
                                    String decoded2;
                                    ArrayList cList = dopNaive.getC();
                                    long t21 = System.nanoTime();
                                    decoded2 = compressor.decodeArrayList(cList);
                                    long time21 = System.nanoTime() - t21;
                                    if (debugNaive) {
                                        System.out.println("Decoded (Naive) string in\t\t" + time21 / 1000000000.0 + " seconds");
                                        System.out.println(decoded2.equals(S) ? "OK (Naive)" : "FAILED (Naive)");
                                    }
                                }
                                dop = dopNaive;
                                break;
                            default:
                                throw new IllegalArgumentException("Wrong DOP!");
                        }

                        System.out.println(
                                "DynamicOperation = " + dopString  +
                                        " \t Opertation = " + "insert " +
                                        " \t Length = " + totalLen +
                                        " \t Steps = " + steps +
                                        " \t Timescale = Seconds(per " + steps + " operation)" +
                                        " \t Data: \n");
                        TimeTest test = new TimeTest(dop);
                        System.out.println(Arrays.toString(test.insertCharactersSingleBatch(S.length(), totalLen, steps)));
                        System.out.println("\n");

                    }
                }
            }
        }
    }
}