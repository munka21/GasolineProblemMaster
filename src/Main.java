import Greedy.GreedyAlgo;
import InstanceGenerator.JobsGeneration;
import InstanceGenerator.Transformation;
import ModelG.ModelGurobi;
import gurobi.*;
import Tests.*;

import java.util.Arrays;


public class Main {

    static int numberOfJobs = 5;
    static int maxSumOfJobs = 35;
    static int maxSizeOfOneJob = 10;
    public static void main(String[] args) throws GRBException {
         TestMain test = new TestMain();
         //test.testClassForJobsGeneration();
        //test.testClassTransformation();

        JobsGeneration jobsGenerator = new JobsGeneration();
        GreedyAlgo greedy = new GreedyAlgo();
        ModelGurobi model = new ModelGurobi();
        Transformation shift = new Transformation();

        int y_i[];
        int x_i[];
        double[][] z_ij;
        double[] z_j;

        double delta = 0.1;
        int i_1 = 0;
        int i_2 = 2;
        int i_3 = 4;
        int j = 1;

        y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,false);
        x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,true);

        jobsGenerator.printJobs(y_i);//Verstecken
        jobsGenerator.printJobs(x_i);//Verstecken

        z_ij = model.solveLP(numberOfJobs, x_i, y_i);

        //int R[][] = greedy.solveProblem(numberOfJobs, x_i, y_i);
        //greedy.printOutput(R, numberOfJobs);
/*
        z_ij = new double[][]{
                {0.2, 0.1, 0.4, 0.3},
                {0.0, 0.3, 0.2, 0.5},
                {0.3, 0.4, 0.2, 0.1},
                {0.5, 0.2, 0.2, 0.1}
        };
*/
        //z_j = shift.generateFractionalValues(z_ij, x_i, numberOfJobs);
        double[] a_i = shift.shift(numberOfJobs, z_ij, j, i_1, i_2, i_3, delta, x_i );
        System.out.println(Arrays.toString(a_i) + "\n");//TODO: löschen wenn fertig
        testPrintMatrix(z_ij);
        shift.swapColumn(z_ij, a_i, j, numberOfJobs);
        System.out.println("\n New \n");
        testPrintMatrix(z_ij);

    }

    public static void testPrintMatrix(double[][] z_ij){
        int n = z_ij.length;
        for (int i = 0; i < n; i++){
            System.out.println(Arrays.toString(z_ij[i]));
        }
    }

}