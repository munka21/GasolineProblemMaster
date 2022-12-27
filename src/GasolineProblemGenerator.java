import Greedy.GreedyAlgo;
import InstanceGenerator.JobsGeneration;
import InstanceGenerator.Rounding;
import InstanceGenerator.Transformation;
import ModelG.ModelGurobi;
import Test.TestAlgo;
import Test.log;
import gurobi.GRBException;

import java.io.IOException;
import java.util.Arrays;


public class GasolineProblemGenerator {

    static int numberOfJobs = 8;
    static int maxSumOfJobs = 500;
    static int maxSizeOfOneJob = 100;
    static int[] y_i;
    static int[] x_i;

    static log log = new log();
    static TestAlgo test = new TestAlgo();

    protected static void doGenerateJobs() throws Exception {
        JobsGeneration jobsGenerator = new JobsGeneration();
        do {
            y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, false);
            x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, true);
        }while (checkIfJobsAreCorrect(y_i,x_i) != true);
        jobsGenerator.printJobs(y_i, "Y");
        jobsGenerator.printJobs(x_i, "X");
        log.addJobsToLog("Y:" + Arrays.toString(y_i));
        log.addJobsToLog("X:" + Arrays.toString(x_i));
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

    protected static double[][] doSolveLP() throws GRBException, IOException {
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
        //z_ij = rounding.doRounding(z_ij);
        z_ij = rounding.doRoundingTemp(z_ij);
        return z_ij;
    }

    protected static void startAlgo() throws Exception {
        log.startTestLog(numberOfJobs, maxSumOfJobs, maxSizeOfOneJob);
        doGenerateJobs();
        double[][] z_ij = doSolveLP();
        z_ij = doAlgo(z_ij);
        testPrintAndLog(z_ij, "Result Algo:");
        double[][] z_greedy = doGreedy();
        testPrintAndLog(z_greedy, "Result Greedy:");
    }

    protected static void testPrintAndLog(double[][] z, String str_one) throws Exception {
        testPrintMatrix(z, str_one, false);
        int[] toZero = test.doTestEndResult(z,x_i,y_i);
        log.addResultsToLog(z, str_one);
        log.addDistanceToZero(toZero, "Distance to Zero: ", test.getSum());
    }

    protected static double[][] doGreedy(){
        GreedyAlgo greedy = new GreedyAlgo();
        double[][] z_greedy = new double[numberOfJobs][numberOfJobs];
        greedy.doGreedy(z_greedy, y_i, x_i);
        return z_greedy;
    }

    private static void testPrintMatrix(double[][] z_ij, String str, boolean round){
        int n = z_ij.length;
        System.out.println(str);
        if (round == false){
            for (int i = 0; i < n; i++){
                System.out.println(Arrays.toString(z_ij[i]));
            }
        }
        if (round == true){
            double[][] z_ij_temp = doRound(z_ij);
            for (int i = 0; i < n; i++){
                System.out.println(Arrays.toString(z_ij_temp[i]));
            }
        }
    }

    private static double[][] doRound(double[][] z_ij){
        int n = z_ij.length;
        double[][] z_ij_round =  new double[n][n];
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                z_ij_round[i][j] = round(z_ij[i][j], 3);
            }
        }
        return z_ij_round;
    }

    private static double round(double value, int decimalPoints) {
        double d = Math.pow(10, decimalPoints);
        return Math.round(value * d) / d;
    }

}