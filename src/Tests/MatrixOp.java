package Tests;

public class MatrixOp {

    public static double[][] Array2DRound(double[][] z_ij){//TODO: Löschen
        int n = z_ij.length;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z_ij[i][j] = round(z_ij[i][j], 3);
            }
        }
        return z_ij;
    }

    public static double[] ArrayRound(double[] z_j){//TODO: Löschen
        int n = z_j.length;
        for (int i = 0; i < n; i++){
            z_j[i] = round(z_j[i], 3);
        }
        return z_j;
    }

    public static double round(double value, int decimalPoints) {
        double d = Math.pow(10, decimalPoints);
        return Math.round(value * d) / d;
    }

}
