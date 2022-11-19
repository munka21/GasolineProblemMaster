import gurobi.*;
import Tests.*;


public class Main {

    static int numberOfJobs = 10;
    static int maxSumOfJobs = 300;
    static int maxSizeOfOneJob = 50;
    public static void main(String[] args) throws GRBException {
         TestMain test = new TestMain();
         test.testClassForJobsGeneration();
/*
        JobsGeneration jobsGenerator = new JobsGeneration();
        GreedyAlgo greedy = new GreedyAlgo();
        ModelGurobi model = new ModelGurobi();
        Transformation shift = new Transformation();

        int y_i[];
        int x_i[];
        double[][] z_ij;
        double[] z_j;
        double delta = 0.01;
        int i_1 = 2;
        int i_2 = 4;
        int i_3 = 7;
        int j = 4;

        y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,false);
        x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,true);

        jobsGenerator.printJobs(y_i);//Verstecken
        jobsGenerator.printJobs(x_i);//Verstecken

        z_ij = model.solveLP(numberOfJobs, x_i, y_i);

        //Test*************************
        //TODO: Lösche Test wenn schon alles stimmt
/*
        z_ij = new double[][]{
                {0.2, 0.5, 0.3},
                {0.0, 0.0, 0.6},
                {0.6, 0.3, 0.1}
        };
        //*****************************

*/
        /*
        z_j = shift.generateFractionalValues(z_ij, x_i, numberOfJobs);
        int R[][] = greedy.solveProblem(numberOfJobs, x_i, y_i);
        greedy.printOutput(R, numberOfJobs);

        shift.shift(numberOfJobs, z_ij, j, i_1, i_2, i_3, delta, x_i );
*/

    }

}