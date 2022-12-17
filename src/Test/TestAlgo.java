package Test;

import java.util.Arrays;

public class TestAlgo {
    public int[] doTestEndResult(double[][] z_ij, int[] x_i, int[] y_i) throws Exception {
        int n = z_ij.length;
        int toZero = 0;
        int index = 0;
        int [] toZeroLog = new int[n];
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                if (z_ij[i][j] > 0.0){
                    toZero = toZero + x_i[i] - y_i[j];
                    toZeroLog[index] = toZero;
                    index++;
                }
            }
        }
        System.out.println("Tank Log End:");
        System.out.println(Arrays.toString(toZeroLog));
        return toZeroLog;
    }

}
