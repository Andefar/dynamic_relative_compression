import javax.swing.plaf.SliderUI;

/**
 * Created by Josefinetusindfryd on 31/03/16.
 */
public class TimeTest {

    RunningTime rt;
    DynamicOperations dop;

    TimeTest(DynamicOperations dop){
        this.dop = dop;
        this.rt = new RunningTime(dop);
    }


    // Inserts the first l characters of S one character at a time at random indices
    // Returns the height of the resulting binary compression binary tree
    public long insertCharactersSingle(String S, int l){
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
            insertTime += (this.rt.getCpuTime() - CpuTimeStart);
        }
        return insertTime;
    }

    // Inserts the first l characters of S in blocks of length 1-blockSize at random indices
    // Returns the height of the resulting binary compression binary tree
    public long insertCharactersBlock(String S, int l, int blockSize) {
        int insertIndex;
        int SLength = S.length();
        String insertPartOfS = S.substring(0, l);
        char c;
        long CpuTimeStart;
        long insertTime = 0;
        int remainingLength = l;
        int insertNumber;


        // Insert block of between 1 and blockSize characters until all of the substring is inserted

        while (remainingLength > 0) {
            insertIndex = (int) (Math.random() * (double) (SLength - blockSize));

            if (remainingLength <= blockSize) {
                for (int i = 0; i < remainingLength; i++) {
                    c = insertPartOfS.charAt((l - remainingLength) + i);
                    CpuTimeStart = this.rt.getCpuTime();
                    dop.insert(insertIndex + i, c);
                    insertTime += (this.rt.getCpuTime() - CpuTimeStart);
                }
                remainingLength = 0;
            } else {
                insertNumber = (int) (Math.random() * blockSize);
                for (int i = 0; i < insertNumber; i++) {
                    c = insertPartOfS.charAt((l - remainingLength) + i);
                    CpuTimeStart = this.rt.getCpuTime();
                    dop.insert(insertIndex + i, c);
                    insertTime += (this.rt.getCpuTime() - CpuTimeStart);
                }
                remainingLength = remainingLength - insertNumber;
            }
        }
        return insertTime;
    }


    public double[] insertCharactersSingleBatch(int SLength,int l, int steps){
        int insertIndex;
        char[] choose = {'A','G','T','C'};
        char c;
        long CpuTimeStart;
        long intermediateTime = 0;
        double[] output = new double[(l/steps)-1];
        int index = 0;
        for (int i = 0; i < l; i++){

            insertIndex = (int) (Math.random() * ((double) SLength-1));
            c = choose[ (int) (Math.random() * ((double) 4))];
            CpuTimeStart= this.rt.getCpuTime();
            this.dop.insert(insertIndex, c);
            long time = this.rt.getCpuTime();
            intermediateTime += (time - CpuTimeStart);

            if(i != 0 && i % steps == 0 ) {
                output[index] = ( intermediateTime / 1000000000.0);
                intermediateTime = 0;
                index++;
            }
        }
        return output;
    }

    public double[] replaceCharactersSingleBatch(int SLength,int l, int steps){
        int insertIndex;
        char[] choose = {'A','G','T','C'};
        char c;
        long CpuTimeStart;
        long intermediateTime = 0;
        double[] output = new double[l/steps];
        int index = 0;
        for (int i = 0; i < l; i++){

            insertIndex = (int) (Math.random() * ((double) SLength-1));
            c = choose[ (int) (Math.random() * ((double) 4))];
            CpuTimeStart= this.rt.getCpuTime();
            this.dop.replace(insertIndex, c);
            long time = this.rt.getCpuTime();
            intermediateTime += (time - CpuTimeStart);

            if(i % steps == 0 ) {
                output[index] = ( intermediateTime / 1000000000.0);
                intermediateTime = 0;
                index++;
            }
        }
        return output;
    }

    public double[] deleteCharactersSingleBatch(int SLength,int l, int steps){
        int insertIndex;
        long CpuTimeStart;
        int SLengthCur = SLength;
        long intermediateTime = 0;
        double[] output = new double[l/steps];
        int index = 0;
        for (int i = 0; i < l; i++){

            insertIndex = ((int) (Math.random() * (double) (SLengthCur-1)));
            CpuTimeStart= this.rt.getCpuTime();
            this.dop.delete(insertIndex);
            SLengthCur--;
            long time = this.rt.getCpuTime();
            intermediateTime += (time - CpuTimeStart);

            if(i % steps == 0 ) {
                output[index] = ( intermediateTime / 1000000000.0);
                intermediateTime = 0;
                index++;
            }
        }
        return output;
    }
}
