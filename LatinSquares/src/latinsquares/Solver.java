package latinsquares;

import java.math.BigInteger;

/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 4p03 Assignment #2 backtracking latin squares
* Solver Class - dfs to find all possible solutions of a Latin Square of order n
* Note that in my program, my i's generally refer to row and j's refer to column
*/

public class Solver {
    private BigInteger solutions; //for n greater than 8 in case
    private final BigInteger four = new BigInteger("4");
    
    public Solver(int n) {
        solutions = new BigInteger("0");
    }
    
    /**
     * check that the sub square to insert the value is not already occupied by a value
     * @param latinsqu - the Latin square currently trying to solve for the current level
     * @param cur_row - row index 
     * @param cur_col - column index
     * @return true if not occupied, false if is occupied 
     */
    private boolean isNotOccupied(int[][] latinsqu, int cur_row, int cur_col) {
        return latinsqu[cur_row][cur_col] == 0;
    }
    
    /**
     * checks if value is already in that column, duplicate
     * i is each row
     * @param latinsqu - the current Latin square configuration to check
     * @param cur_col - column index (constant)
     * @param order - order of Latin square
     * @param val - value to check for duplicate
     * @return true if value already exist in this column, false otherwise
     */
    private boolean isSameCol(int[][] latinsqu, int cur_col, int order, int val) {
        for (int i=0; i<order; i++) if (latinsqu[i][cur_col] == val) return true;
        return false;
    }
    
    /**
     * checks if value is already in that row
     * j is each col
     * @param latinsqu - the current Latin square configuration to check
     * @param cur_row - row index (constant)
     * @param order - order of Latin square
     * @param val - value to check for duplicate
     * @return true if value already exist in this row, false otherwise
     */
    private boolean isSameRow(int[][] latinsqu, int cur_row, int order, int val) {
        for (int j=0; j<order; j++) if (latinsqu[cur_row][j] == val) return true;
        return false;
    }
  
    /**
     * the depth first search used to find all possible solution to current
     Latin square configuration
     * @param latinsqu - the Latin square trying to solve
     * @param atCol - current column index of the recursion
     * @param order - order of Latin square
     * @param val - value to be inserted at this level
     */
    public void dfs(int[][] latinsqu, int atCol, int order, int val) { //never going to add to first column because of unique property
        if (!isSameCol(latinsqu, atCol, order, val)) {
            for (int i=0; i<order; i++) { //i is row
                if (!isSameRow(latinsqu, i, order, val) && isNotOccupied(latinsqu, i, atCol)) { //need to also check for non-empty subsquare
                    latinsqu[i][atCol] = val;
                    if (atCol == order-1) { //at last column (inserted a value at the last column)
                        //a partial solution has been found at this point
                        if (val-1 != 0) { //finished with last level, need to backtrack now if equal to 0, otherwise go in the if statement and dfs to next level
                            fillValAtCorrespondingFirstRowAndColIndex(latinsqu, val-1);
                            dfs(latinsqu, 0, order, val-1);
                            clearSubSquare(latinsqu, i, atCol); //after backtrack, clear subsquare   
                        }
                        else {
                            //clears the value from the last sub square that was inserted when a solution was found before backtracking occurs
                            solutions = solutions.add(BigInteger.ONE);
                            if (solutions.compareTo(four) <= 0) printSquare(latinsqu);
                            clearSubSquare(latinsqu, i, atCol); 
                        }
                    }
                    else {
                        dfs(latinsqu, atCol+1, order, val);
                        clearSubSquare(latinsqu, i, atCol); //after backtrack, clear subsquare
                    }
                }
            }
        }
        else { 
            if (atCol == order-1) { //at last column (no insertion of value, value already exist in last column)
                //a partial solution has been found at this point
                if (val-1 != 0) { //finished with last level, need to backtrack now if equal to 0, otherwise go in the if statement and dfs to next level
                    fillValAtCorrespondingFirstRowAndColIndex(latinsqu, val-1);
                    dfs(latinsqu, 0, order, val-1);
                }
            }
            else { //
                dfs(latinsqu, atCol+1, order, val); //move to next col because current col already has the value
            }
        }
    }
    
    /**
     * clears the value of that sub square at the row and column index after the backtrack
     * @param latinsqu - the Latin square currently trying to solve
     * @param atRow - row index
     * @param atCol - column index
     */
    private void clearSubSquare(int[][] latinsqu, int atRow, int atCol) {
        latinsqu[atRow][atCol] = 0;
    }

    /**
     * before starting the search for that level, it will fill in the respective 
     value at the corresponding sub squares 
     * e.g. n = 4, at level 4, you fill in 4 at index 3 of first row and column
     * @param latinsqu - the Latin square to fill
     * @param val - the value to fill the sub square with
     */
    public void fillValAtCorrespondingFirstRowAndColIndex(int[][] latinsqu, int val) {
        latinsqu[0][val-1] = val;
        latinsqu[val-1][0] = val;
    }
    
    /**
     * Prints a Latin square, used for debugging
     * @param latinsqu - a Latin square
     */
    public void printSquare(int[][] latinsqu) {
        System.out.println();
        for (int i=0; i<latinsqu.length; i++) {
            for (int j=0; j<latinsqu[i].length; j++) System.out.print(String.format("%3d", latinsqu[i][j]));
            System.out.println();
        }            
    }
    
    //return total count of solutions for Latin square of order n
    public BigInteger getSolutionsCnt() {
        return solutions;
    }
}
