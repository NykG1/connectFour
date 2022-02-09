// file: connect_four.AIPlayer.java
// CSC 243, Spring 2020
// File was originally made by Dr. Schwesinger, updated and edited by Nik Golombek

package connect_four;

import java.util.Random;
import java.util.List;

import connect_four.Board;
import connect_four.Board.Disc;
import connect_four.Board.Position;

public class AIPlayer extends Player {
    private Random r;

    AIPlayer(Disc player_disc) {
        super(player_disc);
        r = new Random();
    }

    @Override
    public PlayerMove getMove(Board board) {
        PlayerMove result = null;

		//Integer [] old = board.getValidMoves.toArray();

        Integer [] validMoves = board.getValidMoves().toArray(new Integer[board.getValidMoves().size()]); // Used https://docs.oracle.com/javase/8/docs/api/
        Integer moveIndex = new Random().nextInt(validMoves.length);
        Integer move = validMoves[moveIndex];
        try {
            result = new PlayerMove(Action.PLAY, new Position(move));
        }
        catch (GameException ex) {
            // pass
            // we know it will work because we called getValidMoves
        }

        return result;
    }
}


