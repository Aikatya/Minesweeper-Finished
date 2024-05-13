public abstract class Tile {
    protected boolean open;
    protected boolean flagged;

    public void printTile() {
        if (flagged)
            System.out.print('F');
        else if (!open)
            System.out.print('_');
    }

    public boolean click(Board gameboard, int x, int y){
        if (!flagged) {
            open = true;
            gameboard.incrementOpenTiles();
        }
        return false;
    }

    public void flag() {
        if (flagged){
            flagged = false;
        }
        else {
            flagged = true;
        }
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen() {
        open = true;
    }
}
