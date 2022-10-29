import InstanceGenerator.*;
import ModelG.*;
import gurobi.GRBException;

import java.util.Arrays;

public class Main {

    static int numberOfJobs = 3;
    static int maxSumOfJobs = 20;
    static int maxSizeOfOneJob = 15;
    public static void main(String[] args) throws GRBException {
        JobsGeneration jobsGenerator = new JobsGeneration();
        MatrixOperations matrix = new MatrixOperations();
        int y_i[];
        int x_i[];
        double[][] z_ij;
        double[] z_j;
        ModelGurobi model = new ModelGurobi();

        y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,false);
        x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,true);

        jobsGenerator.printJobs(y_i);//Verstecken
        jobsGenerator.printJobs(x_i);//Verstecken

        //z_ij = model.solveLP(numberOfJobs, x_i, y_i);

        //Test*************************
        //TODO: Lösche Test wenn schon alles stimmt
        z_ij = new double[][]{
                {0.4, 0.6, 0.0},
                {0.0, 0.0, 1.0},
                {0.6, 0.4, 0.0}
        };
        //*****************************

        z_j = matrix.generateFractionalValues(z_ij, x_i, numberOfJobs);

        System.out.println(Arrays.toString(z_j));
    }

}