/*
* Student ID: 201447766
* Student Name: Minhao Jin
* Email: M.Jin5@student.liverpool.ac.uk
*
* User: sgmjin2
*
* Problem ID: 1014
* RunID: 4043
* Result: Accepted
*/

#include<stdio.h>

#define PI 3.14	/*define PI as a constant 3.14*/

/*main function*/
int main(void){
	int r1,r2;	/*initialize 'r1' and 'r2' as integer to record user input*/
	
	/*read 'r1' and 'r2'*/
	scanf("%d",&r1);
	scanf("%d",&r2);
	
	float area_sum=0;	/*initialize 'area_sum' to record the sum of areas*/
	float cir_sum=0;	/*initialize 'cir_sum' to record the sum of circumferences*/
	
	for(int i=r1;i<=r2;i++){
		/*calculate the sums of circle areas and circumference from 'r1' to 'r2'*/
		area_sum += PI*i*i;
		cir_sum += 2*PI*i;
	}
	
	printf("%.3f\n%.3f",area_sum,cir_sum);	/*print the result in given format*/
	
	return 0;
}

