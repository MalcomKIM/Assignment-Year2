/*
* Student ID: 201447766
* Student Name: Minhao Jin
* Email: M.Jin5@student.liverpool.ac.uk
*
* User: sgmjin2
*
* Problem ID: 1081
* RunID: 10472
* Result: Accepted
*/

#include<stdio.h>
#include <math.h>

/* main function */
int main(void){
	int row, col, step;		/* initialize 'row','col' and 'step' as integers for user input */
	scanf("%d %d %d",&row, &col, &step);	/* read user input */
	
	char board[row][col];	/* initialize a 2D char array for storing board status */
	int livings[row][col];	/* initialize a 2D int array for storing numbers of living neighbours for each cell*/
	
	for(int i=0;i<row;i++){
		/* read in the initial board state row by row */
		scanf("%s",&(board[i]));
	}
 
  /* simulate Game of Life within given steps */
  for(int s=0;s<step;s++){
  	/* calculate the number of living neighbors for each cell */
  	for(int i=0;i<row;i++){
  		for(int j=0;j<col;j++){
  			livings[i][j]=living_neighbours(row, col,board,i,j);
  			printf("%d",livings[i][j]);
  		}
		printf("\n");
  	}
     	

  	update(row,col,board,livings);		/* update the status of each cell based on numbers of living neighbours */
  }
  
  /* print the final simulating results after given steps */
	for(int i=0;i<row;i++){
     	for(int j=0;j<col;j++){
     		printf("%c",board[i][j]);
	}
	printf("\n");
  }  
  
  return 0;
}

/* calculate the number of living neighbors for one cell */
int living_neighbours(int row, int col, char *board, int x, int y ){
	int living_count=0;		/* 'living_count' records the number of living neighbours */
	
	/* look up cells' status around the cell, including itself */
	/* function 'fmax' amd 'fmin' are used for boundary detection to avoid exceptions */
	for(int i=fmax(x-1,0);i<=fmin(x+1,row-1);i++){
		for(int j=fmax(y-1,0);j<=fmin(y+1,col-1);j++){
			if(*(board+i*col+j)=='X'){
				/* if the cell is live, add 1 to 'living_count' */
				living_count++;
			}
		}
	}
	
	if(*(board+x*col+y)=='X'){
		/* if the cell itself is live, minus 1 to the total livings count 'living_count' */
		living_count--;
	}
	
	return living_count;
}

/* update the board based on numbers of living neighbours */
void update(int row, int col, char *board,int livings[row][col]){
	for(int i=0;i<row;i++){
		for(int j=0;j<col;j++){
			char state = *(board+i*col+j);		/* get current status of the cell */
			int livings_count = livings[i][j];	/* get the corresponding number of living neighbours of that cell */
			
			if(state == '.' && livings_count==3){
				/* if the cell is dead and number of its living neighbours equals to 3,
				 then it becomes live */
				*(board+i*col+j)='X';
			}
			else if(state == 'X'){
				/* if the cell is live */
				if(livings_count<2||livings_count>3){
					/* if the cell is live and number of its living neighbours is greater than 3 or less than 2,
					then it becomes dead */
					*(board+i*col+j)='.';
				}
			}
		}
	}
}
