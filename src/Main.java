import InstanceGenerator.*;
import ModelG.*;
import gurobi.GRBException;

public class Main {
    public static void main(String[] args) throws GRBException {
        JobsGeneration jobsGenerator = new JobsGeneration();
        int numberOfJobs = 3;
        int maxSumOfJobs = 12;
        int maxSizeOfOneJob = 10;

        int Y_jobs[];
        Y_jobs = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,false);
        jobsGenerator.printJobs(Y_jobs);
        int X_jobs[];
        X_jobs = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs,true);
        jobsGenerator.printJobs(X_jobs);

        ModelGurobi model = new ModelGurobi();
        model.solveLP(numberOfJobs, X_jobs, Y_jobs);

    }
}