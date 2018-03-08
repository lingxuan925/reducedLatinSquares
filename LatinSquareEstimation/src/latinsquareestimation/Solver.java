package latinsquareestimation;
/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 4p03 Assignment #2 backtracking latin squares
* Solver Class - dfs to find all possible solutions to a Latin square of order n
* Note that in my program, my i's generally refer to row and j's refer to column
*/
public class Solver {
    //contains count of partial solutions at each level
    private final long[] partialSolutions;
    /*stores the chosen configuration of the latin square at that level which 
    is the one where to go down a level from*/
    private int[][] chosenNode;
    
    public Solver(int n) {
        partialSolutions = new long[n];
        chosenNode = new int[n][n]; 
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
     * after getting the random number which is between 1 and the total count
     of partial solutions at that level, do the dfs again to find the node in
     which to go down a level from
     * @param latinsqu - the Latin square trying to solve
     * @param atCol - current column index of the recursion
     * @param order - order of Latin square
     * @param val - value to be inserted at this level (indication of level)
     * @param chosen - the randomized number which is between 1 and the total count
     of partial solutions at that level
     */
    public void dfsGetChosenNode(int[][] latinsqu, int atCol, int order, int val, long chosen) { //never going to add to first column because of unique property
        if (!isSameCol(latinsqu, atCol, order, val)) {
            for (int i=0; i<order; i++) { //i is row
                if (!isSameRow(latinsqu, i, order, val) && isNotOccupied(latinsqu, i, atCol)) { //need to also check for non-empty subsquare
                    latinsqu[i][atCol] = val;
                    if (atCol == order-1) { //at last column (inserted a value at the last column)
                        //a partial solution has been found at this point
                        partialSolutions[val-1] += 1;
                        //found chosen node configuration
                        if (partialSolutions[val-1] == chosen) chosenNode = Helpers.cloneLatinSqu(latinsqu);
                        clearSubSquare(latinsqu, i, atCol); //need to clear subsquare last inserted before solution
                    }
                    else {
                        dfsGetChosenNode(latinsqu, atCol+1, order, val, chosen);
                        clearSubSquare(latinsqu, i, atCol); //after backtrack, clear subsquare
                    }
                }
            }
        }
        else { 
            if (atCol == order-1) { //at last column (no insertion of value, value already exist in last column)
                //a partial solution has been found at this point
                partialSolutions[val-1] += 1;
                if (partialSolutions[val-1] == chosen) chosenNode = Helpers.cloneLatinSqu(latinsqu);
            }
            else { //
                dfsGetChosenNode(latinsqu, atCol+1, order, val, chosen); //move to next col because current col already has the value
            }
        }
    }
    
    /**
     * the depth first search used to count the number of partial solutions at
     the specified level
     * @param latinsqu - the Latin square trying to solve
     * @param atCol - current column index of the recursion
     * @param order - order of Latin square
     * @param val - value to be inserted at this level (indication of level)
     */
    public void dfsCnt(int[][] latinsqu, int atCol, int order, int val) { //never going to add to first column because of unique property
        if (!isSameCol(latinsqu, atCol, order, val)) {
            for (int i=0; i<order; i++) { //i is row
                if (!isSameRow(latinsqu, i, order, val) && isNotOccupied(latinsqu, i, atCol)) { //need to also check for non-empty subsquare
                    latinsqu[i][atCol] = val;
                    if (atCol == order-1) { //at last column (inserted a value at the last column)
                        //a partial solution has been found at this point
                        partialSolutions[val-1] += 1;
                        clearSubSquare(latinsqu, i, atCol); //need to clear subsquare last inserted before solution 
                    }
                    else {
                        dfsCnt(latinsqu, atCol+1, order, val);
                        clearSubSquare(latinsqu, i, atCol); //after backtrack, clear subsquare
                    }
                }
            }
        }
        else { 
            if (atCol == order-1) { //at last column (no insertion of value, value already exist in last column)
                //a partial solution has been found at this point
                partialSolutions[val-1] += 1;
            } 
            else { //
                dfsCnt(latinsqu, atCol+1, order, val); //move to next col because current col already has the value
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

    //returns the array containing the count of partial solutions at each level
    public long[] getPartialSolutions() {
        return partialSolutions;
    }
    
    //returns the configuration of the latin square based on the chosen node at that level
    public int[][] getChosenNode() {
        return chosenNode;
    }
}
