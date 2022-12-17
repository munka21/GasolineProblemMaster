import Greedy.GreedyAlgo;
import InstanceGenerator.JobsGeneration;
import InstanceGenerator.Rounding;
import InstanceGenerator.Transformation;
import ModelG.ModelGurobi;
import Test.TestAlgo;
import gurobi.GRBException;

import java.util.Arrays;


public class GasolineProblemGenerator {

    static int numberOfJobs = 5;
    static int maxSumOfJobs = 500;
    static int maxSizeOfOneJob = 250;
    static int[] y_i;
    static int[] x_i;

    protected static void doGenerateJobs() throws Exception {
        JobsGeneration jobsGenerator = new JobsGeneration();
        do {
            y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, false);
            x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, true);
        }while (checkIfJobsAreCorrect(y_i,x_i) != true);
        jobsGenerator.printJobs(y_i, "Y");
        jobsGenerator.printJobs(x_i, "X");
    }

    private static boolean checkIfJobsAreCorrect(int[] y_i, int[] x_i){
        if (checkForZero(y_i, x_i)){
            return false;
        }
        return true;
    }

    private static boolean checkForZero(int[] y_i, int[] x_i){
        for (int i = 0; i < numberOfJobs; i++){
            if ((y_i[i] == 0) || (x_i[i] == 0)){
                return true;
            }
        }
        return false;
    }

    protected static double[][] doSolveLP() throws GRBException {
        ModelGurobi model = new ModelGurobi();
        double[][] z_ij;
        z_ij = model.solveLP(numberOfJobs, x_i, y_i);
        return z_ij;
    }

    protected static double[][] doAlgo(double[][] z_ij){
        Transformation transformation = new Transformation();
        Rounding rounding = new Rounding();
        int j = 0;
        while (j != numberOfJobs) {
            z_ij = transformation.doTransformation(z_ij, j, x_i);
            j++;
        }
        z_ij = rounding.doRounding(z_ij);
        return z_ij;
    }

    protected static void startAlgo() throws Exception {
        TestAlgo test = new TestAlgo();
        doGenerateJobs();
        double[][] z_ij = doSolveLP();
        z_ij = doAlgo(z_ij);
        testPrintMatrix(z_ij, "\nResult Algo:");
        test.doTestEndResult(z_ij,x_i,y_i);
        double[][] z_greedy = doGreedy();
        testPrintMatrix(z_greedy, "\nResult Greedy:");
        test.doTestEndResult(z_greedy,x_i,y_i);
    }

    protected static double[][] doGreedy(){
        GreedyAlgo greedy = new GreedyAlgo();
        double[][] z_greedy = new double[numberOfJobs][numberOfJobs];
        greedy.doGreedy(z_greedy, y_i, x_i);
        return z_greedy;
    }

    private static void testPrintMatrix(double[][] z_ij, String str){
        int n = z_ij.length;
        System.out.println(str);
        for (int i = 0; i < n; i++){
            System.out.println(Arrays.toString(z_ij[i]));
        }
    }

}