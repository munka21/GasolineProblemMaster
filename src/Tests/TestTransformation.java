package Tests;

import InstanceGenerator.*;
import ModelG.ModelGurobi;
import gurobi.GRBException;

import java.util.Arrays;
import java.util.Random;


public class TestTransformation {
    public void testShift(){
    }

    public double[][] generateMatrix() throws GRBException {
        System.out.println("Matrix will be generate");

        JobsGeneration jobsGenerator = new JobsGeneration();
        ModelGurobi model = new ModelGurobi();
        TestJobsGeneration test_jobsGeneration =  new TestJobsGeneration();
        Random rand = new Random();

        int test_numberOfJobs = rand.nextInt(10) + 1;
        int test_maxSizeOfOneJob = test_numberOfJobs * rand.nextInt(6) + 1;
        int test_maxSumOfJobs = (test_numberOfJobs * test_maxSizeOfOneJob) - ((test_maxSizeOfOneJob * rand.nextInt(3)));
        test_maxSumOfJobs = test_jobsGeneration.checkSumOfJobs(test_numberOfJobs, test_maxSizeOfOneJob,test_maxSumOfJobs);

        int y_i[] = jobsGenerator.nGenerator(test_maxSizeOfOneJob, test_numberOfJobs, test_maxSumOfJobs,false);
        int x_i[] = jobsGenerator.nGenerator(test_maxSizeOfOneJob, test_numberOfJobs, test_maxSumOfJobs,true);
        double[][] z_ij = model.solveLP(test_numberOfJobs, x_i, y_i);
        z_ij = roundMatrix(z_ij, test_numberOfJobs);
        testPrintMatrix(z_ij);
        System.out.println("Matrix is successful generated");
        return z_ij;
    }

    public void testPrintMatrix(double[][] z_ij){
        int n = z_ij.length;
        for (int i = 0; i < n; i++){
            System.out.println(Arrays.toString(z_ij[i]));
        }
    }

    public double[][] roundMatrix(double[][] z_ij, int n){
        double s = Math.pow(10, 3);
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z_ij[i][j] = Math.round(z_ij[i][j] * s) / s;
            }
        }
        return z_ij;
    }

    public static double round(double val, int sca) {
        double s = Math.pow(10, sca);
        return Math.round(val * s) / s;
    }

}