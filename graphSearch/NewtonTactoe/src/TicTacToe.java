public class TicTacToe
{
    char[][] board; // TicTacToe board has 3 rows and 3 columns.
    char PLAYER_1 = 'X';
    char PLAYER_2 = 'O';
    char turn ; // Whose turn is it?
    int limit;

    int Xgot3,Ogot3;

    boolean customGridGame;
    /*
     * Initialize the 2D array. X always start's first.
     */
    public TicTacToe() {
        customGridGame = false;
        limit = 3;
        board = new char[limit][limit];
        for(int i=0;i <limit;i++){
            for(int j=0;j <limit;j++) {
                board[i][j] = ' ';
            }

        }
        turn='X';
    }

    public TicTacToe(int n) {
        customGridGame= true;
        Xgot3=0;
        Ogot3=0;
        limit = n;
        board = new char[n][n];
        for(int i=0;i <n;i++){
            for(int j=0;j <n;j++) {
                board[i][j] = ' ';
            }

        }
        turn='X';
    }

    /*
     * Check 3 rows, 3 cols and 2 diagonals for a win
     * If there is a winner return who won : X or O
     * Otherwise return a blank (space) character.
     */
    public char getWinner() {
        if(!customGridGame) {
            for (int i = 0; i < board.length; i++) {
                char a = board[i][0], b = board[i][1], c = board[i][2], d = board[0][i], e = board[1][i], f = board[2][i];
                if (a != ' ' && a == b && b == c) return a;
                if (d != ' ' && d == e && e == f) return d;
                if (i == 0) {
                    char g = board[i][0], h = board[i + 1][1], j = board[i + 2][2];
                    if (g != ' ' && g == h && h == j) return g;
                    char k = board[i][2], l = board[i + 1][1], m = board[i + 2][0];
                    if (k != ' ' && k == l && l == m) return k;

                }

            }
        }else{
            int looper1 =0, looper2=1,looper3=2;
            Xgot3=0;
            Ogot3=0;
            int n = board.length;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= n - 3; j++) {
                    // Horizontal
                    if (board[i][j] != ' ' && board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2]) {
                        if (board[i][j] == 'X') Xgot3++;
                        else Ogot3++;
                    }
                    // Vertical
                    if (board[j][i] != ' ' && board[j][i] == board[j+1][i] && board[j][i] == board[j+2][i]) {
                        if (board[j][i] == 'X') Xgot3++;
                        else Ogot3++;
                    }
                }
            }

            // Diagonal (\ direction)
            for (int i = 0; i <= n - 3; i++) {
                for (int j = 0; j <= n - 3; j++) {
                    if (board[i][j] != ' ' &&
                            board[i][j] == board[i+1][j+1] &&
                            board[i][j] == board[i+2][j+2]) {
                        if (board[i][j] == 'X') Xgot3++;
                        else Ogot3++;
                    }
                }
            }

            // Anti-diagonal (/ direction)
            for (int i = 0; i <= n - 3; i++) {
                for (int j = 2; j < n; j++) {
                    if (board[i][j] != ' ' &&
                            board[i][j] == board[i+1][j-1] &&
                            board[i][j] == board[i+2][j-2]) {
                        if (board[i][j] == 'X') Xgot3++;
                        else Ogot3++;
                    }
                }
            }


        }
        return ' ';
    }
    /*
     * Pretty print the TTT board.
     */
    public void displayBoard() {
        for(int i=0;i<board.length;i++){
            if(i==0) {
                for (int l = 0; l < board.length; l++) {
                    System.out.print("+---");
                }

                System.out.print("+");
                System.out.print("\n");
            }
            for(int k=0; k<limit;k++){
                //put color
                System.out.print("|");
                System.out.print(" ");
                System.out.print(board[i][k]);
                System.out.print(" ");
                if (k==limit-1) System.out.print("|");
            }
            System.out.print("\n");

            for(int l=0;l<board.length;l++) {
                System.out.print("+---");
            }
            System.out.print("+");

            System.out.print("\n");

        }
    }
    /*
     * Return the Player who has to put a mark.
     */
    public char whoseTurn() { return turn;}
    /*
     * Fill the board at [row,col] with X or O
     * depending on whose turn it is
     * then change turn from X to O or O to X.
     */
    public void putMark(int row, int col) {
        if(turn=='X'){
            board[row][col] ='X';
            turn = 'O';
        }
        else{
            board[row][col] ='O';
            turn = 'X';
        }

    }
    /*
     * Return the mark at [row,col] in the board.
     */
    public char getMark(int row, int col) {return board[row][col];}
    public int getXgot3() {return Xgot3;}
    public int getOgot3() {return Ogot3;}
    public boolean isCustomGridGame() {return customGridGame;}

    public int totalGridSize() {return limit*limit;}
    public int getLimit() {return limit;}

}