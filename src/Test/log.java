package Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class log {
    int Test_number = 1;

    public void startTestLog(int numberOfJobs, int maxSumOfJobs, int maxSizeOfOneJob) throws IOException {
        FileWriter fw = new FileWriter("master_log.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write("Test nr:" + Test_number);
        Test_number++;
        bw.newLine();
        bw.write("Number of jobs: " + numberOfJobs);
        bw.newLine();
        bw.write("Max sum of jobs: " + maxSumOfJobs);
        bw.newLine();
        bw.write("Max size of one job: " + maxSizeOfOneJob);
        bw.newLine();
        bw.close();
    }

    public void addLpToLog(double Obj, double alpha, double beta) throws IOException {
        FileWriter fw = new FileWriter("master_log.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write("Obj:= " + Obj);
        bw.newLine();
        bw.write("alpha:= " + alpha);
        bw.newLine();
        bw.write("beta:= " + beta);
        bw.newLine();
        bw.close();
    }
    public void addJobsToLog(String str) throws IOException {
        FileWriter fw = new FileWriter("master_log.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(str);
        bw.newLine();
        bw.close();
    }

    public void addResultsToLog(double[][] z, String str) throws IOException {
        int n = z.length;
        FileWriter fw = new FileWriter("master_log.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(str);
        bw.newLine();
        for (int i = 0; i < n; i++){
            bw.write(Arrays.toString(z[i]));
            bw.newLine();
        }
        bw.close();

    }

    public void addDistanceToZero(int[] toZero, String str, int sum) throws IOException {
        FileWriter fw = new FileWriter("master_log.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(str);
        bw.write(Arrays.toString(toZero));
        bw.write(" absolute sum: " + sum);
        bw.newLine();
        bw.close();
    }

}
