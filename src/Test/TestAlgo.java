package Test;

import java.util.Arrays;

public class TestAlgo {
    private int sum;
    public int[] doTestEndResult(double[][] z_ij, int[] x_i, int[] y_i) throws Exception {
        int n = z_ij.length;
        int toZero = 0;
        int index = 0;
        int [] toZeroLog = new int[n];
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                if (z_ij[i][j] > 0.0){
                    if (index == n){
                        break;
                    }
                    toZero = toZero + x_i[i] - y_i[j];
                    toZeroLog[index] = toZero;
                    index++;
                }
            }
        }
        absoluteValueOfTankLog(toZeroLog);
        System.out.println("Tank Log End:");
        System.out.println(Arrays.toString(toZeroLog) + " Sum: " + sum);
        return toZeroLog;
    }

    private void absoluteValueOfTankLog(int[] toZeroLog){
        sum = 0;
        for (int i: toZeroLog){
            sum = sum + Math.abs(i);
        }
    }

    public int getSum(){
        return sum;
    }

}
