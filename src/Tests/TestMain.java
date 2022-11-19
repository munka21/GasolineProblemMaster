package Tests;
import InstanceGenerator.*;
import ModelG.ModelGurobi;
import gurobi.GRBException;

import java.util.Random;

public class TestMain {

    public void testClassForJobsGeneration(){
        TestJobsGeneration test_jobsGeneration =  new TestJobsGeneration();
        test_jobsGeneration.testGenerator();
    }

    public void testClassTransformation() throws GRBException {
        TestTransformation OutputMatrix = new TestTransformation();
        double[][] z_ij = OutputMatrix.generateMatrix();
    }

}
