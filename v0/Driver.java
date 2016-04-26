

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
    public static void main(String[] args) {

        //String pathS = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/data/dna.50MB";
        //String pathR = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/DNA_R";
        //String pathSave = "/Users/JosefineTusindfryd/Desktop/dynamic_relative_compression/";

        List<String> Spaths = new ArrayList<>();
        Spaths.add("/Users/AndreasLauritzen/dynamic_relative_compression/dna_clean.1mb");
        Spaths.add("/Users/AndreasLauritzen/dynamic_relative_compression/dna_clean.5mb");
        Spaths.add("/Users/AndreasLauritzen/dynamic_relative_compression/dna_clean.10mb");
        Spaths.add("/Users/AndreasLauritzen/dynamic_relative_compression/dna_clean.50mb");
        Spaths.add("/Users/AndreasLauritzen/dynamic_relative_compression/dna_clean.100mb");
        Spaths.add("/Users/AndreasLauritzen/dynamic_relative_compression/dna_clean.200mb");
        Spaths.add("/Users/AndreasLauritzen/dynamic_relative_compression/dna_clean.400mb");

        List<String> refs = new ArrayList<>();
        refs.add("/Users/AndreasLauritzen/dynamic_relative_compression/R2000.txt");
        refs.add("/Users/AndreasLauritzen/dynamic_relative_compression/R4500.txt");
        refs.add("/Users/AndreasLauritzen/dynamic_relative_compression/R9990.txt");
        refs.add("/Users/AndreasLauritzen/dynamic_relative_compression/R20000.txt");
        refs.add("/Users/AndreasLauritzen/dynamic_relative_compression/R29850.txt");
        refs.add("/Users/AndreasLauritzen/dynamic_relative_compression/R49750.txt");

        List<String> comps = new ArrayList<>();
        comps.add("Naive");
        comps.add("Suffix");
        comps.add("DPS");

        List<String> dops = new ArrayList<>();
        dops.add("Naive");
        dops.add("KMP");
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
                            //long t00 = System.nanoTime();
                            compressor = new CompressorNaive(R);
                            //long time00 = System.nanoTime() - t00;
                            //System.out.println("Created naive-compressor in\t\t" + time00 / 1000000000.0 + " seconds");
                            break;
                        case "Suffix":
                            //long t01 = System.nanoTime();
                            compressor = new CompressorSuffix(R);
                            //long time01 = System.nanoTime() - t01;
                            //System.out.println("Created suffix-tree in\t\t" + time01 / 1000000000.0 + " seconds");
                            break;
                        case "DPS":
                            //long t02 = System.nanoTime();
                            compressor = new CompressorDPS(R);
                            //long time02 = System.nanoTime() - t02;
                            //System.out.println("Created suffix-tree in\t\t" + time02 / 1000000000.0 + " seconds");
                            break;
                        default:
                            throw new IllegalArgumentException("Wrong compressor!");
                    }

                    //Encode
                    ArrayList<Block> cp;
                    //long t1 = System.nanoTime();
                    cp = compressor.encode(S);
                    //long time1 = System.nanoTime() - t1;
                    //System.out.println("Encoded string in\t\t\t\t" + time1 / 1000000000.0 + " seconds");

                    int totalLen = 10000;
                    int steps = 100;
                    for(String dopString : dops) {
                        DynamicOperations dop;
                        switch (dopString) {
                            case "DPS":
                                //Create DynamicOperationsDPS
                                //long t20 = System.nanoTime();
                                dop = new DynamicOperationsDPS(cp, compressor);
                                //long time20 = System.nanoTime() - t20;
                                //System.out.println("Created DPS structure in\t\t" + time20 / 1000000000.0 + " seconds");
                                break;
                            case "KMP":
                                //Create DynamicOperationsKMP
                                //long t20 = System.nanoTime();
                                dop = new DynamicOperationsKMP(cp, compressor);
                                //long time20 = System.nanoTime() - t20;
                                //System.out.println("Created DPS structure in\t\t" + time20 / 1000000000.0 + " seconds");
                                break;
                            case "Naive":
                                //Create DynamicOperationsNaive
                                //long t21 = System.nanoTime();
                                dop = new DynamicOperationsNaive(cp, compressor);
                                //long time21 = System.nanoTime() - t21;
                                //System.out.println("Created Naive structure in\t\t" + time21 / 1000000000.0 + " seconds");
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
                        System.out.println();
                    }

                    /*
                    //Decode Naive
                    String decoded0;
                    ArrayList cList = dop.getC();
                    long t30 = System.nanoTime();
                    decoded0 = compressor.decodeArrayList(cList);
                    long time30 = System.nanoTime() - t30;
                    System.out.println("Decoded (Naive) string in\t\t" + time30 / 1000000000.0 + " seconds");

                    //Decode KMP
                    String decoded1;
                    ArrayList compressionList = dop1.getC();
                    long t31 = System.nanoTime();
                    decoded1 = compressor.decodeArrayList(compressionList);
                    long time31 = System.nanoTime() - t31;
                    System.out.println("Decoded (Naive) string in\t\t" + time31 / 1000000000.0 + " seconds");

                    //Decode DPS
                    String decoded2;
                    BinaryTree bt = dop2.getDPS();
                    long t32 = System.nanoTime();
                    decoded2 = compressor.decodeBinTree(bt);
                    long time32 = System.nanoTime() - t32;
                    System.out.println("Decoded (DPS) string in\t\t\t" + time32 / 1000000000.0 + " seconds");



                    //Check the decompression was equal to the source string
                    System.out.println(decoded0.equals(S) ? "OK (Naive)" : "FAILED (Naive)");
                    System.out.println(decoded1.equals(S) ? "OK (KMP)" : "FAILED (KMP)");
                    System.out.println(decoded2.equals(S) ? "OK (DPS)" : "FAILED (DPS)");
                    System.out.println("\n");
                    */

                }
            }
        }
    }
}