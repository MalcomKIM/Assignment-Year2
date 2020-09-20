/*
* Student ID: 201447766
* Student Name: Minhao Jin
* Email: M.Jin5@student.liverpool.ac.uk
*
* User: sgmjin2
*
* Problem ID: 1025
* RunID: 4050
* Result: Accepted
*/

#include<stdio.h>

/*a recursive function for calulating largest common factor of 2 integers 'a' and 'b'*/
int LCF(int a, int b){
	
	if(a == 0){
		/*if 'a' equals 0, which means a%b=0 and return 'b' as the result*/
		return b;
	}
	
	if(b == 0){
		/*if 'b' equals 0, which means b%a=0 and return 'a' as the result*/
		return a;
	}
	
	if(a == b){
		/*if 'a' equals 'b', then return 'a' as result*/
		return a;
	}
	
	if(a > b){
		/*if 'a'>'b', then calculate the largest common factor between 'a%b' and 'b' recursively*/
		return LCF(a%b,b);
	}
	else{
		/*if 'b'>'a', then calculate the largest common factor between 'a' and 'b%a' recursively*/
		return LCF(a,b%a);
	}
}

/*main function*/
int main(void){
	/*intialize 'a' and 'b' as two integers to record user input*/
	int a;
	int b;
	
	/*read input from the user*/
	scanf("%d",&a);
	scanf("%d",&b);
	
	int result=LCF(a,b);	/*calculate the largest common factor*/
	
	/*smallest common multiple = 'a' * 'b' / largest common factor
	print out the result*/
	printf("%d %d",result,a*b/result);
	
	return 0;
}
