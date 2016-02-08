
import java.util.Arrays;


//Initial version of string compression - naive
public class First {

	//O(|S|*|R|^2) :Iterate through string S and foreach character use indexOf
	//O(|S|^2*|R|) :Iterate through string S and foreach character use indexOf
	public static void encode(String S, String R){
		char[] SA = S.toCharArray(); 
		char[] RA = R.toCharArray();


		int indexR = -1; 
		int indexTemp = -1; 
		int counter = 0; 
		char c = SA[counter];
		char[] C = {};
		
		//O(|S|) : every char is considered in S
		while (c != '$'){
			indexTemp = indexR; 
			C = Arrays.copyOf(C, C.length+1);
			C[C.length-1] = c; 
			
			indexR = indexOf(C,RA);
			
			if (indexR == -1 || c == '$'){
				if (C.length == 1){
					System.out.println("("+ c + ")");
					C = new char[0];
					counter += 1;
					c = SA[counter];
				} else {
					System.out.println("(" + indexTemp + "," + (C.length - 1) + ")");
					C = new char[0]; 
				}
			} else {
				counter += 1; 
				c = SA[counter]; 
				if (c == '$'){
					System.out.println("(" + indexR + "," + (C.length) + ")");
				}
			}
			
		}
	}
	
// Return index of beginning of C in R. If not found return -1
// O(|R|^2) : C might be the length of R
	public static int indexOf(char[] C, char[] R){
		int index = -1; 
		
		for (int i = 0; i < R.length; i++){
			
			for (int j = 0; j < C.length; j++){
				
				if (i+j < R.length && C[j] != R[i+j] ){
					break; 
				} else if (j == C.length -1){
					index = i;
				}
			}
			if (index != -1){ return index;}; 
		}
		return -1; 
	}
	
    public static void main(String[] args) {
    	String C = "acbcbbcabbababcbsacbababaaaabcbbebcbcccabbabcababbabgaaabsacbcaaadsabbbbccccbabaafbacbcabbbcbcacbbabca$";
    	String R = "aaabbbcccabc$";
        encode(C,R);
    }

}