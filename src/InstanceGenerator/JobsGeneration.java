package InstanceGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class JobsGeneration {
    private boolean isPossible(int bound, int n, int sum){
        if ((bound * n) < sum){
            return false;
        }
        return true;
    }

    public int[] nGenerator(int bound, int n, int sum, boolean sort){
        Random random = new Random();
        int JobList[] = new int[n];
        int currentSum = 0;
        int currentJob = 0;
        if (isPossible(bound,n,sum) == false){
            System.out.print("\nIt is not possible to make a list of jobs with these paramaters.\n");
            return JobList;
        }
        else {
            for (int index = 0; index < n ;index++){
                if (currentSum == sum){
                    JobList[index] = 0;
                }
                if (currentSum < sum){
                    int job = random.nextInt(bound);
                    currentJob = job;
                    currentSum = currentSum + job;
                    JobList[index] = job;
                }
                if ((currentSum < sum) && (index == (n - 1))){
                    while (currentSum != sum){
                        int newIndex = random.nextInt(n-1);
                        if (JobList[newIndex] < bound){
                            JobList[newIndex] = JobList[newIndex] + 1;
                            currentSum = currentSum + 1;
                        }
                        else {
                            continue;
                        }
                    }
                }
                if ((currentSum > sum)  && (index == (n - 1))){
                    while (currentSum != sum){
                        int newIndex = random.nextInt(n-1);
                        if (JobList[newIndex] >= 1){
                            JobList[newIndex] = JobList[newIndex] - 1;
                            currentSum = currentSum - 1;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
        if (sort == true){
            Arrays.sort(JobList);
            JobList = flipArray(JobList, n);
        }
        return JobList;
    }

    private int[] flipArray(int [] JobList, int n){
        int[] tempList = new int[n];
        int j = n - 1;
        for (int i = 0; i < n; i++){
            tempList[i] = JobList[j];
            j--;
        }
        JobList = tempList;
        return JobList;
    }

    public void printJobs (int JobsList[]){
        int length = JobsList.length;
        int checkSum = 0;
        for (int index = 0; index < length; index++){
            checkSum = checkSum + JobsList[index];
            if (index == 0){
                System.out.print("[" + JobsList[index]);
            }
            else if (index == (length - 1)){
                System.out.print("," + JobsList[index] + "]");
            }
            else {
                System.out.print("," + JobsList[index]);
            }
        }
        System.out.print(" Sum: " + checkSum + "\n");
    }
}
