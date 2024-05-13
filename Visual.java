import java.util.Scanner;

public class Visual {
    public void displayBoard(Board board) {
        Tile[][] array = board.getPlayboard();
        for (Tile[] row: array){
            for (Tile element: row){
                element.printTile();
            }
            System.out.println();
        }
//        System.out.println();
    }

    public void endMessage(boolean won) {
        if (won){
            System.out.println("You win!");
        }
        else {
            System.out.println("You lose");
        }
    }

    public void displayInstructions() {
        System.out.println("Instructions: ...");
    }

    //	asks for and returns desired coordinates
    public int[] readCoordinates(Board gameBoard){
        int[] numbers = new int[2];
        Scanner myObj = new Scanner(System.in);
        System.out.println("What x coordinate do you want to work with?");
        try{
            numbers[0] = myObj.nextInt();
        }
        catch (Exception e) {
            System.out.println("Please enter an integer");
            return this.readCoordinates(gameBoard);
        }
        System.out.println("What y coordinate do you want to work with?");
        try{
            numbers[1] = myObj.nextInt();
        } catch (Exception e) {
            System.out.println("Please enter an integer");
            return this.readCoordinates(gameBoard);
        }
        while (numbers[0]>=gameBoard.getRows()||numbers[1]>=gameBoard.getColumns()||numbers[0]<0||numbers[1]<0){
            System.out.println("The chosen index is not on the board");
            numbers = readCoordinates(gameBoard);
        }
        return numbers;
    }

    //	asks what operation needs to be performed, returns String or calls itself if input invalid
    public String readOperation(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Would you like to flag or open a tile?");

        String operation = myObj.nextLine();

        if (!operation.equals("flag") && !operation.equals("open")){
            System.out.println("Please enter a valid operation");
            operation = this.readOperation();
        }
        return operation;
    }

    //	queries which difficulty user wants
    public Difficulty getBoardSize() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("What difficulty would you like (easy, medium, hard or custom)?");

        String operation = myObj.nextLine();

        return switch (operation) {
            case "easy" -> Difficulty.easy;
            case "medium" -> Difficulty.medium;
            case "hard" -> Difficulty.hard;
            default -> null;
        };
    }

    //	allows user to choose own board settings
    public int[] chooseSettings() {
        int[] numbers = new int[3];
        Scanner myObj = new Scanner(System.in);
        System.out.println("How many rows would you like?");
        try{
            numbers[0] = myObj.nextInt();
            if (numbers[0]<=0){
                System.out.println("Please choose a positive number");
                return this.chooseSettings();
            }
            else if (numbers[0]>200){
                System.out.println("Are you sure that isn't too many?");
                return this.chooseSettings();
            }
        }
        catch (Exception e) {
            System.out.println("Please enter an integer");
            return this.chooseSettings();
        }
        System.out.println("How many columns would you like ?");
        try{
            numbers[1] = myObj.nextInt();
            if (numbers[1]<=0){
                System.out.println("Please choose a positive number");
                return this.chooseSettings();
            }
            else if (numbers[1]>200){
                System.out.println("Are you sure that isn't too many?");
                return this.chooseSettings();
            }
        } catch (Exception e) {
            System.out.println("Please enter an integer");
            return this.chooseSettings();
        }
        System.out.println("How many mines would you like ?");
        try{
            numbers[2] = myObj.nextInt();
            if (numbers[2]<=0){
                System.out.println("Please choose a positive number");
                return this.chooseSettings();
            }
            else if (numbers[2]>(numbers[0]*numbers[1])){
                System.out.println("This many mines will not fit");
                return this.chooseSettings();
            }
        } catch (Exception e) {
            System.out.println("Please enter an integer");
            return this.chooseSettings();
        }
        return numbers;
    }
}
