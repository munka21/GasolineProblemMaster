import Greedy.GreedyAlgo;
import InstanceGenerator.JobsGeneration;
import InstanceGenerator.*;
import ModelG.ModelGurobi;
import gurobi.*;


public class Main {

    static int numberOfJobs = 5;
    static int maxSumOfJobs = 35;
    static int maxSizeOfOneJob = 10;
    public static void main(String[] args) throws GRBException {

        JobsGeneration jobsGenerator = new JobsGeneration();
        GreedyAlgo greedy = new GreedyAlgo();
        ModelGurobi model = new ModelGurobi();
        Shift shift = new Shift();
        Transform transform = new Transform();

        int y_i[];
        int x_i[];
        double[][] z_ij;
        double[] z_j;
        int j = 1;

        while (true) {
            y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, false);
            x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, true);
            jobsGenerator.printJobs(y_i);//Verstecken
            jobsGenerator.printJobs(x_i);//Verstecken
            z_ij = model.solveLP(numberOfJobs, x_i, y_i);
            z_ij = shift.doShift(z_ij, j, x_i);
            z_ij = transform.doTransform(z_ij, j, x_i);


            //int R[][] = greedy.solveProblem(numberOfJobs, x_i, y_i);
            //greedy.printOutput(R, numberOfJobs);

            //z_j = shift.generateFractionalValues(z_ij, x_i, numberOfJobs);

        }

    }
}