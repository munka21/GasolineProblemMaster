package Greedy;


import InstanceGenerator.JobsGeneration;

import java.util.Arrays;

public class GreedyAlgo {
    public static double[][] doGreedy(double[][] z_greedy, int[] y, int[] x){
        //TODO: Implemetiert Greedy for Problem
        JobsGeneration jobsGenerator = new JobsGeneration();
        jobsGenerator.printJobs(y, "y");
        jobsGenerator.printJobs(x, "x");
        return z_greedy;
    }

}
