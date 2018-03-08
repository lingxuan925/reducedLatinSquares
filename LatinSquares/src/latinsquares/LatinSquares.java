package latinsquares;

import java.util.Scanner;

/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 4p03 Assignment #2 backtracking latin squares
* main class
*/

public class LatinSquares {
    
    private final Scanner sc;
    
    public LatinSquares() {
        sc = new Scanner(System.in);
        System.out.print("Enter the order of latin squares to compute: ");
        int n = sc.nextInt(); //n is the order of latin squares
        doPartOne(n);
    }
    
    /**
     * does Part 1 of the assignment which just searches for all possible number
     of solutions and provide the count and CPU time
     * @param n - order of Latin squares
     */
    private void doPartOne(int n) {
        Solver solver = new Solver(n);
        
        int[][] latinsqu = new int[n][n];   
        solver.fillValAtCorrespondingFirstRowAndColIndex(latinsqu, n);
        
        long startTime = System.currentTimeMillis(); //start recording cpu time
        
        //start at level 1
        solver.dfs(latinsqu, 0, n, n); //the latin square, col 0, order, value to insert to subsquares

        long endTime = System.currentTimeMillis(); //end recording of cpu time
        long searchTime = endTime - startTime;
        System.out.println();
        System.out.println("Total number of inequivalent latin squares of order "+n+": "+solver.getSolutionsCnt());
        System.out.println("Search Time: "+searchTime+" milliseconds");
    }
    
    public static void main(String[] args) {
        new LatinSquares();
    }   
}
