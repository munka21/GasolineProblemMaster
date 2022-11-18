import InstanceGenerator.*;
import ModelG.*;
import gurobi.*;
import Greedy.*;

import java.util.Arrays;

public class Main {

    static int numberOfJobs = 20;
    static int maxSumOfJobs = 1000;
    static int maxSizeOfOneJob = 100;
    public static void main(String[] args) throws GRBException {
        JobsGeneration jobsGenerator = new JobsGeneration();
        MatrixOperations matrix = new MatrixOperations();
        GreedyAlgo greedy = new GreedyAlgo();
        ModelGurobi model = new ModelGurobi();
        Transformation shift = new Transformation();

        int y_i[];
        int x_i[];
        double[][] z_ij;
        double[] z_j;
        double delta = 0.01;
        int i_1 = 0;
        int i_2 = 1;
        int i_3 = 2;

        y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,false);
        x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,true);

        jobsGenerator.printJobs(y_i);//Verstecken
        jobsGenerator.printJobs(x_i);//Verstecken

        z_ij = model.solveLP(numberOfJobs, x_i, y_i);

        //Test*************************
        //TODO: Lösche Test wenn schon alles stimmt

        z_ij = new double[][]{
                {0.2, 0.5, 0.3},
                {0.2, 0.2, 0.6},
                {0.6, 0.3, 0.1}
        };
        //*****************************


        //z_j = matrix.generateFractionalValues(z_ij, x_i, numberOfJobs);
        //int R[][] = greedy.solveProblem(numberOfJobs, x_i, y_i);
        //greedy.printOutput(R, numberOfJobs);

        shift.shift(3, z_ij, 2, i_1, i_2, i_3, delta, x_i );

    }

}