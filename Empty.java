public class Empty extends Tile{
    @Override
    public void printTile() {
        if (!open)
            super.printTile();
        else
            System.out.print('-');
    }

    @Override
    public boolean click(Board gameboard, int x, int y) {
        super.click(gameboard, x, y);
        openAroundEmpty(gameboard, x, y);
        return false;
    }
    private void openAroundEmpty(Board gameBoard, int x, int y){
        int rows = gameBoard.getRows();
        int columns = gameBoard.getColumns();

        for (int i=x-1; i<=x+1;i++) {
            for (int j=y-1; j<=y+1;j++) {
                if (i>=0 && i<rows && j>=0 && j<columns) {
                    if (!gameBoard.getPlayboard()[i][j].isOpen()){
                        gameBoard.getPlayboard()[i][j].click(gameBoard, i, j);
                    }
                }
            }
        }
    }
}
