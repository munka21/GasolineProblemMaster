package Tests;
import InstanceGenerator.*;
import java.util.Random;

public class TestJobsGeneration {

    public void testGenerator(){
        System.out.println("Test of JobsGeneration:");
        System.out.println("Two arrays x and y will be always generated with random size. ");
        Random rand = new Random();
        for (int i = 0; i < 10; i++){
            System.out.println("Test nr [" + (i+1) + "]:");
            int test_numberOfJobs = rand.nextInt(100  - 10 + 1) + 10;
            int test_maxSizeOfOneJob = test_numberOfJobs * rand.nextInt(10) + 1;
            int test_maxSumOfJobs = (test_numberOfJobs * test_maxSizeOfOneJob) - ((test_maxSizeOfOneJob * rand.nextInt(10)));
            test_maxSumOfJobs = checkSumOfJobs(test_numberOfJobs, test_maxSizeOfOneJob,test_maxSumOfJobs);

            System.out.println("The sizes are:\n test_numberOfJobs: " + test_numberOfJobs + "\n test_maxSizeOfOneJob: " + test_maxSizeOfOneJob + "\n test_maxSumOfJobs: " + test_maxSumOfJobs);
            JobsGeneration test_jobsGenerator = new JobsGeneration();
            int test_y[];
            int test_x[];
            test_y = test_jobsGenerator.nGenerator(test_maxSizeOfOneJob, test_numberOfJobs, test_maxSumOfJobs,false);
            test_x = test_jobsGenerator.nGenerator(test_maxSizeOfOneJob, test_numberOfJobs, test_maxSumOfJobs,true);
            System.out.println("The generation was successful.");
            areJobsSortiert(test_x);
            checkSum(test_x, test_y, test_numberOfJobs);
            System.out.println("End of Test for JobsGeneration");
        }
    }

    protected int checkSumOfJobs (int test_numberOfJobs, int test_maxSizeOfOneJob, int test_maxSumOfJobs){
        int test_size = (test_numberOfJobs * test_maxSizeOfOneJob) - test_maxSumOfJobs;
        if ((test_size <= 150) && (test_maxSumOfJobs - 150) > 0){
            test_maxSumOfJobs = test_maxSumOfJobs - 150;
            return test_maxSumOfJobs;
        }
        else if ((test_size <= 100) && (test_maxSumOfJobs - 100) > 0){
            test_maxSumOfJobs = test_maxSumOfJobs - 100;
            return test_maxSumOfJobs;
        }
        else if ((test_size <= 50) && (test_maxSumOfJobs - 50) > 0){
            test_maxSumOfJobs = test_maxSumOfJobs - 50;
            return test_maxSumOfJobs;
        } else if ((test_size <= 20) && (test_maxSumOfJobs - 20) > 0){
            test_maxSumOfJobs = test_maxSumOfJobs - 20;
            return test_maxSumOfJobs;
        }
        return test_maxSumOfJobs;
    }

    private void areJobsSortiert(int[] array){
        int l = array.length - 1;
        int j = 1;
        for (int i = 0; i < l; i++){
            if (array[i] < array[j]){
                System.out.println("The jobs in x are NOT sorted");
                return;
            }
            j++;
        }
        System.out.println("The jobs in x are sorted");
        return;
    }

    private void checkSum(int[] x, int[]y, int n){
        int sum_x = 0;
        int sum_y = 0;
        for (int i = 0; i < n; i++){
            sum_x = sum_x + x[i];
            sum_y = sum_y + y[i];
        }
        if (sum_y == sum_x){
            System.out.println("The summ of x and y arrays are equal.");
        }
        else {
            System.out.println("The summ of x and y arrays are NOT equal.");
        }

    }



}
