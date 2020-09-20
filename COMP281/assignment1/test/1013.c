/*
* Student ID: 201447766
* Student Name: Minhao Jin
* Email: M.Jin5@student.liverpool.ac.uk
*
* User: sgmjin2
*
* Problem ID: 1013
* RunID: 4041
* Result: Accepted
*/

#include<stdio.h>

void print_result(int levelA,int levelB,int levelC){
	/*fuction for printing results in given format
	arguments are the number of scores in each level*/
	printf(">=85:%d\n",levelA);
	printf("60-84:%d\n",levelB);
	printf("<60:%d\n",levelC);
}

/*main function*/
int main(void){
	/*initialize 'levelA','levelB' and 'levelC' to record the number of scores in each level
	and set the initial value to 0*/
	int levelA=0;
	int levelB=0;
	int levelC=0;
	
	int input=-1;	/*initialize 'input' with a negative value to accept user's input*/
	
	
	while(1){
		scanf("%d",&input);	/*user input a score*/
		
		if(input==0){
			/*if 'input' equals 0, break the loop*/
			break;
		}
		
		if(input>=85){
			/*if 'input'>=85, add one to 'levelA'*/
			levelA++;
		}
		else if(input>=60&&input<85){
			/*if 85>'input'>=60, add one to 'levelB'*/
			levelB++;
		}
		else if(input<60&&input>0){
			/*if 60>'input'>0, add one to 'levelC'*/
			levelC++;
		}
	}
	
	print_result(levelA,levelB,levelC);	/*call the function to print the results*/
	
	return 0;
}
