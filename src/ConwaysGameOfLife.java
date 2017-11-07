import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Boolean;
import java.util.Scanner;

/**
 * Conway's Game of Life:
 * The universe is a finite two­dimensional grid of square cells (square matrix). Each cell has 2 possible states, alive
 * or dead. Every cell interacts with its neighbors, which are the cells that are horizontally, vertically, or diagonally
 * adjacent. Therefore, a cell can have up to eight neighbors. At each step in time, the following transitions occur:
 *
 * 1. Any live cell with fewer than two live neighbors dies, as if caused by under­population.
 * 2. Any live cell with two or three live neighbors lives on to the next generation.
 * 3. Any live cell with more than three live neighbors dies, as if by over­population.
 * 4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 *
 * @author Ari Bernstein
 * @since 10/20/2017
 */

public class ConwaysGameOfLife {

    private int sideLength = 0; /** as gameArray is a SQUARE 2D array, the lengths of both sides are synonymous */
    private Cell[][] initialGameArray; /**2D array representing the living and dead elements at the start of Conway's Game of Life*/
    private Cell[][] finalGameArray; /**2D array representing the living and dead elements at the end of Conway's Game of Life */
    private String filePath; /**the filepath of the text file from which we construct gameArray*/

    private final char alive = '1'; //state of cells
    private final char dead = '0';

    private final int fileNotFoundErrorCode = 1;
    private final int invalidFile = 2;

    /**
     * Getter for initialGameArray
     * @return initialGameArray
     */
    public Cell[][] getInitialGameArray() {
        return initialGameArray;
    }

    /**
     * Getter for finalGameArray
     * @return finalGameArray
     */
    public Cell[][] getFinalGameArray() {
        return finalGameArray;
    }

    /**
     * Constructor for Conway's Game of Life
     * @param filePath the file path of the text file from which we construct initialGameArray
     */
    public ConwaysGameOfLife(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Instantiates 2D Array gameArray from inputted file
     * Pre-condition: file is correctly formatted - consists of lines of Cell objects where the number of cells per line
     * are both equal to each other, and equal to the number of lines
     */
    private void instantiateGameArray() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));

            String line; //Info from line
            line = br.readLine();
            sideLength = line.length();

            this.initialGameArray = new Cell[sideLength][sideLength]; // (Pre-Condition: 2d array is square.)
            finalGameArray = new Cell[sideLength][sideLength]; // (Pre-Condition: 2d array is square.)

            int lineNum = 0; //keeps track of the line to add integers to the array
            while (line != null) { //populate Array with Cell objects
                for(int i=0; i < line.length(); i++) {

                    if(line.charAt(i) == '1' || line.charAt(i) == '0') { //Checks that all inputted characters are either '1', or '0'
                        Cell newCell = new Cell(line.charAt(i));
                        initialGameArray[i][lineNum] = newCell;
                    }
                    else {
                        System.out.println("Invalid file, at least one character is not '1' or '0'.");
                        System.exit(invalidFile);
                    }

                }
                lineNum ++;
                line = br.readLine();
            }
            br.close();
        }
        catch(FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("File not found!");
            System.exit(fileNotFoundErrorCode);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Prints the cells object states as grid
     * @return string representing the cells object states as grid
     * @param arr array of Cell objects
     */
    private String displayCells(Cell[][] arr) {
        String display = "";

        int depth = 0;
        while (depth < sideLength) {
            for(int i = 0; i < sideLength; i++) {
                display += arr[i][depth].getState();
            }
            depth++;
            display += "\n";
        }
        return display;
    }

    /**
     * Helper function for neighborIsAlive that tests whether or not neighbors exist in 2D array
     * @param row neighboring row of current index of gameArray
     * @param column neighvorign column of current index in gameArray
     * @return True if neighbor exists, false if not
     */
    private Boolean legalNeighbor(int row, int column) {
        if (row < 0 || row  >= sideLength || column < 0 || column >= sideLength) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Helper function for findNeighbors that determines whether or not to increment the number of neighbors
     * uses legalNeighbor method. Primary use is so that there is only one repeated function call in findNeighbors
     * @param row current index of gameArray row (length)
     * @param column current index of gameArray column (height)
     * @return True if neighbors should be in
     */
    private Boolean neighborIsAlive(int row, int column) {
        if (legalNeighbor(row, column)){
            if(initialGameArray[row][column].getState() == alive) {
                return true;
            }
        }
        return false;
    }

    /**
     * Assigns the correct number of neighbors to a Cell object
     * @param row current index of gameArray row (length)
     * @param column current index of gameArray column (height)
     */
    private void setNeighbors(int row, int column) {
        int livingNeighbors = 0;

        if (neighborIsAlive(row-1,column-1)) { //Checks top-left neighbor
            livingNeighbors ++;
        }

        if (neighborIsAlive(row  ,column -1)) { //Checks left neighbor
            livingNeighbors ++;
        }

        if (neighborIsAlive(row + 1,column - 1)) {  //Checks bottom-left neighbor
            livingNeighbors ++;
        }

        if (neighborIsAlive(row -1 ,column)) {  //Checks top neighbor
            livingNeighbors ++;
        }

        if (neighborIsAlive(row + 1,column)) {  //Checks bottom neighbor
            livingNeighbors ++;
        }

        if (neighborIsAlive(row - 1,column + 1)) {  //Checks top-right neighbor
            livingNeighbors ++;
        }

        if (neighborIsAlive(row ,column + 1)) { //Checks right neighbor
            livingNeighbors ++;
        }

        if (neighborIsAlive(row+1,column+1)) { //Checks bottom-right neighbor
            livingNeighbors ++;
        }
        initialGameArray[row][column].setNeighbors(livingNeighbors);
    }

    /**
     * Plays Conway's Game of Life!
     */
    private void playGame(int numGenerations) {

        System.out.println("Initial Matrix:\n" + displayCells(initialGameArray));

        for(int i = 0; i<numGenerations; i ++) {
            for (int row = 0; row < initialGameArray.length; row++) { //Iterates through rows of gameArray
                for (int column = 0; column < initialGameArray.length; column++) { //Iterates through columns of gameArray
                    setNeighbors(row, column);

                    int liveNeighbors = initialGameArray[row][column].getLivingNeighbors(); //Assigns the number of neighbors surrounding this point

                    finalGameArray[row][column] = new Cell(dead); //instantiates cell in finalGameArray

                    if (initialGameArray[row][column].getState() == alive) { //if cell is currently living:
                        if (liveNeighbors < 2 || liveNeighbors > 3) {
                            finalGameArray[row][column].setState(dead);
                        } else {
                            finalGameArray[row][column].setState(alive);
                        }
                    } else { //if cell is currently dead:
                        if (liveNeighbors == 3) {
                            finalGameArray[row][column].setState(alive);
                        }
                    }
                }
            }
            System.out.println("Generation " + (i + 1) + ":\n" + displayCells(finalGameArray) + "\n");

            initialGameArray = finalGameArray;
        }
    }

    public static void main(String [] args) {
        ConwaysGameOfLife game = new ConwaysGameOfLife(args[0]);
        game.instantiateGameArray();
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Welcome to Conway's Game of Life! Please enter the number of generations you would like to simulate: ");
        int generations = Integer.parseInt(sc1.next());
        game.playGame(generations);
    }

}