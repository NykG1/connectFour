// connect_four.Board.java
// CSC 243, Spring 2020
// File was originally made by Dr. Schwesinger, updated and edited by Nik Golombek

package connect_four;

import java.util.List;
import java.util.ArrayList;

import java.lang.Cloneable; 

/**
 * The Board class is a representation of the board state for a game of Connect
 * Four
 */
public class Board implements Cloneable {

    public enum Disc {
        EMPTY,
        YELLOW,
        RED
    }

	/**
	* The location on the board being looked at
	*/
    public static class Position {
        public int column;
		/**
		* The location on the board being looked at
		* @param column the column chosen by the user
		* @throws GameException when a user chooses an out of bound column
		*/
        Position(int column) throws GameException {
            if (column < 1 || column > COLUMNS) {
                throw new GameException("Invalid move: " + column);
            }
            this.column = column;
        }
    }

    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final int CONNECT = 4;

    private Disc [][] board;

    /**
     * The Board constructor initializes the board state to the starting
     * configuration of the game
     */
    public Board() {
        board = new Disc[ROWS][COLUMNS];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Disc.EMPTY;
            }
        }
    }
	
	/**
     * Overrides the clone method for this object
	 * @return an object of the cloned board
     */
	public Object clone(){
			Board boardCloned = new Board();
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					boardCloned.board[i][j] = board[i][j];
				}
			}
			//System.out.println("Catch meeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			return boardCloned;
	}

    /**
     * playMove attempts to play the move indicated by the player and house
     * parameters. If the move is invalid, then a result of false is returned.
     *
     * @param disc says the action of the player
     * @param position a representation of the column in the range 1-7
     * @throws GameException if the move is not valid
     */
    public void playMove(Disc disc, Position position) throws GameException {
        int column = position.column;

        // check if there is space in the column
        if (board[0][column-1] != Disc.EMPTY) {
            throw new GameException("Invalid move: " + column);
        }

        int row = 0;
        while (row+1 < ROWS && board[row+1][column-1] == Disc.EMPTY) {
            row += 1;
        }

        board[row][column-1] = disc;
    }

    /**
     * getValidMoves returns list of valid moves
     * @return an list of valid moves
     */
    public List<Integer> getValidMoves() {
        List<Integer> result = new ArrayList<Integer>();
/*
        int valid_count = 0;
        for (int col = 0; col < COLUMNS; col++) {
            if (board[0][col] == Disc.EMPTY) {
                valid_count += 1;
            }
        }

        result = new int[valid_count];
        int index = 0;
        for (int col = 0; col < COLUMNS; col++) {
            if (board[0][col] == Disc.EMPTY) {
                result[index] = col+1;
                index += 1;
            }
        }
*/
		for (int col = 0; col < COLUMNS; col++) {
            if (board[0][col] == Disc.EMPTY) {
                result.add(col+1);
            }
        }

        return result;
    }

    /**
     * checkWinner determines if there is a current winner and returns the Disc
     * representation of the winner. If there is no winner, then Disc.EMPTY is
     * returned.
     *
     * @return a character representation of the winner.
     * @throws DrawException if the game is a draw
     */
    public Disc checkWinner() throws DrawException{
        Disc result = Disc.EMPTY;

        List<Integer> validMoves = getValidMoves();
        if (validMoves.size() == 0) {
            throw new DrawException("Draw");
        }

        // check rows
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < (COLUMNS - CONNECT)+1; col++) {
                Disc [] chunk = new Disc[CONNECT];
                for (int offset = 0; offset < CONNECT; offset++) {
                    chunk[offset] = board[row][col+offset];
                }
                if (allEqual(chunk) && chunk[0] != Disc.EMPTY) {
                    result = board[row][col];
                }
            }
        }

        // check columns
        for (int row = 0; row < (ROWS - CONNECT)+1; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Disc [] chunk = new Disc[CONNECT];
                for (int offset = 0; offset < CONNECT; offset++) {
                    chunk[offset] = board[row+offset][col];
                }
                if (allEqual(chunk) && chunk[0] != Disc.EMPTY) {
                    result = board[row][col];
                }
            }
        }

        // check diagonals
        for (int col = 0; col < (COLUMNS - CONNECT) + 1; col++) {
            for (int row = 0; row < (ROWS - CONNECT) + 1; row++) {
                Disc [] chunk = new Disc[CONNECT];
                for (int offset = 0; offset < CONNECT; offset++) {
                    chunk[offset] = board[row+offset][col+offset];
                }
                if (allEqual(chunk) && chunk[0] != Disc.EMPTY) {
                    result = board[row][col];
                }
            }
        }

        for (int col = 0; col < (COLUMNS - CONNECT) + 1; col++) {
            for (int row = 0; row < (ROWS - CONNECT) + 1; row++) {
                Disc [] chunk = new Disc[CONNECT];
                for (int offset = 0; offset < CONNECT; offset++) {
                    chunk[offset] = board[row+CONNECT-offset-1][col+offset];
                }
                if (allEqual(chunk) && chunk[0] != Disc.EMPTY) {
                    result = board[row+CONNECT-1][col];
                }
            }
        }

        return result;
    }

    /**
     * allEqual checks if all the elements in an array are equal
     *
     * @param a the array to check
     * @return true if all elements are equal, otherwise false
     */
    private boolean allEqual(Disc[] a) {
        boolean result = true;

        if (a.length > 0) {
            Disc val = a[0];
            for (int i = 1; i < a.length; i++) {
                if (a[i] != val) {
                    result = false;
                }
            }
        }

        return result;
    }

    /**
     * toString returns a string representation of the board
     * @return the string representation of the board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < (COLUMNS * 2)+1; i++) {
            if (i % 2 == 0) {
                sb.append(" ");
            }
            else {
                sb.append((i/2)+1);
            }
        }
        sb.append("\n");

        for (int row = 0; row < ROWS; row++) {
            for (int out_col = 0; out_col < (COLUMNS*2)+1; out_col++) {
                if (out_col % 2 == 0) {
                    sb.append("|");
                }
                else {
                    if (board[row][out_col/2] == Disc.YELLOW) {
                        sb.append("Y");
                    }
                    else if (board[row][out_col/2] == Disc.RED) {
                        sb.append("R");
                    }
                    else {
                        sb.append(" ");
                    }
                }
            }
            sb.append("\n");
        }

        for (int i = 0; i < (COLUMNS * 2)+1; i++) {
            sb.append("-");
        }
        sb.append("\n");

        return sb.toString();
    }

    /**
     * test and display game state changes
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Board b = new Board();
    }
}
