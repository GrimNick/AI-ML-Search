
import java.util.Scanner;

public class TTTGame
{
    TicTacToe game; // For all TTT board related tasks.


    public TTTGame() {
         game = new TicTacToe(); // For all TTT board related tasks.
    }

    public TTTGame(int n) {
         game = new TicTacToe(n); // For all TTT board related tasks.
    }

    char winner; // Who won?
    /*
     * Start the game
     * Display the results after it is completed.
     */
    public void startGame() {
        game.displayBoard();
        playGame();
        winner = game.getWinner();
        if(game.isCustomGridGame()) {
            int noOf3Os = game.getOgot3();
            int noOf3Xs = game.getXgot3();
            if (noOf3Os > noOf3Xs) winner = 'O';
            if (noOf3Os < noOf3Xs) winner = 'X';
            if (noOf3Os == noOf3Xs) winner = ' ';
        }
        printMessage();
    }
    /*
     * Scanner class is used to get [row,col] from standard input

     * Game is completed if there is a winner or 9 moves have been made.
     */
    public char playGame() //Kina char type? i think its better for void type
    {
        Scanner in = new Scanner(System.in);
        int count = 0; // Count number of turns. If it is 9 it is a draw.
        char turn; // Is it X's turn or O's turn?
        int row, col; // Hold board position.
// while no one has won and not yet a draw
        if(!(game.isCustomGridGame())) {
            while (game.getWinner() == ' ' && count < 9) {
                turn = game.whoseTurn();
                System.out.println(turn + "'s turn. Type row and col:");
                do {

                    row = in.nextInt();
                    while (row < 0 || row > 2) {
                        System.out.println("Invalid! Please enter a number from 0 and 2: ");
                        row = in.nextInt();
                    }

                    col = in.nextInt();
                    while (col < 0 || col > 2) {
                        System.out.println("Invalid! Please enter a number from 0 and 2: ");
                        col = in.nextInt();
                    }

                } while (game.getMark(row, col) != ' '); // Is this cell empty?
                game.putMark(row, col);
                game.displayBoard();
                count++;
            }
        }
        else{
            int totalGrids = game.totalGridSize();
            while ( count < totalGrids) {
                turn = game.whoseTurn();
                System.out.println(turn + "'s turn. Type row and col:");
                do {
                    row = in.nextInt();
                    while (row < 0 || row > game.getLimit()-1) {
                        System.out.println("Invalid! Please enter a number from 0 and "+(game.getLimit()-1));
                        row = in.nextInt();
                    }

                    col = in.nextInt();
                    while (col < 0 || col > game.getLimit()) {
                        System.out.println("Invalid! Please enter a number from 0 and "+(game.getLimit()-1));
                        col = in.nextInt();
                    }

                } while (game.getMark(row, col) != ' '); // Is this cell empty?
                game.putMark(row, col);
                game.displayBoard();
                char temp = game.getWinner();
                int noOf3Os= game.getOgot3();
                int noOf3Xs= game.getXgot3();
                System.out.println("Score of O is : "+noOf3Os);
                System.out.println("Score of X is : "+noOf3Xs);

                count++;
            }

        }
        in.close();
        return 0;
    }
    /*
     * Print Win or Draw message.
     */
    public void printMessage() {
        if(winner=='X')
            System.out.println("X has won!");
        else if(winner=='O')
            System.out.println("O has won!");
        else
            System.out.println("It's a draw!");
    }
    public static void main(String[] args)
    {
        int sizeOfGrid;
        TTTGame ttt;
        System.out.println("if you want custom grid size (custom rules most 3's), press y, else for simple tic tac toe press any button!");
        Scanner in = new Scanner(System.in);
        char ch = in.next().charAt(0); // Reads first character of the input
        if( ch == 'y' || ch == 'Y') {
            System.out.println("enter the size of the grid nxn!");

            sizeOfGrid = in.nextInt();
            while (sizeOfGrid < 3 || sizeOfGrid > 50) {
                System.out.println("Too big or too small! Please enter a number from 3 and 50: ");
                sizeOfGrid = in.nextInt();
            }
            ttt = new TTTGame(sizeOfGrid);
        }else {
            ttt = new TTTGame();
        }
        ttt.startGame();
    }
}