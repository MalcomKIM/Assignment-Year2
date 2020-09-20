/*
* Student ID: 201447766
* Student Name: Minhao Jin
* Email: M.Jin5@student.liverpool.ac.uk
*
* User: sgmjin2
*
* Problem ID: 1030
* RunID: 4054
* Result: Accepted
*/
#include<stdio.h>

/*main function*/
int main(void){
	/*initialize 'a','b' and 'n' as integers for user input*/
	int a;
	int b;
	int n;
	
	int result=-1;	/*initialize 'result' to store the final result*/
	
	scanf("%d %d %d",&a,&b,&n);	/*read user input*/
	
	if(a>b){
	/*since the result is the n-th digit after decimal point,
	set 'a' to the remainder of 'a/b'*/
		a=a%b;
	}
	
	for(int i=1;i<=n;i++){
	/*calculate the target digit
	set 'a' as the remainder of '10a/b' until reaches the target digit*/
		a=a*10;
		result=a/b;
		a=a%b;
	}
	
	printf("%d",result);	/*print the result*/
	
	return 0;
}
