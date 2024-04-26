package tictactoe;
import java.util.Scanner;

class Board {

    private char[][] board;

    public Board() {
        board = new char[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = ' ';
            }
        }
    }
    public Board(String init_setting){
        this();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = init_setting.charAt(j + i * 3);
            }
        }
    }
    public void show() {
        System.out.println("---------");
        for(int i = 0; i < 3; i++) {
            System.out.printf("| %c %c %c |\n", board[i][0], board[i][1], board[i][2]);
        }
        System.out.println("---------");
    }

    private boolean checkWin(char player){
        // Check each row.
        for (int i = 0; i < 3; ++i)
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;

        // Check each column.
        for (int j = 0; j < 3; ++j)
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player)
                return true;

        // Check the diagonals.
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;

        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;

        // No winner.
        return false;
    }

    private int[] countChecks(){
        int[] counts = {0, 0};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch(board[i][j]){
                    case 'X':
                        counts[0]++;
                        break;
                    case 'O':
                        counts[1]++;
                        break;
                }
            }
        }
        return counts;
    }

    public boolean checkBoard(){
        int[] counts = countChecks();
        // if difference bigger than 1 then it is a not possible state
        if(Math.abs(counts[0] - counts[1]) > 1){
            System.out.println("Impossible");
            return false;
        }
        boolean winX = checkWin('X');
        boolean winY = checkWin('O');
        // it can't have two winner at the same time
        if(winX && winY){
            System.out.println("Impossible");
            return false;
        }
        // check for a winner
        if(winX || winY){
            System.out.printf("%c wins\n", winX ? 'X' : 'O');
            return true;
        }
        // check if it is a draw
        if (counts[0] + counts[1] == 9) {
            System.out.println("Draw");
            return true;
        }

        // the game is not yet finished
        System.out.println("Game not finished");
        return false;
    }

    private boolean checkPosition(int x, int y){
        if (board[x][y] == 'X' || board[x][y] == 'O'){
            return false;
        }
        return true;
    }

    public int[] getInput(){
        Scanner scanner = new Scanner(System.in);
        int[] input = new int[2];
        do {
            String[] substring = scanner.nextLine().split(" ");
            if(substring.length != 2){
                System.out.println("You should enter numbers!");
                continue;
            }
            try {
                boolean rangeFalse = false;
                for (int i = 0; i < substring.length; i++) {
                    // parse the string to a number
                    input[i] = Integer.parseInt(substring[i]) - 1;
                    if (input[i] < 0 || input[i] > 2) {
                        System.out.println("Coordinates should be from 1 to 3!");
                        rangeFalse = true;
                        continue;
                    }
                }
                if (rangeFalse) {
                    continue;
                }

            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if(!checkPosition(input[0], input[1])){
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            break;
        }while(true);

        return input;
    }

    public boolean setPosition(char player, int x, int y){
        if(!checkPosition(x, y)){
            return false;
        }
        board[x][y] = player;
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        // write your code here
        //Scanner scanner = new Scanner(System.in);
        //String input = scanner.nextLine();

        char[] player = {'X', 'O'};
        int currentPlayer = 0;

        Board board = new Board();
        board.show();
        do{
            int[] pos = board.getInput();
            board.setPosition(player[currentPlayer], pos[0], pos[1]);
            board.show();
            if(board.checkBoard()){
                break;
            }
            currentPlayer = (currentPlayer + 1) % 2;
        }while(true);

    }

}
