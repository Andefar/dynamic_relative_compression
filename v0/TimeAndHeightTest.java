/**
 * Created by Josefinetusindfryd on 31/03/16.
 */
public class TimeAndHeightTest {

    RunningTime rt1;
    RunningTime rt2;
    DynamicOperationsDPS dop1;
    DynamicOperations dop2;

    TimeAndHeightTest(DynamicOperationsDPS dop1, DynamicOperations dop2){
        this.dop1 = dop1;
        this.dop2 = dop2;
        this.rt1 = new RunningTime(dop1);
        this.rt2 = new RunningTime(dop2);
    }


    // Inserts the first l characters of S one character at a time at random indices in two different dynamic operations
    // Initially used to compare KMP and suffix tree implementation of the reference string R
    // Returns the height of the resulting binary compression binary tree and the insertion time for each dynamic operation
    public long[] compareInsertCharactersSingle(String S, int l){
        int insertIndex;
        int SLength = S.length();
        String insertPartOfS = S.substring(0, l);
        char c;
        long CpuTimeStart;
        long insertTime1 = 0;
        long insertTime2 = 0;
        int insertNumber = 0;

        for (int i = 0; i < l; i++){
            insertIndex = (int) (Math.random() * (double) (SLength + insertNumber));
            c = insertPartOfS.charAt(i);
            CpuTimeStart= this.rt1.getCpuTime();
            this.dop1.insert(insertIndex, c);
            insertTime1 += (this.rt1.getCpuTime() - CpuTimeStart);

            CpuTimeStart= this.rt2.getCpuTime();
            this.dop2.insert(insertIndex, c);
            insertTime2 += (this.rt2.getCpuTime() - CpuTimeStart);

            insertNumber += 1;

        }

        return new long[] {(long) this.dop1.getDPS().getMaxHeight(), insertTime1, insertTime2};
    }

    // Deletes l characters in S one character at a time at random indices in two different dynamic operations
    // Initially used to compare KMP and suffix tree implementation of the reference string R
    // Returns the height of the resulting binary compression binary tree and the deleteion time for each dynamic operation
    public long[] compareDeleteCharactersSingle(String S, int l){
        int deleteIndex;
        int SLength = S.length();
        int deleteNumber= 0;
        char c;
        long CpuTimeStart;
        long deleteTime1 = 0;
        long deleteTime2 = 0;

        for (int i = 0; i < l; i++){
            deleteIndex = (int) (Math.random() * (double) (SLength - deleteNumber));
            CpuTimeStart= this.rt1.getCpuTime();
            this.dop1.delete(deleteIndex);
            deleteTime1 += (this.rt1.getCpuTime() - CpuTimeStart);

            CpuTimeStart= this.rt2.getCpuTime();
            this.dop2.delete(deleteIndex);
            deleteTime2 += (this.rt2.getCpuTime() - CpuTimeStart);

            deleteNumber += 1;

        }

        return new long[] {(long) this.dop1.getDPS().getMaxHeight(), deleteTime1, deleteTime2};
    }

    // Replaces l characters of S with the first l characters of S one character at a time at random indices in two different dynamic operations
    // Initially used to compare KMP and suffix tree implementation of the reference string R
    // Returns the height of the resulting binary compression binary tree and the replace time for each dynamic operation
    public long[] compareReplaceCharactersSingle(String S, int l){
        int replaceIndex;
        int SLength = S.length();
        String replacePartOfS = S.substring(0, l);
        char c;
        long CpuTimeStart;
        long replaceTime1 = 0;
        long replaceTime2 = 0;

        for (int i = 0; i < l; i++){
            replaceIndex = (int) (Math.random() * (double) SLength);
            c = replacePartOfS.charAt(i);
            CpuTimeStart= this.rt1.getCpuTime();
            this.dop1.replace(replaceIndex, c);
            replaceTime1 += (this.rt1.getCpuTime() - CpuTimeStart);

            CpuTimeStart= this.rt2.getCpuTime();
            this.dop2.replace(replaceIndex, c);
            replaceTime2 += (this.rt2.getCpuTime() - CpuTimeStart);

        }

        return new long[] {(long) this.dop1.getDPS().getMaxHeight(), replaceTime1, replaceTime2};
    }


