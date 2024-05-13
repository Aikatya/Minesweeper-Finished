import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @BeforeEach
    public void setUp() throws Exception {
    }

    //    Tests world generation with given parameters
    @Test
    public void testParameterGeneration() {
        Board b = new Board(new int[]{7, 5, 3});
        assertEquals(7, b.getRows(), "Wrong number of rows created");
        assertEquals(5, b.getColumns(), "Wrong number of columns created");
        assertEquals(3, b.getNrOfMines(), "Wrong number of mines added");
    }

    //    Tests world generation using Difficulty
    @Test
    public void testDifficultyGeneration() {
        Board b1 = new Board(Difficulty.easy);
        assertEquals(8, b1.getRows(), "Wrong number of rows created for easy difficulty");
        assertEquals(8, b1.getColumns(), "Wrong number of columns created for easy difficulty");
        assertEquals(10, b1.getNrOfMines(), "Wrong number of mines added for easy difficulty");

        Board b2 = new Board(Difficulty.medium);
        assertEquals(16, b2.getRows(), "Wrong number of rows created for medium difficulty");
        assertEquals(16, b2.getColumns(), "Wrong number of columns created for medium difficulty");
        assertEquals(40, b2.getNrOfMines(), "Wrong number of mines added for medium difficulty");

        Board b3 = new Board(Difficulty.hard);
        assertEquals(16, b3.getRows(), "Wrong number of rows created for hard difficulty");
        assertEquals(30, b3.getColumns(), "Wrong number of columns created for hard difficulty");
        assertEquals(99, b3.getNrOfMines(), "Wrong number of mines added for hard difficulty");
    }

    //    Tests mine allocation
    @Test
    public void testMineAllocation() {
        Board b = new Board(Difficulty.easy);
        int minesFound=0;
        for (int i=0; i<b.getRows(); i++){
            for (int j=0; j<b.getColumns(); j++){
                if (b.getPlayboard()[i][j] instanceof Mine){
                    minesFound++;
                }
            }
        }
        assertEquals(b.getNrOfMines(), minesFound, "Wrong number of mines created on the board");
    }

    //    Tests updating of numbered tiles
    @Test
    public void testNumbered() {
    }

    //    Tests flagging and unflagging of unopened tile
    @Test
    public void testUnopenedFlag() {
        Board b = new Board(new int[]{3, 4, 2});
        assertFalse(b.getPlayboard()[0][0].isFlagged(), "Tile starts out flagged");
        b.getPlayboard()[0][0].flag();
        assertTrue(b.getPlayboard()[0][0].isFlagged(), "Tile does not get flagged");
        b.getPlayboard()[0][0].flag();
        assertFalse(b.getPlayboard()[0][0].isFlagged(), "Tile does not get unflagged");
    }

    //    Tests outputting the board
    @Test
    public void testBoard() {
        Board b = new Board(new int[]{3, 5, 7});
        int rowsFound=0;
        Visual UI = new Visual();
        UI.displayBoard(b);
        PrintStream defaultSO = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String result="";
        System.setOut(new PrintStream(baos));
        try{
            UI.displayBoard(b);
            BufferedReader br = new BufferedReader(new StringReader(baos.toString()));
            result = br.readLine();
            while (result != null) {
                assertEquals("_____", result, "Wrong output when printing a row in the board");
                rowsFound++;
                result = br.readLine();
            }
            br.close();
        }
        catch(Exception e){
            System.setOut(defaultSO);
            System.out.println("Error while redirection System.out");
        }
        assertEquals(b.getRows(), rowsFound, "Wrong number of rows printed");
        System.setOut(defaultSO);
    }

    //    Tests opening a tile with neighbouring mines
    @Test
    public void testOpenNumbered() {
        Numbered n = new Numbered(5);
        assertFalse(n.isOpen());
        n.click(new Board(Difficulty.easy), 0, 0);
        assertTrue(n.isOpen());
    }

    //    Tests opening a tile with no neighbouring mines
    @Test
    public void testOpenEmpty() {
        Numbered n = new Numbered(5);
        assertFalse(n.isOpen());
        n.click(new Board(Difficulty.easy), 0, 0);
        assertTrue(n.isOpen());
    }

    //    Tests opening a mine
    @Test
    public void testOpenMine() {
        Numbered n = new Numbered(5);
        assertFalse(n.isOpen());
        n.click(new Board(Difficulty.easy), 0, 0);
        assertTrue(n.isOpen());
    }

    //    Tests flagging an opened tile
    @Test
    public void testFlagOpened() {
        Board b = new Board(new int[]{3, 4, 2});
//        b.getPlayboard()[0][1].setOpen();
        assertFalse(b.getPlayboard()[0][0].isFlagged(), "Tile starts out flagged");
        b.getPlayboard()[0][0].flag();
        assertTrue(b.getPlayboard()[0][0].isFlagged(), "Tile does not get flagged");
    }

    //    Tests first tile rule
    @Test
    public void testFirstTile() {
        Board b = new Board(new int[]{7, 5, 3});
        int[] originalMine = new int[2];
        for (int i=0; i<b.getRows(); i++){
            for (int j=0; j<b.getColumns(); j++){
                if (b.getPlayboard()[i][j] instanceof Mine && b.getNrOfOpenTiles()==0){
                    b.incrementOpenTiles();
                    originalMine[0] = i;
                    originalMine[1] = j;
                }
            }
        }
        assertTrue(b.getPlayboard()[originalMine[0]][originalMine[1]] instanceof Mine);
        b.firstTimeRule(originalMine);
        assertFalse(b.getPlayboard()[originalMine[0]][originalMine[1]] instanceof Mine);
    }

    //    Tests losing a game
    @Test
    public void testGameLost() {
    }

    //    Tests winning a game
    @Test
    public void testGameWon() {
    }

    //    Tests interactions with text commands
    @Test
    public void testTextCommands() {
    }

}
