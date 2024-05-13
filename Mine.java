public class Mine extends Tile{
    @Override
    public void printTile() {
        if (!open)
            super.printTile();
        else
            System.out.print('*');
    }

    @Override
    public boolean click(Board gameboard, int x, int y) {
        super.click(gameboard, x, y);
        if (!flagged)
            openAllMines(gameboard);
        return !flagged;
    }

    private static void openAllMines(Board gameboard) {
        for (int i=0; i< gameboard.getRows(); i++){
            for (int j=0; j< gameboard.getColumns(); j++){
                if (gameboard.getPlayboard()[i][j] instanceof Mine){
                    gameboard.getPlayboard()[i][j].setOpen();
                }
            }
        }
    }

}
