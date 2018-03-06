package latinsquares;

import java.util.ArrayList;
import java.util.Scanner;

/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 4p03 Assignment #2 backtracking latin squares
* main class - note the order in this program means the order of the latin 
square so a latin square of order n is an n × n grid filled with n symbols 
so that each symbol appears once in each row and column

* Note also this special property of latin squares:
* A reduced Latin square is one in which the first row is 1...n (in order) and 
the first column is likewise 1...n 
* the reduced latin squares of order n are inequivalent
* all other latin squares of order n is equivalent to one of the reduced latin
squares because they can be reduced to that reduced latin square by a 
permutation of the rows and/or a permutation of the columns
*/

/**
 * how the main logic works:
 * at each subsequent level of the search, the search will generate all possible
 solutions from the corresponding node in the previous level and add it to the 
 list of nodes (solutions for this current level)
 * before starting the search for the next level, it will move the list of nodes
 (solutions found) for the current level into a temporary list and then clear
 the original list of nodes for storing the nodes at the next level
 * first level starts with inserting value equal to n in the Latin squares
 * then at each subsequent level, it will insert n-1, n-2, etc in the Latin
 squares
 */
public class LatinSquares {
    
    private final Scanner sc;
    
    public LatinSquares() {
        sc = new Scanner(System.in);
        System.out.print("Enter the order of latin squares to compute: ");
        int n = sc.nextInt(); //n is the order of latin squares
        Solver solver = new Solver(n);
        
        int[][] latinsqu = new int[n][n];   
        solver.fillValAtCorrespondingFirstRowAndColIndex(latinsqu, n);
        
        long startTime = System.currentTimeMillis(); //start recording cpu time
        
        //start at level 1
        solver.dfs(latinsqu, 0, n, n); //the latin square, col 0, order, value to insert to subsquares

        long endTime = System.currentTimeMillis(); //end recording of cpu time
        long searchTime = endTime - startTime;
        System.out.println();
        String formatStr = "%-41s %-30s";
        System.out.println("Total number of inequivalent latin squares of order "+n+": "+solver.getSolutionsCnt());
        for (int i=n-1; i>=0; i--) {
            if (i != 0) solver.getPartialSolutionsCnt()[i-1] = solver.getPartialSolutionsCnt()[i-1].multiply(solver.getPartialSolutionsCnt()[i]);                     
            System.out.println(String.format(formatStr, "Number of partial solutions at level "+Math.abs(i+1)+": ", solver.getPartialSolutionsCnt()[i]));
        }
        System.out.println("Search Time: "+searchTime+" milliseconds");
    }
    
    /**
     * Clones a list of nodes (solutions to the previous level nodes)
     * @param toClone - the list to clone
     * @return a copy
     */
    private ArrayList<int[][]> cloneList(ArrayList<int[][]> toClone) {
        ArrayList<int[][]> copy = new ArrayList();
        for (int[][] node: toClone) copy.add(node);
        return copy;
    }
    
    public static void main(String[] args) {
        new LatinSquares();
    }   
}
