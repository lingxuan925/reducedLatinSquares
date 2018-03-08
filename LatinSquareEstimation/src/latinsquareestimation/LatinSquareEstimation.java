package latinsquareestimation;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 4p03 Assignment #2 backtracking latin squares
* main class - mostly same as part 1's code with some slight modifications in
the dfs and also adding the code to do the indicated number of runs from user's
input
*/
public class LatinSquareEstimation {
    
    private final Scanner sc;
    private final long[] averages;
    private final BigInteger[] estimation;
    
    public LatinSquareEstimation() {
        sc = new Scanner(System.in);
        System.out.print("Enter the order of latin squares to compute: ");
        int n = sc.nextInt(); //n is the order of latin squares
        System.out.print("Enter number of runs for averages: ");
        int runs = sc.nextInt(); //number of runs to do 
        averages = new long[n]; //stores the sum of total number of partial solutions at each level for that chosen node for all runs
        estimation = new BigInteger[n];
        
        System.out.println("Computing for Latin Squares of order "+n);
        for (int i=0; i<runs; i++) {
            System.out.println("Run "+(i+1)+"...");
            doPartTwo(n);
            System.out.println();
        }
        System.out.println("List of partial solutions count at each level (average of "+runs+" runs): ");
        for (int i=n-1; i>=0; i--) averages[i] /= runs;
        for (int i=n-1; i>=0; i--) System.out.println(String.format("%9s %20s", "Level "+(i+1), averages[i]));
        //now mutiply to get estimation of partial solutions, need to use BigInteger
        for (int i=n-1; i>=0; i--) estimation[i] = new BigInteger(String.valueOf(averages[i]));
        for (int i=n-1; i>=0; i--) if (i != n-1) estimation[i] = estimation[i].multiply(estimation[i+1]);
        System.out.println();
        for (int i=n-1; i>=0; i--) System.out.println(String.format("%9s %80s", "Level "+(i+1), estimation[i]));
    }
    
    /**
     * Does part two of the assignment:
     * main logic is there are two dfs methods: it calls the first dfs method to 
     count the number of partial solutions at the specified level
     * then it randomizes a value based on this number of partial solutions at 
     this level to chose the node from which to go down a level from current
     level
     * the partial solutions array's index corresponds to the level
     * does level n first, then loops n-1 times for subsequent levels
     * @param n - order of Latin squares
     */
    private void doPartTwo(int n) {
        Solver solver = new Solver(n);
        
        int[][] latinsqu = new int[n][n];   
        solver.fillValAtCorrespondingFirstRowAndColIndex(latinsqu, n);
        solver.dfsCnt(latinsqu, 0, n, n); //gets a count of the partial solutions at the level
        //based on the count, randomize a value in that range as the chosen node at that level to go down one level
        long chosenNode = ThreadLocalRandom.current().nextLong(1, solver.getPartialSolutions()[n-1]+1); 
        solver.getPartialSolutions()[n-1] = 0; //resets partial solutions at that level to 0
        System.out.print("Chose node "+chosenNode+" at level "+n);
        solver.dfsGetChosenNode(latinsqu, 0, n, n, chosenNode); //dfs again to get the configuration of the chosen node
        Helpers.printSquare(solver.getChosenNode());
        System.out.println("Number of partial solutions at level "+n+": "+solver.getPartialSolutions()[n-1]);
        
        for (int i=1; i<n; i++) { //does same thing for levels n-1 to 1
            solver.fillValAtCorrespondingFirstRowAndColIndex(solver.getChosenNode(), n-i);
            solver.dfsCnt(solver.getChosenNode(), 0, n, n-i);
            long chosen = ThreadLocalRandom.current().nextLong(1, solver.getPartialSolutions()[n-i-1]+1); 
            System.out.print("Chose node "+chosen+" at level "+(n-i));
            solver.getPartialSolutions()[n-i-1] = 0;
            solver.dfsGetChosenNode(solver.getChosenNode(), 0, n, n-i, chosen);
            Helpers.printSquare(solver.getChosenNode());
            System.out.println("Number of partial solutions at level "+(n-i)+": "+solver.getPartialSolutions()[n-i-1]);
        }
        //sums the partial solutions at each level for each run
        for (int i=0; i<n; i++) averages[i] += solver.getPartialSolutions()[i]; 
    }
    
    public static void main(String[] args) {
        new LatinSquareEstimation();
    }   
}
