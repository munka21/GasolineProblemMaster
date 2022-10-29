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
    private double[] shift(int n, double[][] z_ij, int j, int[] x_i, int i_start, int i_end, double delta, int i_2){
        double[] a_i = new double[n];
        for (int i = 0; i <n; i++){
            //alle, die nicht in interwall i_start und i_end sind
            if ((i < i_start) || (i > i_end)){
                a_i[i] = z_ij[i][j];
            }
            //alle in Interwall i_star und i_end
            if ((i > i_start) && (i < i_end)){
                a_i[i] = z_ij[i][j] + delta;
            }
            //i_start und i_end
            if (x_i[i_start] == x_i[i_end]){
                a_i[i_start] = z_ij[i_start][j] - delta;
                a_i[i_end] = z_ij[i_end][j];

            } else if (x_i[i_start] != x_i[i_end]) {
                //TODO: i_2 ist hier als eine Zahl eingegeben, aber in Paper steht dass es ein Interwall zwischen i_start und i_end ist?
                a_i[i_start] = z_ij[i_start][j] - (delta*((x_i[i_2]-x_i[i_end])/(x_i[i_start]-x_i[i_end])));
                a_i[i_end] = z_ij[i_end][j] -(delta*((x_i[i_start]-x_i[i_2])/(x_i[i_start]-x_i[i_end])));
            }
        }
        return a_i;
    }

    public double[][] transform(int n){
        double[][] z_prime = new double[n][n];
        return z_prime;
    }


}