    // Inserts the first l characters of S one character at a time at random indices
    // Returns the height of the resulting binary compression binary tree
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
            CpuTimeStart= this.rt1.getCpuTime();
            this.dop1.insert(insertIndex, c);
            insertTime += (this.rt1.getCpuTime() - CpuTimeStart);
        }

        return new long[] {(long) this.dop1.getDPS().getMaxHeight(), insertTime};
    }

    // Inserts the first l characters of S in blocks of length blockSize at random indices
    // Returns the height of the resulting binary compression binary tree
    public long[] insertCharactersBlock(String S, int l, int blockSize) {
        int insertIndex;
        int SLength = S.length();
        String insertPartOfS = S.substring(0, l);
        char c;
        long CpuTimeStart;
        long insertTime = 0;
        int remainingLength = l;

        // Insert block of blockSize characters until all of the substring is inserted
        while (remainingLength > 0) {
            insertIndex = (int) (Math.random() * (double) (SLength - blockSize));

            if (remainingLength <= blockSize) {
                for (int i = 0; i < remainingLength; i++) {
                    c = insertPartOfS.charAt((l - remainingLength) + i);
                    CpuTimeStart = this.rt1.getCpuTime();
                    dop1.insert(insertIndex + i, c);
                    insertTime += (this.rt1.getCpuTime() - CpuTimeStart);
                }
                remainingLength = 0;
            } else {
                for (int i = 0; i < blockSize; i++) {
                    c = insertPartOfS.charAt((l - remainingLength) + i);
                    CpuTimeStart = this.rt1.getCpuTime();
                    dop1.insert(insertIndex + i, c);
                    insertTime += (this.rt1.getCpuTime() - CpuTimeStart);
                }
                remainingLength = remainingLength - blockSize;
            }
        }
        return new long[] {(long) this.dop1.getDPS().getMaxHeight(), insertTime};
    }

    // Inserts the first l characters of S in blocks of length 1-blockSize at random indices in two DynamicOperations
    // Initially used to compare KMP and suffix tree implementation of the reference string R
    // Returns the height of the resulting binary compression binary tree and the insertion time of each of the DynamicOperations
    public long[] compareInsertCharactersBlock(String S, int l, int blockSize) {
        int insertIndex;
        int SLength = S.length();
        String insertPartOfS = S.substring(0, l);
        char c;
        long CpuTimeStart;
        long insertTime1 = 0;
        long insertTime2 = 0;
        int remainingLength = l;

        // Insert block of blockSize characters until all of the substring is inserted
        while (remainingLength > 0) {
            insertIndex = (int) (Math.random() * (double) ((SLength + (l-remainingLength) ) -  blockSize));

            if (remainingLength <= blockSize) {
                for (int i = 0; i < remainingLength; i++) {
                    c = insertPartOfS.charAt((l - remainingLength) + i);

                    CpuTimeStart = this.rt1.getCpuTime();
                    dop1.insert(insertIndex + i, c);
                    insertTime1 += (this.rt1.getCpuTime() - CpuTimeStart);

                    CpuTimeStart = this.rt2.getCpuTime();
                    dop2.insert(insertIndex + i, c);
                    insertTime2 += (this.rt2.getCpuTime() - CpuTimeStart);
                }
                remainingLength = 0;
            } else {
                for (int i = 0; i < blockSize; i++) {
                    c = insertPartOfS.charAt((l - remainingLength) + i);

                    CpuTimeStart = this.rt1.getCpuTime();
                    dop1.insert(insertIndex + i, c);
                    insertTime1 += (this.rt1.getCpuTime() - CpuTimeStart);

                    CpuTimeStart = this.rt2.getCpuTime();
                    dop2.insert(insertIndex + i, c);
                    insertTime2 += (this.rt2.getCpuTime() - CpuTimeStart);
                }
                remainingLength = remainingLength - blockSize;
            }
        }
        return new long[] {(long) this.dop1.getDPS().getMaxHeight(), insertTime1, insertTime2};
    }

    // Deletes l characters of S in blocks of length blockSize at random indices in two DynamicOperations
    // Initially used to compare KMP and suffix tree implementation of the reference string R
    // Returns the height of the resulting binary compression binary tree and the deletion time of each of the DynamicOperations
    public long[] compareDeleteCharactersBlock(String S, int l, int blockSize) {
        int deleteIndex;
        int SLength = S.length();
        char c;
        long CpuTimeStart;
        long deleteTime1 = 0;
        long deleteTime2 = 0;
        int remainingLength = l;

        // delete block of blockSize characters until all of the substring is deleteed
        while (remainingLength > 0) {
            deleteIndex = (int) (Math.random() * (double) ((SLength - (l-remainingLength)) - blockSize));

            if (remainingLength <= blockSize) {
                for (int i = 0; i < remainingLength; i++) {

                    CpuTimeStart = this.rt1.getCpuTime();
                    dop1.delete(deleteIndex + i);
                    deleteTime1 += (this.rt1.getCpuTime() - CpuTimeStart);

                    CpuTimeStart = this.rt2.getCpuTime();
                    dop2.delete(deleteIndex + i);
                    deleteTime2 += (this.rt2.getCpuTime() - CpuTimeStart);
                }
                remainingLength = 0;
            } else {
                //deleteNumber = (int) (Math.random() * blockSize);
                for (int i = 0; i < blockSize; i++) {

                    CpuTimeStart = this.rt1.getCpuTime();
                    dop1.delete(deleteIndex + i);
                    deleteTime1 += (this.rt1.getCpuTime() - CpuTimeStart);

                    CpuTimeStart = this.rt2.getCpuTime();
                    dop2.delete(deleteIndex + i);
                    deleteTime2 += (this.rt2.getCpuTime() - CpuTimeStart);
                }
                remainingLength = remainingLength - blockSize;
            }
        }
        return new long[] {(long) this.dop1.getDPS().getMaxHeight(), deleteTime1, deleteTime2};
    }

    // Replaces the l characters at random indices in two DynamicOperations with first l characters of S in blocks of length blockSize
    // Initially used to compare KMP and suffix tree implementation of the reference string R
    // Returns the height of the resulting binary compression binary tree and the replace time of each of the DynamicOperations
    public long[] compareReplaceCharactersBlock(String S, int l, int blockSize) {
        int replaceIndex;
        int SLength = S.length();
        String replacePartOfS = S.substring(0, l);
        char c;
        long CpuTimeStart;
        long replaceTime1 = 0;
        long replaceTime2 = 0;
        int remainingLength = l;

        // replace block of blockSize characters until all of the substring is replaced
        while (remainingLength > 0) {
            replaceIndex = (int) (Math.random() * (double) (SLength - blockSize));

            if (remainingLength <= blockSize) {
                for (int i = 0; i < remainingLength; i++) {
                    c = replacePartOfS.charAt((l - remainingLength) + i);

                    CpuTimeStart = this.rt1.getCpuTime();
                    dop1.replace(replaceIndex + i, c);
                    replaceTime1 += (this.rt1.getCpuTime() - CpuTimeStart);

                    CpuTimeStart = this.rt2.getCpuTime();
                    dop2.replace(replaceIndex + i, c);
                    replaceTime2 += (this.rt2.getCpuTime() - CpuTimeStart);
                }
                remainingLength = 0;
            } else {
                for (int i = 0; i < blockSize; i++) {
                    c = replacePartOfS.charAt((l - remainingLength) + i);

                    CpuTimeStart = this.rt1.getCpuTime();
                    dop1.replace(replaceIndex + i, c);
                    replaceTime1 += (this.rt1.getCpuTime() - CpuTimeStart);

                    CpuTimeStart = this.rt2.getCpuTime();
                    dop2.replace(replaceIndex + i, c);
                    replaceTime2 += (this.rt2.getCpuTime() - CpuTimeStart);
                }
                remainingLength = remainingLength - blockSize;
            }
        }
        return new long[] {(long) this.dop1.getDPS().getMaxHeight(), replaceTime1, replaceTime2};
    }


}
