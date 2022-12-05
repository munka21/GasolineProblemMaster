package InstanceGenerator;

public class Rounding {

    public double[][] doRounding(double[][] z_ij){
        for (int j = 0; j < z_ij.length; j++){
            lookAndSetAktivBlock(z_ij, j);
        }
        return z_ij;
    }

    private double[][] lookAndSetAktivBlock(double[][] z_ij, int j){
        boolean isOneSet = false;
        for (int i = 0; i < z_ij.length; i++){
            if (z_ij[i][j] > 0.000000000000000){
                if ((checkIfEwerywereZero(z_ij, i, j)) && (isOneSet == false)){
                    z_ij[i][j] = 1.0;
                    isOneSet = true;
                }
                else if ((checkIfEwerywereZero(z_ij, i, j)) && (isOneSet == true)){
                    z_ij[i][j] = 0.0;
                }
            }
        }
        return z_ij;
    }

    private boolean checkIfEwerywereZero(double[][] z_ij, int i, int j){
        for (int  x = j; x >= 0; x--){
            if (z_ij[i][x] > 0.00000000000000000){
                return false;
            }
        }
        return true;
    }

}
