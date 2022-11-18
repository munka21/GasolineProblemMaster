package InstanceGenerator;

public class MatrixOperations {
    public double[] generateFractionalValues(double[][] z_ij, int[] x_i , int n){
        double[] z_j = new double[n];
        for (int j = 0; j < n; j++){
            for (int i = 0; i <n; i++){
                z_j[i] = z_j[i] + (z_ij[i][j] * x_i[i]);
            }
        }
        return z_j;
    }

    public double[] generateFractionalValuesUntilJ(double[][] z_ij, int[] x_i , int n, int n_j){
        double[] z_j = new double[n];
        for (int j = 0; j < n_j; j++){
            for (int i = 0; i <n; i++){
                z_j[i] = z_j[i] + (z_ij[i][j] * x_i[i]);
            }
        }
        return z_j;
    }

    private boolean isFinished (double[][] z_ij, int index, int j_end){
        double sum = 0.0;
        for (int j = 0; j < j_end; j++){
            sum = sum + z_ij[index][j];
        }
        if (sum >= 1.0){
            return true;
        }
        else {
            return false;
        }
    }
    private boolean consecutivenessPropertyForJFromI_StartToI_End(double[][] z_ij, int i_start, int i_end, int j_end){
        double sum = 0.0;
        for (int i = i_start + 1; i < i_end; i++){
            for (int j = 0; j < j_end; j++){
                sum = sum + z_ij[i][j];
            }
        }
        if (sum >= 1.0){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean consecutivenessPropertyForJInMatrix(double[][] z_ij, int n, int j_end){
        double sum = 0.0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < j_end; j++){
                sum = sum + z_ij[i][j];
            }
        }
        if (sum >= 1.0){
            return true;
        }
        else {
            return false;
        }
    }


    private double[] shift(int n, double[][] z_ij, int j, int[] x_i, int i_1, int i_3, double delta, int i_2) throws Exception {
        double[] a_i = new double[n];
        if (interwallValidation(i_1, i_2, i_3)){
            for (int i = 0; i <n; i++){
                //alle, die nicht in Interwall i_1 und i_3 sind
                if ((i < i_1) || (i > i_3)){
                    a_i[i] = z_ij[i][j];
                }
                //for i_2
                a_i[i_2] = z_ij[i_2][j] + delta;
                //alle in Interwall i_1 und i_3
                if (x_i[i_1] == x_i[i_3]){
                    a_i[i_1] = z_ij[i_1][j] - delta;
                    a_i[i_3] = z_ij[i_3][j];
                } else if (x_i[i_1] != x_i[i_3]) {
                    a_i[i_1] = z_ij[i_1][j] - (delta*((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3])));
                    a_i[i_3] = z_ij[i_3][j] -(delta*((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3])));
                }
            }
        } else if (interwallValidation(i_1, i_2, i_3) == false) {
            throw new Exception("The Interwall is false");
        }
        return a_i;
    }

    private boolean interwallValidation(int i_1, int i_2, int i_3){
        if ((i_2 > i_1) && (i_3 > i_2) && (i_3 > (i_1 + 1))){
            return true;
        }
        else {
            return false;
        }
    }

    public double[][] transform(int n){
        double[][] z_prime = new double[n][n];
        return z_prime;
    }


}