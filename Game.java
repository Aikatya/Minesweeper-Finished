public class Game {
    private Board gameBoard;
    private Visual UI;
    private boolean gameEnd;
    private boolean gameWon;

    public Game() {
        gameEnd = false;
        gameWon = false;
        UI = new Visual();

        Difficulty difficulty = UI.getBoardSize();
        if (difficulty != null){
            gameBoard = new Board(difficulty);
        }
        else {
            int[] boardProperties = UI.chooseSettings();
            gameBoard = new Board(boardProperties);
        }
        UI.displayInstructions();
        UI.displayBoard(gameBoard);
        this.gameLoop();
    }

    //	method that calls other methods during game operation
    private void gameLoop() {
        while (!gameEnd){
            int[] selectedTile = UI.readCoordinates(gameBoard);
            String nextAction = UI.readOperation();
            if (nextAction.equals("flag")){
                gameBoard.getPlayboard()[selectedTile[0]][selectedTile[1]].flag();
            }
            else {
                if (gameBoard.getNrOfOpenTiles()==0){
                    gameBoard.firstTimeRule(selectedTile);
                }
                gameEnd = gameBoard.getPlayboard()[selectedTile[0]][selectedTile[1]].click(gameBoard, selectedTile[0], selectedTile[1]);
            }
            UI.displayBoard(gameBoard);
            if (gameBoard.getNrOfOpenTiles() == gameBoard.getColumns() * gameBoard.getRows() - gameBoard.getNrOfMines()){
                gameEnd=true;
                gameWon=true;
            }
        }
        endGame(gameWon);
    }

    public void endGame(boolean won) {
        UI.endMessage(won);
    }

}
