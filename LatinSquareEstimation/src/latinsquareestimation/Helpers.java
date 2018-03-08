package latinsquareestimation;

/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 4p03 Assignment #2 backtracking latin squares
* Helpers class - some methods to manipulate the Latin squares
*/

public class Helpers {
    /**
     * Clones the current configuration of the Latin square being solved (the 
     solution) because any further changes made to this Latin
     square in the search will be reflected since it references the same Latin 
     square in memory
     * @param toClone - the current configuration to clone
     * @return a copy
     */
    public static int[][] cloneLatinSqu(int[][] toClone) {
        int[][] copy = new int[toClone.length][toClone.length];      
        for (int i=0; i<copy.length; i++) for (int j=0; j<copy[i].length; j++) copy[i][j] = toClone[i][j];
        return copy;
    }
    
    /**
     * Prints a Latin square, used for debugging
     * @param latinsqu - a Latin square
     */
    public static void printSquare(int[][] latinsqu) {
        System.out.println();
        for (int i=0; i<latinsqu.length; i++) {
            for (int j=0; j<latinsqu[i].length; j++) System.out.print(String.format("%3d", latinsqu[i][j]));
            System.out.println();
        }            
    }
}
