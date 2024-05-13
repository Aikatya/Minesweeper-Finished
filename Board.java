import java.util.Random;

public class Board {

    private int rows;
    private int columns;
    private int nrOfMines;
    private Tile[][] playboard;
    private int nrOfOpenTiles;

    public Board(Difficulty difficulty) {
        switch(difficulty) {
            case easy:
                rows = 8;
                columns = 8;
                nrOfMines = 10;
                break;
            case medium:
                rows = 16;
                columns = 16;
                nrOfMines = 40;
                break;
            case hard:
                rows = 16;
                columns = 30;
                nrOfMines = 99;
                break;
        }

        nrOfOpenTiles=0;
        playboard = new Tile[rows][columns];
        populateBoard();
    }

    public Board(int[] boardProperties) {
        this.rows = boardProperties[0];
        this.columns = boardProperties[1];
        this.nrOfMines = boardProperties[2];

        nrOfOpenTiles=0;
        playboard = new Tile[rows][columns];
        populateBoard();
    }

    //	places mines in random locations over the board, populates other cells with corresponding tiles
    private void populateBoard() {
        Random rn = new Random();
        for (int i=0; i<nrOfMines; i++){
            int x = rn.nextInt(rows-1);
            int y = rn.nextInt(columns-1);
            if (!(playboard[x][y] instanceof Mine)){
                playboard[x][y] = new Mine();
            }
            else {i--;}
        }
        boolean teleportPlaced=false;
        while (!teleportPlaced){
            int x = rn.nextInt(rows-1);
            int y = rn.nextInt(columns-1);
            if (!(playboard[x][y] instanceof Mine)){
                playboard[x][y] = new Teleport();
                teleportPlaced = true;
            }
        }
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                if (!(playboard[i][j] instanceof Mine) && !(playboard[i][j] instanceof Teleport)){
                    int neighbourMines = findMines(i, j);
                    if (neighbourMines == 0){
                        playboard[i][j] = new Empty();
                    }
                    else {
                        playboard[i][j] = new Numbered(neighbourMines);
                    }
                }
            }
        }
    }

    //	finds all mines neighbouring chosen tile
    private int findMines(int x, int y){
        int neighbourMines = 0;
        for (int i=x-1; i<=x+1;i++) {
            for (int j=y-1; j<=y+1;j++) {
                if (i>=0 && i<rows && j>=0 && j<columns) {
                    if (playboard[i][j] instanceof Mine){
                        neighbourMines++;
                    }
                }
            }
        }
        return neighbourMines;
    }

    // updates field if first tile selected was a mine
    public void firstTimeRule(int[] coordinates) {
        if (playboard[coordinates[0]][coordinates[1]] instanceof Mine){
            Random rn = new Random();
            int x = 0;
            int y = 0;
            boolean success = false;
//			picks a new location for the mine
            while (!success) {
                x = rn.nextInt(rows - 1);
                y = rn.nextInt(columns - 1);
                if (!(playboard[x][y] instanceof Mine) && !(playboard[x][y] instanceof Teleport)) {
                    playboard[x][y] = new Mine();
                    success = true;
                }
            }
            playboard[coordinates[0]][coordinates[1]] = new Empty();
            updateAroundTile(coordinates[0], coordinates[1]); //updates around original mine location
            updateAroundTile(x, y); //updates around new mine location
        }
    }
    //	updates all tiles neighbouring given tile
    private void updateAroundTile(int x, int y){
        for (int i=x-1; i<=x+1;i++) {
            for (int j=y-1; j<=y+1;j++) {
                int neighbours = findMines(i, j);
                if (i>=0 && i<rows && j>=0 && j<columns) {
                    if (!(playboard[i][j] instanceof Mine) && !(playboard[i][j] instanceof Teleport)) {
                        if (neighbours == 0) {
                            playboard[i][j] = new Empty();
                        } else {
                            playboard[i][j] = new Numbered(neighbours);
                        }
                    }
                }
            }
        }
    }
    public void incrementOpenTiles(){
        nrOfOpenTiles++;
    }

    //	getters ----------------------------
    public Tile[][] getPlayboard() {
        return this.playboard;
    }

    public int getNrOfOpenTiles() {
        return this.nrOfOpenTiles;
    }
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getNrOfMines() {
        return nrOfMines;
    }
}
