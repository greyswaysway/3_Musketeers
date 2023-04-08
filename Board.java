package lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import lab2.Piece.Type;

public class Board {
    public int size = 5;

    // 2D Array of Cells for representation of the game board
    public final Cell[][] board = new Cell[size][size];

    private Piece.Type turn;
    private Piece.Type winner;

    /**
     * Create a Board with the current player turn set.
     */
    public Board() {
        this.loadBoard("Boards/Starter.txt");
    }

    /**
     * Create a Board with the current player turn set and a specified board.
     * @param boardFilePath The path to the board file to import (e.g. "Boards/Starter.txt")
     */
    public Board(String boardFilePath) {
        this.loadBoard(boardFilePath);
    }

    /**
     * Creates a Board copy of the given board.
     * @param board Board to copy
     */
    public Board(Board board) {
        this.size = board.size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = new Cell(board.board[row][col]);
            }
        }
        this.turn = board.turn;
        this.winner = board.winner;
    }

    /**
     * @return the Piece.Type (Muskeeteer or Guard) of the current turn
     */
    public Piece.Type getTurn() {
        return turn;
    }

    /**
     * Get the cell located on the board at the given coordinate.
     * @param coordinate Coordinate to find the cell
     * @return Cell that is located at the given coordinate
     */
    public Cell getCell(Coordinate coordinate) { // TODO
    	return board [coordinate.row][coordinate.col];//returns the row and column of the cell
    }

    /**
     * @return the game winner Piece.Type (Muskeeteer or Guard) if there is one otherwise null
     */
    public Piece.Type getWinner() {
        return winner;
    }

    /**
     * Gets all the Musketeer cells on the board.
     * @return List of cells
     */
    public List<Cell> getMusketeerCells() { // TODO
        List<Cell> musketList = new ArrayList<>();
        for(int i = 0; i < size; i++) {//goes through the entire array to check if the piece is a musketeer
        	for(int j = 0; j < size; j++) {
        		if(board[i][j].getPiece() instanceof Musketeer) {//adds it to the list if it is
        			musketList.add(board[i][j]);
        		}
        	}
        }
        return musketList;
    }

    /**
     * Gets all the Guard cells on the board.
     * @return List of cells
     */
    public List<Cell> getGuardCells() { // TODO
    	List<Cell> guardList = new ArrayList<>();
        for(int i = 0; i < size; i++) {//goes through the entire array to check if the piece is a guard
        	for(int j = 0; j < size; j++) {
        		if(board[i][j].getPiece() instanceof Guard) {//adds it to the list if it is
        			guardList.add(board[i][j]);
        		}
        	}
        }
        return guardList;
    }

    /**
     * Executes the given move on the board and changes turns at the end of the method.
     * @param move a valid move
     */
    public void move(Move move) { // TODO
    	board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col].setPiece(move.fromCell.getPiece());//set the destination piece to the starting cell's piece
    	board[move.fromCell.getCoordinate().row][move.fromCell.getCoordinate().col].removePiece();//removes the piece in the starting cell
    	if(turn.equals(Type.MUSKETEER)) {//changes the turn appropriately 
    		turn = Type.GUARD;
    	}
    	else if(turn.equals(Type.GUARD)){
    		turn = Type.MUSKETEER;
    	}
    }

    /**
     * Undo the move given.
     * @param move Copy of a move that was done and needs to be undone. The move copy has the correct piece info in the
     *             from and to cell fields. Changes turns at the end of the method.
     */
    public void undoMove(Move move) { // TODO
    	if(turn.equals(Type.GUARD)) {//if it is currently the guard's turn, it would set the starting cell of the move to have a musketeer and the destination cell of the move to have a guard
    		board[move.fromCell.getCoordinate().row][move.fromCell.getCoordinate().col].setPiece(new Musketeer());
        	board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col].setPiece(new Guard());
    		turn = Type.MUSKETEER;//switches turns after
    	}
    	else if(turn.equals(Type.MUSKETEER)){////if it is currently the musketeer's turn, it would set theit would set the starting cell of the move to have a guard and the destination cell of the move to have a no piece
    		board[move.fromCell.getCoordinate().row][move.fromCell.getCoordinate().col].setPiece(new Guard());
        	board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col].removePiece();
    		turn = Type.GUARD;//switches turns after
    	}
    }

    /**
     * Checks if the given move is valid. Things to check:
     * (1) the toCell is next to the fromCell
     * (2) the fromCell piece can move onto the toCell piece.
     * @param move a move
     * @return     True, if the move is valid, false otherwise
     */
    public Boolean isValidMove(Move move) { // TODO
    	if(!(move.fromCell.getPiece().getType().equals(turn))) {//check if the piece currently moving is on it's turn. return false if it is not it's turn
    		return false;
    	}
    	if(move.fromCell.getCoordinate().row == move.toCell.getCoordinate().row + 1 || move.fromCell.getCoordinate().row == move.toCell.getCoordinate().row - 1) {//check if the destination cell is to the left or the right of the starting cell
    		if(move.fromCell.getCoordinate().col == move.toCell.getCoordinate().col) {
    			return move.fromCell.getPiece().canMoveOnto(move.toCell);//if it is directly to the right or the left, it will check if that move is possible for the starting cell's piece type
    		}
    	}
    	else if(move.fromCell.getCoordinate().row == move.toCell.getCoordinate().row) {//check if the destination cell is to above or below the starting cell
    		if(move.fromCell.getCoordinate().col == move.toCell.getCoordinate().col + 1 || move.fromCell.getCoordinate().col == move.toCell.getCoordinate().col - 1) {
    			return move.fromCell.getPiece().canMoveOnto(move.toCell);//if it is directly to above or below, it will check if that move is possible for the starting cell's piece type
    		}
    	}
        return false;//return false if it is not beside the starting cell
    }

    /**
     * Get all the possible cells that have pieces that can be moved this turn.
     * @return      Cells that can be moved from the given cells
     */
    public List<Cell> getPossibleCells() { // TODO
    	List<Cell> posList = new ArrayList<>();
    	if(turn.equals(Type.GUARD)) {//if it is the guard's turn will add check all the locations of the guards and see if any move can be made from them
    		for(Cell curCell : getGuardCells()) {
    			if(getPossibleDestinations(curCell).size() != 0) {
    				posList.add(curCell);//if there are possible moves the current cell will be added to the list
    			}
    		}
    	}
    	else if(turn.equals(Type.MUSKETEER)){//if it is the musketeer's turn will add check all the locations of the musketeers and see if any move can be made from them
    		for(Cell curCell : getMusketeerCells()) {
    			if(getPossibleDestinations(curCell).size() != 0) {
    				posList.add(curCell);//if there are possible moves the current cell will be added to the list
    			}
    		}
    	}
        return posList;
    }

    /**
     * Get all the possible cell destinations that is possible to move to from the fromCell.
     * @param fromCell The cell that has the piece that is going to be moved
     * @return List of cells that are possible to get to
     */
    public List<Cell> getPossibleDestinations(Cell fromCell) { // TODO
    	List<Cell> posDest = new ArrayList<>();
    	int x = fromCell.getCoordinate().row;
    	int y = fromCell.getCoordinate().col;
    	if(x != size - 1 && fromCell.getPiece().canMoveOnto(board[x + 1][y])) {//check the cell below the the starting cell to see if it can move there and add it to the list if it can
			posDest.add(board[x + 1][y]);
		}
		if(x != 0 && fromCell.getPiece().canMoveOnto(board[x - 1][y])) {//check the cell above the the starting cell to see if it can move there and add it to the list if it can
			posDest.add(board[x - 1][y]);
		}
		if(y != size - 1 && fromCell.getPiece().canMoveOnto(board[x][y + 1])) {//check the cell to the right of the the starting cell to see if it can move there and add it to the list if it can
			posDest.add(board[x][y + 1]);
		}
		if(y != 0 && fromCell.getPiece().canMoveOnto(board[x][y - 1])) {//check the cell to the left of the the starting cell to see if it can move there and add it to the list if it can
			posDest.add(board[x][y - 1]);
		}
    	return posDest;
    }

    /**
     * Get all the possible moves that can be made this turn.
     * @return List of moves that can be made this turn
     */
    public List<Move> getPossibleMoves() { // TODO
    	List<Cell> startCells = getPossibleCells();//get possible starting cells
    	List<Move> posMove = new ArrayList<>();
    	for(Cell curCell : startCells) {
    		for(Cell toCell : getPossibleDestinations(curCell)) {//get all the destinations that exist for each starting cell
    			Move newMove = new Move(curCell, toCell);//turn them into moves and add them to the list
    			posMove.add(newMove);
    		}
    	}
        return posMove;
    }

    /**
     * Checks if the game is over and sets the winner if there is one.
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOver() { // TODO
    	List<Cell> musket = getMusketeerCells();//check if musketeer are all in same row
    	if(musket.get(0).getCoordinate().row == musket.get(1).getCoordinate().row) {
    		if(musket.get(0).getCoordinate().row == musket.get(2).getCoordinate().row) {
    			this.winner = Type.GUARD;
    			return true;
    		}
    	}
    	if(musket.get(0).getCoordinate().col == musket.get(1).getCoordinate().col) {//check if musketeer are all in same col
    		if(musket.get(0).getCoordinate().col == musket.get(2).getCoordinate().col) {
    			this.winner = Type.GUARD;
    			return true;
    		}
    	}
    	if(getPossibleMoves().size() == 0) {//check if the current user can make a move
    		this.winner = Type.MUSKETEER;
			return true;
		}
    	return false;//return false otherwise
    }

    /**
     * Saves the current board state to the boards directory
     */
    public void saveBoard() {
        String filePath = String.format("boards/%s.txt",
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        File file = new File(filePath);

        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row: board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell: row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", filePath);
        }
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("  | A B C D E\n");
        boardStr.append("--+----------\n");
        for (int i = 0; i < size; i++) {
            boardStr.append(i + 1).append(" | ");
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                boardStr.append(cell).append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    /**
     * Loads a board file from a file path.
     * @param filePath The path to the board file to load (e.g. "Boards/Starter.txt")
     */
    private void loadBoard(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.printf("File at %s not found.", filePath);
            System.exit(1);
        }

        turn = Piece.Type.valueOf(scanner.nextLine().toUpperCase());

        int row = 0, col = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.trim().split(" ");
            for (String piece: pieces) {
                Cell cell = new Cell(new Coordinate(row, col));
                switch (piece) {
                    case "O" -> cell.setPiece(new Guard());
                    case "X" -> cell.setPiece(new Musketeer());
                    default -> cell.setPiece(null);
                }
                this.board[row][col] = cell;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        scanner.close();
        System.out.printf("Loaded board from %s.\n", filePath);
    }
}
