package lab2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import lab2.Piece.Type;
import lab2.Exceptions.InvalidMoveException;

public class BoardTest {

    private Board board;

    @Before
    public void setup() {
        this.board = new Board();
    }

    @Test
    public void testGetCell() {
        Cell cell = board.getCell(new Coordinate(1, 4));
        Assert.assertNotNull(cell.getPiece());
    }
    
    @Test
    public void testGetMusketeerCell() {//test GetMusketeerCell
        List<Cell> cells = board.getMusketeerCells();
        String comp = "[" + (char)(cells.get(0).getCoordinate().row + 'A') + " " + cells.get(0).getCoordinate().col + "][" + (char)(cells.get(1).getCoordinate().row + 'A') + " " + cells.get(1).getCoordinate().col + "][" + (char)(cells.get(2).getCoordinate().row + 'A') + " " + cells.get(2).getCoordinate().col + "]";
        assertEquals("[A 4][C 2][E 0]", comp);
    }
    @Test
    public void testGetGuardCell() {//only checking the 3 guard cells 22 cells are too much to check
        List<Cell> cells = board.getGuardCells();
        String comp = "[" + (char)(cells.get(1).getCoordinate().row + 'A') + " " + cells.get(1).getCoordinate().col + "][" + (char)(cells.get(12).getCoordinate().row + 'A') + " " + cells.get(12).getCoordinate().col + "][" + (char)(cells.get(20).getCoordinate().row + 'A') + " " + cells.get(20).getCoordinate().col + "]";
        assertEquals("[A 1][C 4][E 3]", comp);
    }
    @Test
    public void testValidMove() {//test valid move
        Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        assertEquals( true, this.board.isValidMove(move));
    }
    @Test
    public void testMove() {//test move
        Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        board.move(move);
        List<Cell> cells = board.getMusketeerCells();
        String comp = "[" + (char)(cells.get(0).getCoordinate().row + 'A') + " " + cells.get(0).getCoordinate().col + "][" + (char)(cells.get(1).getCoordinate().row + 'A') + " " + cells.get(1).getCoordinate().col + "][" + (char)(cells.get(2).getCoordinate().row + 'A') + " " + cells.get(2).getCoordinate().col + "]";
        assertEquals("[B 4][C 2][E 0]", comp);
    }
    @Test
    public void testUndoMove() {//test undo move
        Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        board.move(move);
        board.undoMove(move);
        List<Cell> cells = board.getMusketeerCells();
        String comp = "[" + (char)(cells.get(0).getCoordinate().row + 'A') + " " + cells.get(0).getCoordinate().col + "][" + (char)(cells.get(1).getCoordinate().row + 'A') + " " + cells.get(1).getCoordinate().col + "][" + (char)(cells.get(2).getCoordinate().row + 'A') + " " + cells.get(2).getCoordinate().col + "]";
        assertEquals("[A 4][C 2][E 0]", comp);
    }
    @Test
    public void testPosCell() {//test possibleCells
    	List<Cell> cells = board.getPossibleCells();
    	String comp = "[" + (char)(cells.get(0).getCoordinate().row + 'A') + " " + cells.get(0).getCoordinate().col + "][" + (char)(cells.get(1).getCoordinate().row + 'A') + " " + cells.get(1).getCoordinate().col + "][" + (char)(cells.get(2).getCoordinate().row + 'A') + " " + cells.get(2).getCoordinate().col + "]";
    	assertEquals("[A 4][C 2][E 0]", comp);
    }
    @Test
    public void testPosDest() {//test possibleDestination
    	List<Cell> cells = board.getPossibleDestinations(board.getCell(new Coordinate(0, 4)));
    	String comp = "[" + (char)(cells.get(0).getCoordinate().row + 'A') + " " + cells.get(0).getCoordinate().col + "][" + (char)(cells.get(1).getCoordinate().row + 'A') + " " + cells.get(1).getCoordinate().col + "]";
    	assertEquals("[B 4][A 3]", comp);
    }
    @Test
    public void testPosMove() {//test possibleMoves
    	List<Move> cells = board.getPossibleMoves();
    	List<Move> comp = new ArrayList<>();
    	comp.add(new Move(board.getCell(new Coordinate(0, 4)), board.getCell(new Coordinate(1, 4))));
    	comp.add(new Move(board.getCell(new Coordinate(0, 4)), board.getCell(new Coordinate(0, 3))));
    	comp.add(new Move(board.getCell(new Coordinate(2, 2)), board.getCell(new Coordinate(3, 2))));
    	comp.add(new Move(board.getCell(new Coordinate(2, 2)), board.getCell(new Coordinate(1, 2))));
    	comp.add(new Move(board.getCell(new Coordinate(2, 2)), board.getCell(new Coordinate(2, 3))));
    	comp.add(new Move(board.getCell(new Coordinate(2, 2)), board.getCell(new Coordinate(2, 1))));
    	comp.add(new Move(board.getCell(new Coordinate(4, 0)), board.getCell(new Coordinate(3, 0))));
    	comp.add(new Move(board.getCell(new Coordinate(4, 0)), board.getCell(new Coordinate(4, 1))));
    	assertEquals(comp.toString(), cells.toString());
    }
    @Test
    public void testPosMove2() {//test possibleMoves again
    	Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        board.move(move);
        List<Move> cells = board.getPossibleMoves();
    	List<Move> comp = new ArrayList<>();
    	comp.add(new Move(board.getCell(new Coordinate(0, 3)), board.getCell(new Coordinate(0, 4))));
    	assertEquals(comp.toString(), cells.toString());
    }
    @Test
    public void testIsGameOverGuard() {//test if gameOver for guard victory works
    	Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(0, 3));
        toCell = board.getCell(new Coordinate(0, 4));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(1, 4));
        toCell = board.getCell(new Coordinate(2, 4));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(0, 4));
        toCell = board.getCell(new Coordinate(1, 4));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(4, 0));
        toCell = board.getCell(new Coordinate(3, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(4, 1));
        toCell = board.getCell(new Coordinate(4, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(3, 0));
        toCell = board.getCell(new Coordinate(2, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        assertEquals(true, this.board.isGameOver());
    }
    @Test
    public void testIsGameOverTypeGuard() {//test if if winner is correctly set to guard on a guard win
    	Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(0, 3));
        toCell = board.getCell(new Coordinate(0, 4));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(1, 4));
        toCell = board.getCell(new Coordinate(2, 4));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(0, 4));
        toCell = board.getCell(new Coordinate(1, 4));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(4, 0));
        toCell = board.getCell(new Coordinate(3, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(4, 1));
        toCell = board.getCell(new Coordinate(4, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(3, 0));
        toCell = board.getCell(new Coordinate(2, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        this.board.isGameOver();
        assertEquals(Type.GUARD, this.board.getWinner());
    }
    @Test
    public void testIsGameOverMusket() {//test gameOver for musketeer win
    	this.board = new Board("Boards/NearEnd.txt");
    	Cell fromCell = board.getCell(new Coordinate(3, 2));
        Cell toCell = board.getCell(new Coordinate(2, 2));
        Move move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(3, 1));
        toCell = board.getCell(new Coordinate(2, 1));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(4, 2));
        toCell = board.getCell(new Coordinate(4, 3));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(2, 1));
        toCell = board.getCell(new Coordinate(2, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(3, 0));
        toCell = board.getCell(new Coordinate(4, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(4, 1));
        toCell = board.getCell(new Coordinate(3, 1));
        move = new Move(fromCell, toCell);
        board.move(move);
        assertEquals(true, this.board.isGameOver());
    }
    @Test
    public void testIsGameOverTypeMusket() {//test that winner is correctly set to musketeer in a win
    	this.board = new Board("Boards/NearEnd.txt");
    	Cell fromCell = board.getCell(new Coordinate(3, 2));
        Cell toCell = board.getCell(new Coordinate(2, 2));
        Move move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(3, 1));
        toCell = board.getCell(new Coordinate(2, 1));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(4, 2));
        toCell = board.getCell(new Coordinate(4, 3));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(2, 1));
        toCell = board.getCell(new Coordinate(2, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(3, 0));
        toCell = board.getCell(new Coordinate(4, 0));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(4, 1));
        toCell = board.getCell(new Coordinate(3, 1));
        move = new Move(fromCell, toCell);
        board.move(move);
        this.board.isGameOver();
        assertEquals(Type.MUSKETEER, this.board.getWinner());
    }
    @Test
    public void testGuardCanMoveOntoTrue() {//test guardCanMoveOnto true case
    	Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        board.move(move);
    	fromCell = board.getCell(new Coordinate(0, 3));
    	assertEquals(true, fromCell.getPiece().canMoveOnto(board.getCell(new Coordinate(0, 4))));
    }
    @Test
    public void testGuardCanMoveOntoFalse() {//test guardCanMoveOnto false case
    	Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        board.move(move);
    	fromCell = board.getCell(new Coordinate(0, 1));
    	assertEquals(false, fromCell.getPiece().canMoveOnto(board.getCell(new Coordinate(0, 2))));
    }
    @Test
    public void testMusketeerCanMoveOntoTrue() {//test musketeerCanMoveOnto true case
    	Cell fromCell = board.getCell(new Coordinate(0, 4));
    	assertEquals(true, fromCell.getPiece().canMoveOnto(board.getCell(new Coordinate(1, 4))));
    }
    @Test
    public void testMusketeerCanMoveOntoFalse() {//test musketeerCanMoveOnto false case
    	Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(0, 3));
        toCell = board.getCell(new Coordinate(0, 4));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(1, 4));
        toCell = board.getCell(new Coordinate(0, 4));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(2, 4));
        toCell = board.getCell(new Coordinate(1, 4));
        move = new Move(fromCell, toCell);
        board.move(move);
        fromCell = board.getCell(new Coordinate(0, 4));
    	assertEquals(false, fromCell.getPiece().canMoveOnto(board.getCell(new Coordinate(0, 4))));
    }
    @Test
    public void testgetMoveMusketRandom(){//test randomMove for musketeer
    	Agent currentAgent = new RandomAgent(board);
    	Move move = currentAgent.getMove();
    	System.out.println(move);
    }
    @Test
    public void testgetMoveGuardRandom(){//test randomMove for guard
    	Cell fromCell = board.getCell(new Coordinate(0, 4));
        Cell toCell = board.getCell(new Coordinate(1, 4));
        Move move = new Move(fromCell, toCell);
        board.move(move);
    	Agent currentAgent = new RandomAgent(board);
    	move = currentAgent.getMove();
    	List<Move> comp = new ArrayList<>();
    	comp.add(new Move(board.getCell(new Coordinate(0, 3)), board.getCell(new Coordinate(0, 4))));
    	assertEquals(comp.toString(), "[" + move + "]");
    }
    @Test
    public void testgetMoveMusketeerHuman1(){//test humanMove for musketeer
    	Agent currentAgent = new HumanAgent(board);
    	Move move = currentAgent.getMove();
    	System.out.println(move);
    	move = currentAgent.getMove();
    	System.out.println(move);
    }
}
