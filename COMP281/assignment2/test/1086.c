/*
* Student ID: 201447766
* Student Name: Minhao Jin
* Email: M.Jin5@student.liverpool.ac.uk
*
* User: sgmjin2
*
* Problem ID: 1086
* RunID: 10553
* Result: Accepted
*/

#include<stdio.h>
#include<stdbool.h>

/* a helper function for expanding */
void expand_format(int cnt, char output){
	for(int i=0;i<cnt-1;i++){
		/* print 'output' for 'cnt' times */
		printf("%c",output);
	}
}

/* function for compressing ASCII art file */
void compress(){
	char input;		/* initialize 'input' to store the current input char */
	char pre;		/* initialize 'pre' to store the previous input char */
	bool first = true;		/* a flag for determining whether an input is the first char of a line */
	int cnt=1;		/* a counter for the apperances of a character */
	
	while(scanf("%c",&input)!=EOF){
		/* keep reading characters until an 'EOF' is input */
		if(first){
			/* if the current input is the first character of a line */
			pre=input;		/* set 'pre' to the current 'input' */
			first = false;	/* set the 'first' flag to 'false' */
		}
		else{
			/* if the current input is not the first character of a line */
			if(input == pre && input!='\n'){
				/* if the current input equals to the previous one, add 1 to the counter 'cnt' */
				cnt++;
			}
			else{
				/* if the current input does not equal to the previous one */
				if(cnt>=2){
					/* if the value of 'cnt' is equal or greater than 2 */
					printf("%c%c%d*",pre,pre,cnt);		/* print the compress results in given format */
					cnt=1;		/* set 'cnt' back to 1 */
				}
				else if(cnt==1){
					/* if the value of 'cnt' equals to 1 */
					printf("%c",pre);		/* print that single character */
				}
				
				if(input!='\n'){
					/* if current input does not equal to '\n' */
					pre=input;		/* set 'pre' to the current 'input' */
				}
				else{
					/* if current input equals to '\n' */
					printf("\n");	/* start a new Line */
					first=true;		/* set the 'first' flag to 'true' */
				}
			}
		}
	}
}

/* function for expanding ASCII art file */
void expand(){
	char input;		/* initialize 'input' to store the current input char */
	char pre=NULL;		/* initialize 'pre' to store the previous input char */
	bool first = true;	/* a flag for determining whether an input is the first char of a compress pattern */
	int cnt;		/* times for a character to output */
	char output=NULL;	/* the character that will be printed out */
	
	while(scanf("%c",&input)!=EOF){
		/* keep reading characters until an 'EOF' is input */
		if(first){
			/* if the current input is the first character of a compress pattern */
			pre=input;		/* set 'pre' to the current 'input' */
			first=false;	/* set the 'first' flag to 'false' */
		}
		else{
			/* if the current input is not the first character of a line */
			if(pre==input){
				/* if the current input equals to the previous one */
				output=input;		/* set 'output' to the current 'input', ready to print */
				scanf("%d",&cnt);	/* read the integer which implies how many times that 'output' should be printed */
			}
			else{
				/* if the current input does not equal to the previous one */
				printf("%c",pre);		/* print that single character */
			}
			
			if(input=='*'&&output!=NULL){
				/* the current 'input' is '*' and 'output' is not empty */
				expand_format(cnt,output);	/* print 'output' for 'cnt' times */
				output=NULL;		/* set 'output' back to NULL */
				first=true;			/* set the 'first' flag to 'true' */
			}
			pre=input;		/* set 'pre' to the current 'input' */
		}
		
		if(input=='\n'){
			/* if current input equals to '\n' */
			printf("\n");	/* start a new Line */
			first=true;		/* set the 'first' flag to 'true' */
		}
 	}
}

/* main function */
int main(void){
	char mode;		/* initialize 'mode' for user to choose 'compress' or 'expand' */
	char newLine;	/* initialize 'newLine' for storing extra '\n'*/
	scanf("%c",&mode);		/* read in the mode user chooses */
	scanf("%c",&newLine);	/* catch the '\n' just after user inputs 'mode' */
	
	if(mode == 'C'){
		/* if user inputs 'C', then do 'compress()' */
		compress();
	}
	else if(mode =='E'){
		/* if user inputs 'E', then do 'expand()' */
		expand();
	}

	return 0;
}

