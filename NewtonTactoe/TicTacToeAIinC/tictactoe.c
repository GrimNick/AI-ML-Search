#include <stdio.h>
#include <limits.h>
#include <time.h>
#include <termios.h>
#include <sys/time.h>
#include <sys/types.h>

//using minimaxing for ai as tic tac toe is perfect information, definite problem
static struct termios old,new;
 int thinkingStat;
//For colors in text
void red(void){
	printf("\033[0;31m");
}
void purple(void){
	printf("\033[0;35m");
}
void green(void){
	printf("\033[0;36m");
}
void yellow(void){
	printf("\033[0;38m");
}
void colorreset(void){
	printf("\033[0m");
}
void clr(){
	system("clear");
	}

//For gotoxy x-axis, y-axis
void gotoxy(int x,int y){
	printf("%c[%d;%df",0x1B,y,x);
}


//initializing new terminal io settings
 void initTermios(int echo){
 	tcgetattr(0,&old); 	//grab old terminal io settings
 	new = old;	//make new settings same as old settings
 	new.c_lflag &= ~ICANON; 	//disabling buffering
 	new.c_lflag &= echo ? ECHO : ~ECHO; //setting echo mode
 	tcsetattr(0,TCSANOW,&new);	//applying terminal io settings
 }


//for initializing old terminal io settings that is resetting the terminal settings
 void resetTermios(void){
 	tcsetattr(0,TCSANOW,&old);	//applying old terminal io settings
 }

//To read 1 character, echo defines echo mode
 char getch_(int echo){
 	char ch;
 	initTermios(echo);
 	ch = getchar();
 	resetTermios();
 	return ch;
 }

 char getch(void){
 	getch_(0);
 }


//To use function delay
void delay(int milliseconds)
{
    long pause;
    clock_t now,then;

    pause = milliseconds*(CLOCKS_PER_SEC/1000);
    now = then = clock();
    while( (now-then) < pause )
        now = clock();
}


char board[9];
void setUp(){
	for(int i=0;i<10;i++) board[i]=' ';
}

char checkWin(){
	char winCond[8][3] = {
		{0,1,2},{3,4,5},{6,7,8},	//horizontal win
		{0,3,6},{1,4,7},{2,5,8},	//vertical win
		{0,4,8},{6,4,2}	//diagonal win
	};
	for(int i =0;i<8;i++){
		int a=winCond[i][0],b=winCond[i][1],c=winCond[i][2];
		if(board[a] != ' ' && board[a] == board[b] && board[b] == board[c]){
			return board[a];
		}
	}
	return ' ';
}

int isDraw(){
	for(int i =0;i<9;i++){
		if(board[i] == ' ') return 0;
	}
	return 1;
}

void drawBoard(){
	int j = 0;
	for(int i=0;i<3;i++){
		if(i==0) printf("\n+---+---+---+\n");
		for(int k=0;k<3;k++){
			yellow();
			printf("|");
			printf(" ");
			if(board[j]=='X') red();
			else if(board[j] =='O') green();
			printf("%c",board[j]);
			
			printf(" ");
			j++;
			yellow();
			if(k==2) printf("|");	
		}
		yellow();
		printf("\n+---+---+---+\n");
	}
	colorreset();
	return;
}

void drawBoardMap(){ //Show 1,2..9 position
	int j = 0;
	for(int i=0;i<3;i++){
		if(i==0) printf("\n+---+---+---+\n");
		for(int k=0;k<3;k++){
			printf("| %d ",j+1);
			j++;
			if(k==2) printf("|");	
		}
		printf("\n+---+---+---+\n");
	}
}

void playerMove(int p1){
	int move;
	while(1){
	printf("Turn to move. Choose from (1-9):  ");
	scanf("%d",&move);
	if(move>0 && move<10 && board[move-1] ==' '){
		if(p1 == 1) board[move-1] = 'O';
		else board[move-1] = 'X';
		break;
	}else{
		printf("Wrong move. Choose again.\n");
	}
	}
}

int minimax(int isMaximizing){
	char winner = checkWin();
	if(winner == 'O') return -1;
	if(winner == 'X') return 1;
	if(isDraw()) return 0;
	int bestScore = isMaximizing ? INT_MIN : INT_MAX;
	for(int i=0;i<9;i++){
		if(board[i] ==' '){
		board[i] = isMaximizing ? 'X' : 'O';
		if(thinkingStat){
		 if(isMaximizing){
				printf("AI: maximizing gain and I am thinking %d moves forward human. no chance you win\n",i+1);
				}
				else{
				printf("AI: minimizing risk You might move here.. Or you might not lemme see\n");
		}
				drawBoard();
		}
		int score = minimax(!isMaximizing);
		board[i] = ' ';
		if(isMaximizing){
			if(score>bestScore){
				bestScore = score;
			}
		}else{
			if(score<bestScore){
				bestScore = score;
			}
		}
		}
	}
	return bestScore;
}

