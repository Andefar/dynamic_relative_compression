/**
 * Created by Josefinetusindfryd on 31/03/16.
 */
public class TimeAndHeightTest {

    RunningTime rt;
    DynamicOperationsDPS dop;

    TimeAndHeightTest(DynamicOperationsDPS dop){
        this.dop = dop;
        this.rt = new RunningTime(dop);
    }

    public long[] insertCharactersSingle(String S, int l){
        int insertIndex;
        int SLength = S.length();
        String insertPartOfS = S.substring(0, l);
        char c;
        long CpuTimeStart;
        long insertTime = 0;

        for (int i = 0; i < l; i++){
            insertIndex = (int) (Math.random() * (double) SLength);
            c = insertPartOfS.charAt(i);
            CpuTimeStart= this.rt.getCpuTime();
            this.dop.insert(insertIndex, c);
            insertTime += (rt.getCpuTime() - CpuTimeStart);
        }

        return new long[] {(long) this.dop.getDPS().getMaxHeight(), insertTime};
    }

}
