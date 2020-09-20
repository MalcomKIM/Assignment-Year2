import javax.xml.crypto.Data;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class Main{

    public static ArrayList ReadData(String pathname) {
        ArrayList strlist = new ArrayList();
        try {

            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            int j = 0;
            String line = "";
            while ((line = br.readLine()) != null) {
                strlist.add(line);
            }
            return strlist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strlist;
    }

    public static ArrayList DataWash(ArrayList Datalist) {
        ArrayList AIS = new ArrayList();
        ArrayList IS = new ArrayList();
        for (int i = 0; i < Datalist.size(); i++) {
            String Tstr = (String) Datalist.get(i);
            if (Tstr.equals("A") == false) {
                IS.add(Integer.parseInt(Tstr));
            }
            if (Tstr.equals("A")) {
                AIS.add(IS.clone());
                IS.clear();
            }
        }
        return AIS;
    }



//%%%%%%%%%%%%%%%%%%%%%%% Procedure LongestBubble() that will contain your code:
    

    public static int LongestBubble(int[] A, int n){
        /* Input is array of input sequence (a_1 <= a_2 <= ... <= a_n) as A[0,1,...,n-1], that is,
        a_1 = A[0], a_2 = A[1], ..., a_n = A[n-1].
        n = number of integers in sequence A
        This procedure should return the value of the longest bubble (>= 3) or 0 if there is no bubble.
        It should NOT return the respective bubble!
        */


        /* Here should be the description of the main ideas of your solution:
        describe the recursive relation used in your dynamic programming
        solution and outline how you implement it sequentially in your code below.

        SOME NOTATION: s.t. is abbreviation of "such that"
                       a <= b (a is smaller than or equal to b)
                       a >= b (a is greater than or equal to b)
                       max [ a , b ] denotes the larger among a and b
                       Given an array T[1,...,n] 
                       then M = max_{k: some condition C(k) holds} [ T[k] ],
                       M denotes the largest value T[k] over all indices k such that condition C(k) holds.
     	
    Solution:
		Input sequence A= {A[1], A[2],..., A[n]}
		Define I(i) to be the length of the longest increasing subsequence that ends at position i. (1<=i<=n)
		Define D(i) to be the length of the longest decreasing subsequence that starts at position i. (1<=i<=n)
		Suppose A[i] is the biggest number of a Bubble sequence, 
		then the length of this Bubble sequence is L(i) = I(i) + D(i) -1 (1<= i <=n). 
		(-1 because A[i] is counted twice both in I(i) and D(i).)
		Then the solution to the Longest Bubble problem is equal to L = max{L(2), L(3),..., L(n-1)}. 
		L(1) and L(n) cannot be the solution, because A[1] and A[n] are the start and end point of A, 
		which means they cannot be the largest number of any Bubble sequence.
		
		Step 1: compute I(i+1)
		Set I(1)=1, I(2)=1,...,I(n)=1, 
		because the length of the longest increasing sequence start any point is at least 1.
		I(i+1)=1+max_{I(j): A[j] < A[i+1]} (1<=j<=i)
		
		Step 2: compute D(i+1)
		Given an array B[1,...,n] ={A[n], A[n-1],..., A[1]}, B is a reversed array of A.
		then instead of computing the length of the longest decreasing subsequence that starts at position i,
		we can compute  the length of the longest increasing sequence ends at position i, similar to step 1.
		Notice: we should also reverse D(1), D(2),... ,D(n) after we get the results.
		
		Step 3: compute the max L
		Set L=0;
		then L = max_[I(i)+D(i): I(i)>1 and D(i)>1] (2<=i<=n-1)
		We must ensure I(i)>1 and D(i)>1 because A[i] cannot be 
		the start point of a increasing subsequence 
		or the end point of a decreasing subsequence.
		Otherwise there is no Bubble sequence existing.
		
        */

        /* Here should be the statement and description of the running time
        analysis of your implementation: describe how the running time depends on
        n (length of the input sequence), and give short argument.

        1.Compute I(i) 
        Computing I(i+1) requires us to check all of the values A[1], A[2],...,A[i],i.e. O(i) time.
        In total, computing all of the I(i) values takes O(1+2+3+...+n) = O(n^2) time.
        
        2.Compute D(i) 
        Reverse A takes O(n).
        Computing D(i) takes O(n^2), same as computing I(i).
        Reverse D takes O(n).
        
        3.Find the value of L
        L = max_[I(i)+D(i): I(i)>1 and D(i)>1] takes O(n) time.
        
        Thus, finding the length of longest Bubble sequence takes O(n^2).
        */
    	
    	// Here should be the code of your procedure to solve the problem:
    	
		int res=0;		//'res' is used for recording the final result
		int[] B=reverse(A, n);	//array 'B' is the reversed array of 'A'

		int[] incr=new int[n];	//array 'incr' is used to record length of the longest increasing subsequence that ends at position i for 'A'
		int[] decr=new int[n];	//array 'decr' is used to record length of the longest increasing subsequence that ends at position i for 'B'
		
		//initialize both array 'incr' and 'decr' with 1s
		Arrays.fill(incr, 1);
		Arrays.fill(decr, 1);
		
		//calculating 'incr' and 'decr' using DP
		for(int i = 0; i < n; i++) {
			for(int j=0;j < i; j++) {
				if(A[j] < A[i]) {
					incr[i]=Math.max(incr[j] + 1, incr[i]);
				}
				if(B[j] < B[i]) {
					decr[i]=Math.max(decr[j] + 1, decr[i]);
				}
			}
		}
		
		//reverse the result of 'decr'
		decr=reverse(decr, n);

		//add correspoding results in 'incr' and 'decr' and find the largest one as the final result
		for(int i = 1; i < n - 1;  i++) {
			if(incr[i] > 1 && decr[i] > 1) {
				int helper=incr[i] + decr[i] - 1;
				if(helper > res) {
					res = helper;
				}
			}
		}
		
		return res;
    } // end of procedure LongestBubble()

    // a helper used to reverse an array
    public static int[] reverse(int a[], int n){ 
        int[] b = new int[n]; 
        int j = n; 
        for (int i = 0; i < n; i++) { 
            b[j - 1] = a[i]; 
            j--; 
        } 
        return b;
    } 


    public static int Computation(ArrayList Instance, int opt){
        // opt=1 here means option 1 as in -opt1, and opt=2 means option 2 as in -opt2
        int NGounp = 0;
        int size = 0;
        int Correct = 0;
        size = Instance.size();

        int [] A = new int[size-opt];
        // NGounp = Integer.parseInt((String)Instance.get(0));
        NGounp = (Integer)Instance.get(0); // NOTE: NGounp = 0 always, as it is NOT used for any purpose
                                           // It is just the first "0" in the first line before instance starts.
        for (int i = opt; i< size;i++ ){
            A[i-opt] = (Integer)Instance.get(i);
        }
        int Size =A.length;
        if (NGounp >Size ){
            return (-1);
        }
        else {
            //Size
            int R = LongestBubble(A,Size);
            return(R);
        }
    }

    public static String Test;


    public static void main(String[] args) {
        Test = args[0];
        int opt = 2;
        String pathname = "dataTwo.txt";
        if (Test.equals("-opt1")) {
            opt = 1;
            pathname = "dataOne.txt";
        }


        ArrayList Datalist = new ArrayList();
        Datalist = ReadData(pathname);
        ArrayList AIS = DataWash(Datalist);

        int Nins = AIS.size();
        int NGounp = 0;
        int size = 0;
        if (Test.equals("-opt1")) {
            for (int t = 0; t < Nins; t++) {
                int Correct = 0;
                int Result = 0;
                ArrayList Instance = (ArrayList) AIS.get(t);
                Result = Computation(Instance, opt);
                System.out.println(Result);
            }
        }
        else {
            int wrong_no = 0;
            int Correct = 0;
            int Result = 0;
            ArrayList Wrong = new ArrayList();
            for (int t = 0; t < Nins; t++) {
                ArrayList Instance = (ArrayList) AIS.get(t);
                Result = Computation(Instance, opt);
                System.out.println(Result);
                Correct = (Integer) Instance.get(1);
                if (Correct != Result) {
                    Wrong.add(t+1);
                    //Wrong.add(Instance);
                    wrong_no=wrong_no+1;
                }
            }
            if (Wrong.size() > 0) {System.out.println("Index of wrong instance(s):");}
            for (int j = 0; j < Wrong.size(); j++) {
                System.out.print(Wrong.get(j));
                System.out.print(",");

                /*ArrayList Instance = (ArrayList)Wrong.get(j);
                for (int k = 0; k < Instance.size(); k++){
                    System.out.println(Instance.get(k));
                }*/
            }
            System.out.println("");
            System.out.println("Percentage of correct answers:");
            System.out.println(((double)(Nins-wrong_no) / (double)Nins)*100);

        }

    }
}
