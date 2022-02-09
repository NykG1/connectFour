// connect_four.Game.java
// CSC 243, Spring 2020

package connect_four;

import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.List;
import java.util.EmptyStackException;

import connect_four.Board;
import connect_four.Board.Disc;
import connect_four.Board.Position;
import connect_four.Player.PlayerMove;

/**
 *  The Game class has the game logic for a text based game of Connect Four
 */
public class Game {
    private Board board;
    private Scanner input;
    private Map<Disc, Player> players;
    private Disc currentPlayer;
	private Stack<Board> stack;

    public Game() {
        this(false, false);
    }

	/**
	* Make a game of connect four
	* @param use_AI_for_yellow True if AI is yellow, else false
	* @param use_AI_for_red True if AI is red, else false
	*/
    public Game(boolean use_AI_for_yellow, boolean use_AI_for_red) {
        board = new Board();
        input = new Scanner(System.in);
        players = new HashMap<>();
		stack = new Stack<Board>();

        if (use_AI_for_yellow) {
            players.put(Disc.YELLOW, new AIPlayer(Disc.YELLOW));
        }
        else {
            players.put(Disc.YELLOW, new ConsolePlayer(Disc.YELLOW, input));
        }

        if (use_AI_for_red) {
            players.put(Disc.RED, new AIPlayer(Disc.RED));
        }
        else {
            players.put(Disc.RED, new ConsolePlayer(Disc.RED, input));
        }
        currentPlayer = Disc.YELLOW; // yellow goes first
    }

    /**
     * The run loop for the text based game of Connect Four
     */
    public void run(){
        System.out.println("Connect Four\n");

        // the game loop
        while (true) {

            // print the board to the screen
            System.out.println(board.toString());

            System.out.print("Available moves: ");
            List<Integer> validMoves = board.getValidMoves();
            for (int i = 0; i < validMoves.size(); i++) {
                System.out.print(validMoves.get(i) + " ");
            }
            System.out.println("");

            // get the play move and perform the appropriate action
            PlayerMove pm = players.get(currentPlayer).getMove(board);
            switch (pm.action) {
                case QUIT:
                    System.exit(0);
                    break;
                case PLAY:
                    try {
                        board.playMove(currentPlayer, pm.position);
						stack.push((Board)board.clone());
                    }
                    catch (GameException ex) {
                        System.out.println("Invalid move. Try again");
                        break;
                    }

                    try {
                        Disc winner = board.checkWinner();
                        if (winner != Disc.EMPTY) {
                            String playerName =
                                currentPlayer == Disc.YELLOW ? "Yellow" : "Red";
                            System.out.println("Game over");
                            System.out.println(playerName + " player wins");
                            System.out.println(board);
                            System.exit(0);
                        }
                    }
                    catch (DrawException ex) {
                        System.out.println("Game over");
                        System.out.println("Draw");
                        System.out.println(board);
                        System.exit(0);
                    }

                    // switch player
                    currentPlayer = (currentPlayer == Disc.YELLOW) ?
                        Disc.RED : Disc.YELLOW;
                    break;
				case RESET:
					board = new Board();
					stack.clear();
					break;
				case UNDO:
					if(stack.empty() == true){
						System.out.println("-------------------------");
						System.out.println("There is nothing to undo.");
						System.out.println("-------------------------");
					}	
					if(stack.size() == 1){
						board = new Board();
						stack.clear();
						currentPlayer = (currentPlayer == Disc.YELLOW) ?
						Disc.RED : Disc.YELLOW;
					}
					Player red1 = players.get(Disc.RED);
					Player yellow1 = players.get(Disc.YELLOW);
					/*if(stack.size() == 2 && (red1 instanceof AIPlayer || yellow1 instanceof AIPlayer)){
						board = new Board();
						stack.clear();
					}*/
					if(stack.empty() == false){
						Player red2 = players.get(Disc.RED);
						Player yellow2 = players.get(Disc.YELLOW);
							if(red2 instanceof AIPlayer){
								try{
									stack.pop();
									stack.pop();
									board = stack.peek();
									//currentPlayer = (currentPlayer == Disc.YELLOW) ?
									//Disc.RED : Disc.YELLOW;
								}
								catch(EmptyStackException e){
									stack.clear();
									board = new Board();
									currentPlayer = Disc.YELLOW;
								}
							}
							else if(yellow2 instanceof AIPlayer){
								stack.pop();
								stack.pop();
								board = stack.peek();
								currentPlayer = (currentPlayer == Disc.YELLOW) ?
								Disc.RED : Disc.YELLOW;								
							}
							else{
								stack.pop();
								board = stack.peek();
								currentPlayer = (currentPlayer == Disc.YELLOW) ?
								Disc.RED : Disc.YELLOW;
							}
						}
					break;
                default:
                    System.out.println("Unknown action");
            }

        }
    }

    /**
     * Play a text based game of Connect Four to completion
     *
     * @param args the command line argument array
     */
    public static void main(String[] args) {
        // Parse command line arguments
        boolean use_AI_for_yellow = false;
        boolean use_AI_for_red = false;
        for (String s: args) {
            if (s.equals("-y")) {
                use_AI_for_yellow = true;
            }
            if (s.equals("-r")) {
                use_AI_for_red = true;
            }
        }
		
			Game g = new Game(use_AI_for_yellow, use_AI_for_red);
			g.run();
    }
}