void aiMove(){
	printf("Ai turn \n");
	int bestScore = INT_MIN;
	int move = -1;
	for(int i =0;i<9;i++){
		if(board[i] ==' '){
			board[i] = 'X';
			if(thinkingStat){
				printf("AI: I am thinking of a new move and this is my thought for position no: %d\n",i+1);
				drawBoard();
			}
			int score = minimax(0);
			board[i] = ' ';
			if (score > bestScore){
				bestScore = score;
				move = i;
			}
		}
	}
	board[move] = 'X';
	return;
	
}

void playAI(){
	clr();
	char think;
	int first;
	while(1){
		printf("Do you want to hear ai thoughts?(y/n)");
		scanf("%c",&think);
		if(think == 'y' || think == 'Y'){
			thinkingStat = 1;
			printf("you will now hear ai\n");
			break;
		}
		
		if(think == 'n' || think == 'N'){
			thinkingStat = 0;
			printf("you wont hear ai\n");
			break;
		}
	}
	getch();
	while(1){
		printf("Do you want to go first?(y/n)");
		scanf("%c",&think);
		if(think == 'y' || think == 'Y'){
			first = 1;
			printf("you will now go first\n");
			break;
		}
		
		if(think == 'n' || think == 'N'){
			first = 0;
			printf("you now go second\n");
			break;
		}
	}
	
	
	setUp();
	drawBoardMap();
	drawBoard();
	char winner = ' ';
	
	while (1) {
		if(first ==1){
			playerMove(1);
			drawBoard();
			winner = checkWin();
			if(winner =='O'){
				printf("Player wins\n");
				break;
			}
			else if(winner =='X'){
				printf("Ai wins\n");
				break;
			}
			if(isDraw() == 1){
				printf("DRAW \n");
				break;
			}
		}
		aiMove();
		drawBoard();
		winner = checkWin();
		if(winner =='O'){
			printf("Player wins\n");
			break;
		}
		else if(winner =='X'){
			printf("Ai wins\n");
			break;
		}
		if(isDraw() == 1){
			printf("DRAW \n");
			break;
		}
		if(first==0) first =1;
		

		
	}
}


void play2(){
	clr();
	setUp();
	drawBoardMap();
	drawBoard();
	char winner = ' ';
	printf("first player will be O and second will be X, player 1 start \n");
	int count =0;
	while (1) {
		if(count%2 ==0){
			playerMove(1);
		}else{
			playerMove(0);
		}
		count++;
		drawBoard();
		winner = checkWin();
		if(winner =='O'){
			printf("Player 1 wins\n");
			break;
		}
		else if(winner =='X'){
			printf("Player 2 wins\n");
			break;
		}
		if(isDraw() == 1){
			printf("DRAW \n");
			break;
		}
	}
}



void menu(){
	clr();
	int mplay[2]={25,14};	//for play in menu
	int mhelp[2]={25,15};	//for help in menu
	int mexit[2]={25,16};	//for exit in menu
	int choice=0;
	while(1){
		choice =0;
		clr();
		purple();
		gotoxy(mplay[0],mplay[1]);
		printf("1. Play\n\n");
		gotoxy(mhelp[0],mhelp[1]);
		printf("2. Credits\n\n");
		gotoxy(mexit[0],mexit[1]);
		printf("3. Exit\n\n");
		
		green();
		gotoxy(24,13);
		printf("//~~~~~~~~~\\\\");
		gotoxy(24,17);
		printf("\\\\~~~~~~~~~//");
		colorreset();
		choice=getch();
		if(choice == 49) {
		 	purple();
			gotoxy(mplay[0],mplay[1]);
			printf("1. Against AI \n\n");
			gotoxy(mhelp[0],mhelp[1]);
			printf("2. Player vs Player\n\n");
			gotoxy(mexit[0],mexit[1]);
			printf("3. Back\n\n");

			colorreset();
			choice=getch();

			if(choice ==49){
				playAI();
				printf("press any key to continue\n");
				choice = getch();
				choice=getch();
				continue;
			}
			
			if( choice ==50){
				play2();
				printf("press any key to continue\n");
				choice = getch();
				choice=getch();
			 	continue;
			 }
			if( choice ==51) continue;
		}
		if(choice ==50){
			purple();
			gotoxy(mplay[0],mplay[1]);
			printf("Created by Newton Shahi Thakuri (Engineering student) \n\n");
			gotoxy(mhelp[0],mhelp[1]);
			printf("Kathmandu, Nepal. \n\n");
			gotoxy(mexit[0],mexit[1]);
			printf("Press any key to go back\n\n");

			colorreset();
			choice=getch();
			continue;
		}
		if(choice == 51){
			break;
		}
	}
	return;	

}


int main(){
	menu();
	return 0;
}


