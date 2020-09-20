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
		Input sequence A= {A[1], A[2],..., A[n]}.
		Define incr(i) to be the length of the longest increasing subsequence that ends at position i. (1<=i<=n)
		Define decr(i) to be the length of the longest decreasing subsequence that starts at position i. (1<=i<=n)
		Suppose A[i] is the biggest number in a Bubble sequence, 
		then the length of this Bubble sequence is L(i) = incr(i) + decr(i) -1 (1<= i <=n). 
		(-1 because A[i] is counted twice both in incr(i) and LDS(i).)
		Then the solution to the Longest Bubble problem is L = max{L(2), L(3),..., L(n-1)}. 
		L(1) and L(n) cannot be the solution, because A[1] and A[n] are the start and end point of A, 
		which means they cannot be the largest number in any Bubble sequence.
		
		Step 1: compute incr(i)
		A new array LIS is defined to store the longest increasing subsequence in A.
		Elements of A will be put into LIS sequentialy and here are the rules:
		Suppose A[i] is the current element that should be inserted into LIS,
		1. if A[i] > the last element in LIS, then append A[i] to the end of LIS. 
		2. if A[i] <= the last element in LIS, then find the smallest element in LIS which is greater than A[i]
		   and replace that element with A[i]. (Using binary searching)
		incr(i) is the index of the position of A[i] in LIS.
		
		
		Step 2: compute decr(i)
		Given an array B[1,...,n] ={A[n], A[n-1],..., A[1]}, B is a reversed array of A.
		then instead of computing the length of the longest decreasing subsequence that starts at position i,
		we can compute the length of the longest increasing sequence ends at position i, similar to step 1.
		Notice: we should also reverse decr(1), decr(2),... ,decr(n) after we get the results.
		
		Step 3: compute the max L
		Set L=0;
		then L = max_[incr(i)+decr(i): incr(i)>1 and decr(i)>1] (2<=i<=n-1)
		We must ensure incr(i)>1 and decr(i)>1 because A[i] cannot be 
		the start point of a increasing subsequence 
		or the end point of a decreasing subsequence.
		Otherwise there is no Bubble sequence existing.
        */

        /* Here should be the statement and description of the running time
        analysis of your implementation: describe how the running time depends on
        n (length of the input sequence), and give short argument.
        
        1.Compute incr(i) 
        Computing incr(i) requires us to find the position of A[i] in LIS,
        so this will be O(logn) using binary searching for each element in A.
        In total, computing all of the incr(i) values takes O(n*logn) time beacause there are n elements in A.
        
        2.Compute decr(i) 
        Reverse A takes O(n).
        Computing decr(i) takes O(n*logn), same as computing incr(i).
        Reverse decr takes O(n).
        
        3.Find the value of L
        L = max_[(incr(i)+decr(i)): incr(i)>1 and decr(i)>1] (2<=i<=n-1) takes O(n) time.
        
        Thus, finding the length of longest Bubble sequence takes O(n*logn).
        */
    	int res=0;		//'res' is used for storing the final result
		
		int LISlen=0;	//'LISlen' records the current length of longest increasing subsequence in A
		int LDSlen=0;	//'LDSlen' records the current length of longest increasing subsequence in B
		
		int[] B=reverse(A, n);	//array 'B' is the reversed array of 'A'
		
		int[] LIS=new int[n];	//'LIS' records the longest increasing subsequence in A
		int[] LDS=new int[n];	//'LDS' records the longest increasing subsequence in B
		
		int[] incr=new int[n];	//'incr' records the position of A[i] in LIS
		int[] decr=new int[n];	//'decr' records the position of B[i] in LDS
		
		for(int i = 0; i < n; i++) {
				//find the position of A[i] in LIS
				int incr_replace_index=Arrays.binarySearch(LIS, 0,LISlen,A[i]);
				
				//find the position of B[i] in LDS
				int decr_replace_index=Arrays.binarySearch(LDS, 0,LDSlen,B[i]);
				
				//if A[i] is not contained in the LIS, it will returns (-(insertion point) - 1).
				if(incr_replace_index<0) {
					//get the real insertion point of A[i] in LIS
					incr_replace_index=-(incr_replace_index+1);
				}
				
				//if B[i] is not contained in the LDS, it will returns (-(insertion point) - 1).
				if(decr_replace_index<0) {
					//get the real insertion point of B[i] in LDS
					decr_replace_index=-(decr_replace_index+1);
				}
				
				//insert A[i] and B[i] into LIS and LDS
				LIS[incr_replace_index]=A[i];
				LDS[decr_replace_index]=B[i];
				
				//if all the elements in LIS is smaller than A[i], the length of LIS increases 1
				if(incr_replace_index==LISlen) {
					LISlen++;
				}
				
				//if all the elements in LDS is smaller than B[i], the length of LDS increases 1
				if(decr_replace_index==LDSlen) {
					LDSlen++;
				}
				
				//records the position of A[i] in LIS and the position of B[i] in LDS
				incr[i]=incr_replace_index+1;
				decr[i]=decr_replace_index+1;
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
