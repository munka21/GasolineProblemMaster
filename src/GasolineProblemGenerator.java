import InstanceGenerator.JobsGeneration;
import InstanceGenerator.Rounding;
import InstanceGenerator.RoundingTemp;
import InstanceGenerator.Transformation;
import ModelG.ModelGurobi;
import Test.TestAlgo;
import gurobi.GRBException;


public class GasolineProblemGenerator {

    static int numberOfJobs = 4;
    static int maxSumOfJobs = 150;
    static int maxSizeOfOneJob = 60;
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
        if (existNaiveSolutions(y_i, x_i) == false){
            return false;
        }
        if (checkForZero(y_i, x_i)){
            return false;
        }
        return true;
    }

    private static boolean existNaiveSolutions(int[] y_i, int[] x_i){
        int sum_yi = 0;
        int sum_xi = 0;
        for (int i = 0; i < numberOfJobs; i++){
            sum_yi = sum_yi + y_i[i];
            sum_xi = sum_xi + x_i[i];
            if (sum_yi > sum_xi){
                return false;
            }
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
        //Rounding rounding = new Rounding();
        RoundingTemp roundingTemp = new RoundingTemp();
        int j = 0;
        while (j != numberOfJobs) {
            z_ij = transformation.doTransformation(z_ij, j, x_i);
            j++;
        }
        //z_ij = rounding.doRounding(z_ij);
        z_ij = roundingTemp.doRounding(z_ij);
        return z_ij;
    }

    protected static void startAlgo() throws Exception {
        doGenerateJobs();
        double[][] z_ij = doSolveLP();
        testLPGurobi(z_ij);
        z_ij = doAlgo(z_ij);
        testResult(z_ij);
    }

    protected static void testResult(double[][] z_ij) throws Exception {
        TestAlgo test = new TestAlgo();
        test.doTestEndResult(z_ij,x_i,y_i);

    }

    protected static void testLPGurobi(double[][] z_ij){
        TestAlgo test = new TestAlgo();
        test.testLP(z_ij, x_i, y_i);
    }

}