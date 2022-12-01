import Greedy.GreedyAlgo;
import InstanceGenerator.JobsGeneration;
import InstanceGenerator.*;
import ModelG.ModelGurobi;
import gurobi.*;

import static Tests.MatrixOp.*;


public class Main {

    static int numberOfJobs = 5;
    static int maxSumOfJobs = 100;
    static int maxSizeOfOneJob = 30;
    public static void main(String[] args) throws GRBException {

        JobsGeneration jobsGenerator = new JobsGeneration();
        GreedyAlgo greedy = new GreedyAlgo();
        ModelGurobi model = new ModelGurobi();
        Shift shift = new Shift();
        Transform transform = new Transform();

        int y_i[];
        int x_i[];
        double[][] z_ij;
        int j = 1;//TODO: für j von 0 bis n für eine Matrix, man muss while ändern und nach unter verschieben

        while (true) {
            y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, false);
            x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, true);
            System.out.println("\n START \n");
            jobsGenerator.printJobs(y_i);//Verstecken
            jobsGenerator.printJobs(x_i);//Verstecken
            z_ij = model.solveLP(numberOfJobs, x_i, y_i);//TODO: nach dem verschiben
            //z_ij = ArrayRound(z_ij);
            z_ij = shift.doShift(z_ij, j, x_i);
            z_ij = transform.doTransform(z_ij, j, x_i);


            //int R[][] = greedy.solveProblem(numberOfJobs, x_i, y_i);
            //greedy.printOutput(R, numberOfJobs);

            //z_j = shift.generateFractionalValues(z_ij, x_i, numberOfJobs);

        }

    }

}