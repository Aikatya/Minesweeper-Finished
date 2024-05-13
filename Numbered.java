public class Numbered extends Tile{
    private int neighbourMines;
    public Numbered(int neighbourMines) {
        super();
        this.neighbourMines = neighbourMines;
    }
    @Override
    public void printTile() {
        if (!open)
            super.printTile();
        else
            System.out.print(neighbourMines);
    }

    @Override
    public boolean click(Board gameboard, int x, int y) {
        return super.click(gameboard, x, y);
    }
}
