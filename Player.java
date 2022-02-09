// connect_four.Player.java
// CSC 243, Spring 2020

package connect_four;

import connect_four.Board.Disc;
import connect_four.Board.Position;

/**
* Information on the player's action and position of action
*/
public abstract class Player {

	/**
	* Determines whether the player will keep playing, quit, reset, or undo
	*/
    public enum Action {
        PLAY,
        QUIT,
		RESET,
		UNDO,
    }

	/**
	* The action of the player and what position the action is at
	*/
    public static class PlayerMove {
        Action action;
        Position position;
        PlayerMove(Action action) {
            this.action = action;
        }
        PlayerMove(Action action, Position position) {
            this.action = action;
            this.position = position;
			//board.cloneable();
        }
    }

    Disc player_disc;

	/**
	* Set the description of the player's move
	* @param player_disc the desctription of a move
	*/
    public Player(Disc player_disc) {
        this.player_disc = player_disc;
    }

    public abstract PlayerMove getMove(Board board);
}
