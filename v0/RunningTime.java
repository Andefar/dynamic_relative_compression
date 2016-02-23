import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.management.*;

/**
 * Created by Josefinetusindfryd on 23/02/16.
 */

public class RunningTime {

    protected FileHandler f;
    protected String S, R;
    protected DynamicOperations dop;

    public RunningTime(String pathS, String pathR, DynamicOperations dop) throws IOException {
        try{
            this.f = new FileHandler();
            this.S = this.f.readFromFileLine(pathS);
            this.R = this.f.readFromFileLine(pathR);
            this.dop = dop;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public long[] runTimeAccess(int i) {

        long CpuTimeStart = getCpuTime();
        this.dop.access(i);
        long CpuTimeEnd = getCpuTime() - CpuTimeStart;
        long TimeStart = System.nanoTime();
        this.dop.access(i);
        long TimeEnd = System.nanoTime() - TimeStart;

        return new long[]{CpuTimeEnd, TimeEnd};
    }

    public long[] runTimeDelete(int i) {

        long CpuTimeStart = getCpuTime();
        this.dop.delete(i);
        long CpuTimeEnd = getCpuTime() - CpuTimeStart;
        long TimeStart = System.nanoTime();
        this.dop.delete(i);
        long TimeEnd = System.nanoTime() - TimeStart;

        return new long[]{CpuTimeEnd, TimeEnd};
    }

    public long[] runTimeUpdate(int i, int value) {

        long CpuTimeStart = getCpuTime();
        this.dop.replace(i, value);
        long CpuTimeEnd = getCpuTime() - CpuTimeStart;
        long TimeStart = System.nanoTime();
        this.dop.delete(i);
        long TimeEnd = System.nanoTime() - TimeStart;

        return new long[]{CpuTimeEnd, TimeEnd};
    }




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

}
