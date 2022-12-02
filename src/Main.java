import Greedy.GreedyAlgo;
import InstanceGenerator.JobsGeneration;
import InstanceGenerator.*;
import ModelG.ModelGurobi;
import gurobi.*;

import static Tests.MatrixOp.*;


public class Main {

    static int numberOfJobs = 6;
    static int maxSumOfJobs = 150;
    static int maxSizeOfOneJob = 50;
    public static void main(String[] args) throws GRBException {

        JobsGeneration jobsGenerator = new JobsGeneration();
        GreedyAlgo greedy = new GreedyAlgo();
        ModelGurobi model = new ModelGurobi();
        Transformation transformation = new Transformation();

        int y_i[];
        int x_i[];
        double[][] z_ij;
        int j = 0;//TODO: für j von 0 bis n für eine Matrix, man muss while ändern und nach unter verschieben

        y_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, false);
        x_i = jobsGenerator.nGenerator(maxSizeOfOneJob, numberOfJobs, maxSumOfJobs, true);
        jobsGenerator.printJobs(y_i);//Verstecken
        jobsGenerator.printJobs(x_i);//Verstecken
        z_ij = model.solveLP(numberOfJobs, x_i, y_i);//TODO: nach dem verschiben
        while (j != numberOfJobs) {
            z_ij = Array2DRound(z_ij);//TODO: löschen, nur für Lesbarkeit
            z_ij = transformation.doTransformation(z_ij, j, x_i);
            j++;
        }

    }

}