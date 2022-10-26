import InstanceGenerator.*;
import ModelG.*;
import gurobi.GRBException;

public class Main {
    public static void main(String[] args) throws GRBException {
        JobsGeneration jobsGenerator = new JobsGeneration();
        int numberOfJobs = 4;
        int maxSumOfJobs = 18;
        int maxSizeOfOneJob = 12;
        double[][] z_ij = new double[numberOfJobs][numberOfJobs];

        int y_i[];
        y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,false);
        jobsGenerator.printJobs(y_i);//Verstecken
        int x_i[];
        x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,true);
        jobsGenerator.printJobs(x_i);//Verstecken

        ModelGurobi model = new ModelGurobi();
        z_ij = model.solveLP(numberOfJobs, x_i, y_i);

    }
}