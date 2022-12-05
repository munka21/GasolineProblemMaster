import Greedy.GreedyAlgo;
import InstanceGenerator.JobsGeneration;
import InstanceGenerator.*;
import ModelG.ModelGurobi;
import gurobi.*;


public class Main {

    static int numberOfJobs = 5;
    static int maxSumOfJobs = 150;
    static int maxSizeOfOneJob = 40;
    public static void main(String[] args) throws GRBException {

        JobsGeneration jobsGenerator = new JobsGeneration();
        GreedyAlgo greedy = new GreedyAlgo();
        ModelGurobi model = new ModelGurobi();
        Transformation transformation = new Transformation();
        Rounding rounding = new Rounding();

        int y_i[];
        int x_i[];
        double[][] z_ij;
        int j = 0;

        y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, false);
        x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, true);
        jobsGenerator.printJobs(y_i);//TODO: Löschen, nur für Tests
        jobsGenerator.printJobs(x_i);//TODO: Löschen, nur für Tests
        z_ij = model.solveLP(numberOfJobs, x_i, y_i);
        while (j != numberOfJobs) {
            z_ij = transformation.doTransformation(z_ij, j, x_i);
            j++;
        }
        z_ij = rounding.doRounding(z_ij);

    }

}