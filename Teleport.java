
import java.util.Random;
public class Teleport extends Tile{
    @Override
    public void printTile() {
        if (!open)
            super.printTile();
        else
            System.out.print('@');
    }

    @Override
    public boolean click(Board gameboard, int x, int y) {
        super.click(gameboard, x, y);
        if (! flagged){
            Random rn = new Random();
            boolean success = false;
            while (!success) {
                int i = rn.nextInt(gameboard.getRows() - 1);
                int j = rn.nextInt(gameboard.getColumns() - 1);
                if (!(gameboard.getPlayboard()[i][j] instanceof Mine)
                        && !(gameboard.getPlayboard()[i][j] instanceof Teleport)
                        && !(gameboard.getPlayboard()[i][j].isOpen())){
                    gameboard.getPlayboard()[i][j].click(gameboard, i, j);
                    success = true;
                }
            }
        }
        return false;
    }
}
