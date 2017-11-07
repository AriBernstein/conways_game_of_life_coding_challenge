public class Cell {
    /**
     * Cell:
     * Class for each cell of the square matrix. Used to access state (whether cell is alive or dead) and number of living
     * neighbors.
     *
     * @author Ari Bernstein
     * @since 10/20/2017
     */

    private char State; /**'1'means cell is living, '0' means cell is dead. Should never be any other character*/
    private int livingNeighbors = 0; /**Number of living cells in the (up to 8) surrounding spaces in the matrix.*/

    /**
     * Constructor for Cell. Sets State variable
     * @param State '1' for alive, '0' for dead
     */
    public Cell(char State) {
        this.State = State;
    }

    /**
     * Getter for State
     * @return '1' if Cell is alive, '2' if Cell is dead
     */
    public char getState() {
        return State;
    }

    /**
     * Setter for State
     * @param state '1' for alive, '0' for dead
     */
    public void setState(char state) {
        State = state;
    }

    /**
     * Getter for the number of living neighbors (which are computed by ConwaysGameOfLife.java)
     * @return number of living neighbors
     */
    public int getLivingNeighbors() {
        return livingNeighbors;
    }

    /**
     * Setter for the number of living neighbors (computed by ConwaysGameOfLife.java)
     * @param numNeighbors number of living neighbors
     */
    public void setNeighbors(int numNeighbors) {
        livingNeighbors = numNeighbors;
    }

}
