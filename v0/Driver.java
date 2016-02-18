import java.util.ArrayList;
import java.util.Arrays;

import java.lang.management.*;



public class Driver {

    /** Get CPU time in nanoseconds. */
    public static long getCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadCpuTime( ) : 0L;
    }

    /** Get user time in nanoseconds. */
    public static long getUserTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadUserTime( ) : 0L;
    }

    public static void main(String[] args) {

//        long suffixTreeTimeStart = System.nanoTime();
//        SuffixTree test = new SuffixTree("bananada");
//        long suffixTreeTimeEnd  = System.nanoTime() - suffixTreeTimeStart;

        long suffixTreeCpuTimeStart = getCpuTime( );
        CompressorSuffix suf1 = new CompressorSuffix("atcgacgtatcgtactgactatatatcgcgctcagtcagtacgtactcatatatcgcggcgcctatagtcgtacgtcagtca");
        long suffixTreeCpuTimeEnd = getCpuTime() - suffixTreeCpuTimeStart;
        long suffixTreeTimeStart = System.nanoTime();
        CompressorSuffix suf2 = new CompressorSuffix("atcgacgtatcgtactgactatatatcgcgctcagtcagtacgtactcatatatcgcggcgcctatagtcgtacgtcagtca");
        long suffixTreeTimeEnd  = System.nanoTime() - suffixTreeTimeStart;


        ArrayList<Block> t1;
        ArrayList<Block> t2;
        long encodeCpuTimeStart = getCpuTime();
        t1 = suf1.encode("tcatacttactactatcatcgacgtattatacggacgatgctgcgcgacgcagcgagcatattatatatatatcgttatatcatggtacgtcaatctatcgagcta");
        long encodeCpuTimeEnd = getCpuTime() - encodeCpuTimeStart;
        long encodeTimeStart = System.nanoTime();
        t2 = suf2.encode("tcatacttactactatcatcgacgtattatacggacgatgctgcgcgacgcagcgagcatattatatatatatcgttatatcatggtacgtcaatctatcgagcta");
        long encodeTimeEnd = System.nanoTime() - encodeTimeStart;

        long decodeCpuTimeStart = getCpuTime();
        suf1.decode(t1);
        long decodeCpuTimeEnd = getCpuTime() - decodeCpuTimeStart;
        long decodeTimeStart = System.nanoTime();
        suf2.decode(t2);
        long decodeTimeEnd = System.nanoTime() - decodeTimeStart;


        System.out.println("WALLCLOCK TIMES");
        System.out.println("Suffix tree construction: " + suffixTreeTimeEnd/(1000000000.0));
        System.out.println("encode: " + encodeTimeEnd/(1000000000.0));
        System.out.println("decode: " + decodeTimeEnd/(1000000000.0));

        System.out.println();

        System.out.println("CPU TIMES");
        System.out.println("Suffix tree construction: " + suffixTreeCpuTimeEnd/(1000000000.0));
        System.out.println("encode: " + encodeCpuTimeEnd/(1000000000.0));
        System.out.println("decode: " + decodeCpuTimeEnd/(1000000000.0));

    }






}