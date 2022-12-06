package InstanceGenerator;

import java.util.Arrays;

public class Rounding {

    public double[][] doRounding(double[][] z_ij){
        for (int j = 0; j < z_ij.length; j++){
            z_ij = lookAndSetAktivBlock(z_ij, j);
        }
        System.out.println("Result:\n");
        testPrintMatrix(z_ij);
        return z_ij;
    }

    private double[][] lookAndSetAktivBlock(double[][] z_ij, int j){
        for (int i = 0; i < z_ij.length; i++){
            if (z_ij[i][j] > 0.000000000000000){
                if (checkIfEwerywereZero(z_ij, i, j)){
                    z_ij[i][j] = 1.0;
                    setColumnToZero(z_ij, j);
                    return z_ij;
                }
            }
        }
        return z_ij;
    }

    private boolean checkIfEwerywereZero(double[][] z_ij, int i, int j){
        for (int  x = j - 1; x >= 0; x--){
            if (z_ij[i][x] > 0.00000000000000000){
                return false;
            }
        }
        return true;
    }

    private double[][] setColumnToZero(double[][] z_ij, int j){
        for (int i = 0; i < z_ij.length; i++){
            if (z_ij[i][j] < 1.0){
                z_ij[i][j] = 0.0;
            }
        }
        return z_ij;
    }

    protected static void testPrintMatrix(double[][] z_ij){
        int n = z_ij.length;
        for (int i = 0; i < n; i++){
            System.out.println(Arrays.toString(z_ij[i]));
        }
    }

}
