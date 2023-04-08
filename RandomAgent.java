package lab2;

import java.util.ArrayList;
import java.util.List;

public class RandomAgent extends Agent {

    public RandomAgent(Board board) {
        super(board);
    }

    /**
     * Gets a valid random move the RandomAgent can do.
     * @return a valid Move that the RandomAgent can perform on the Board
     */
    @Override
    public Move getMove() { // TODO
    	Move move = null;
    	List<Move> posMove = this.board.getPossibleMoves();    //get the list of possible moves
		int selection = (int) (Math.random() * posMove.size());
		move = posMove.get(selection);                         //randomly selects one from the list and state what move is executed
		System.out.printf("[%s (Random Agent)] Moving piece " + (char)(move.fromCell.getCoordinate().col + 'A') + (move.fromCell.getCoordinate().row + 1) + " to " + (char)(move.toCell.getCoordinate().col + 'A') + (move.toCell.getCoordinate().row + 1) + ".%n", board.getTurn().getType());
    	return move;
        
    }
}