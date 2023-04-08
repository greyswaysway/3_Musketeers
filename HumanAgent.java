package lab2;

import java.util.List;
import java.util.Scanner;

public class HumanAgent extends Agent {

    public HumanAgent(Board board) {
        super(board);
    }

    /**
     * Asks the human for a move with from and to coordinates and makes sure its valid.
     * Create a Move object with the chosen fromCell and toCell
     * @return the valid human inputted Move
     */
    @Override
    public Move getMove() { // TODO
    	Cell start = null;
    	Scanner userInput = new Scanner(System.in);						//start the scanner
    	Move move = null;
    	boolean found = false;
		List<Cell> startCell = this.board.getPossibleCells();			//get the list of possible starting points
		System.out.printf("[%s] Possible pieces are ", board.getTurn().getType());
		for(int i = 0; i < startCell.size(); i++) {						//display all the possible starting points to the user
			System.out.print("[" + (char)(startCell.get(i).getCoordinate().col + 'A') + (startCell.get(i).getCoordinate().row + 1) +"]");
		}
		System.out.print(". Enter the piece you want to move: ");
		String name = userInput.nextLine();								//get user input
		int x = -1;
		while(!found) {													//keeps looping until a proper starting point is selected
			x++;
			if((int)(name.charAt(0) - 'A') == startCell.get(x).getCoordinate().col && (name.charAt(1) - '0' - 1) == startCell.get(x).getCoordinate().row) {//check if the point the user inputed is a possible starting point
				found = true;
			}
			if(!found && x == startCell.size() - 1) {					//if it is not ask user to input point again
				System.out.println(name + " is an invalid piece.");
				System.out.printf("[%s] Possible pieces are ", board.getTurn().getType());
				for(int i = 0; i < startCell.size(); i++) {
					System.out.print("[" + (char)(startCell.get(i).getCoordinate().col + 'A') + (startCell.get(i).getCoordinate().row + 1) +"]");//display all the possible starting points to the user
				}
				System.out.print(". Enter the piece you want to move: ");
				name = userInput.nextLine();							//get user input
				x = -1;
			}
			
		}
		if(found) {														//double check to make sure that starting point is found
			start = startCell.get(x);
			List<Cell> endCell = this.board.getPossibleDestinations(startCell.get(x));//get possible destinations from that starting cell
			System.out.printf("[%s] Possible destinations are ", board.getTurn().getType());
			for(int i = 0; i < endCell.size(); i++) {
				System.out.print("[" + (char)(endCell.get(i).getCoordinate().col + 'A') + (endCell.get(i).getCoordinate().row + 1) + "]");//show user all the possible destination cells
			}
			System.out.print(". Enter where you want to move: ");
			name = userInput.nextLine();								//get user input
			found = false;
			x = -1;
			while(!found) {												//keeps looping until user enters valid destination cell
				x++;
				if((int) (name.charAt(0) - 'A') == endCell.get(x).getCoordinate().col && (name.charAt(1) - '0' - 1) == endCell.get(x).getCoordinate().row) {//check if the user's destination is valid
					found = true;
					move = new Move(start, endCell.get(x));
				}
				if(!found && x == endCell.size() - 1) {					//if user input is not valid ask again
					System.out.println(name + " is an invalid destination.");
					System.out.printf("[%s] Possible destinations are ", board.getTurn().getType());
					for(int i = 0; i < endCell.size(); i++) {
						System.out.print("[" + (char)(endCell.get(i).getCoordinate().col + 'A') + (endCell.get(i).getCoordinate().row + 1) + "]");//show user all the possible destination cells
					}
					System.out.print(". Enter where you want to move: ");
					name = userInput.nextLine();						//get user input
					x = -1;
				}
			}
		}
    	return move;
    }
}
