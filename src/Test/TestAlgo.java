package Test;

import java.util.Arrays;

public class TestAlgo {
    public void doTestEndResult(double[][] z_ij, int[] x_i, int[] y_i) throws Exception {
        boolean isOkay = true;
        int n = z_ij.length;
        int tank = 0;
        int index = 0;
        int [] tankLog = new int[n];
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                if (z_ij[i][j] > 0.0){
                    tank = tank + x_i[i] - y_i[j];
                    tankLog[index] = tank;
                    index++;
                    if (tank < 0){
                        isOkay = false;
                    }
                }
            }
        }
        if (isOkay == false){
            System.err.println("The result is incorrect");
        }
        System.out.println("\nTank Log End:");
        System.out.println(Arrays.toString(tankLog));
    }

    public void testLP(double[][] z_ij, int[] x_i, int[] y_i){
        boolean isOkay = true;
        int n = z_ij.length;
        double tank = 0;
        int index = 0;
        double [] tankLog = new double[n];
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                double xi = x_i[i];
                tank = tank + xi * z_ij[i][j];
            }
            double yi = y_i[j];
            tank = tank - yi;
            tankLog[index] = tank;
            index++;
            if (tank < 0.00000){
                isOkay = false;
            }
        }
        if (isOkay == false){
            System.err.println("The Tank of LP is smaller as Road");
        }
        System.out.println("\nTank Log LP:");
        System.out.println(Arrays.toString(tankLog));
    }

}
