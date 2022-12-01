package Tests;

public class MatrixOp {

    public static double[][] ArrayRound(double[][] z_ij){
        int n = z_ij.length;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z_ij[i][j] = round(z_ij[i][j], 3);
            }
        }
        return z_ij;
    }

    public static double round(double value, int decimalPoints) {
        double d = Math.pow(10, decimalPoints);
        return Math.round(value * d) / d;
    }

}
