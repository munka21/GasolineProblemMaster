package InstanceGenerator;

public class Shift {

    int i_1;
    int i_2;
    int i_3;
    double delta;
    boolean isShiftPossible;

    public double[][] doShift(double[][] z_ij, int j, int[] int_xi){
        double[] x_i = intArrayToDouble(int_xi);
        do{
            lookForIndexAndSet(z_ij, j);
            setDelta(z_ij, j, x_i);
            z_ij = useFormel(z_ij, x_i);
        }while (isShiftPossible == true);
        return z_ij;
    }

    private double[][] useFormel (double[][] z_ij, double[] x_i){
        return z_ij;
    }

    private void setDelta(double[][] z_ij, int j, double[] x_i){
        double y_i1 = ((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3]));
        double y_i3 = ((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3]));
        double sum_i2 = checkSumForIndex_i2(z_ij, j);
        double maxDelta_i1 = z_ij[i_1][j] / y_i1;
        double maxDelta_i3 = z_ij[i_3][j] / y_i3;
        double maxDelta_i2 = 1.0 - sum_i2;
        delta = Math.min(maxDelta_i1, maxDelta_i3);
        delta = Math.min(delta, maxDelta_i2);
    }

    private void lookForIndexAndSet(double[][] z_ij, int j){
        int n = z_ij.length;
        lookForIndex_i1(z_ij, j, n);
        if (isShiftPossible == true){
            lookForIndex_i3(z_ij, j, n);
        }
        if (isShiftPossible == true){
            lookForIndex_i2(z_ij, j);
        }
    }

    private void lookForIndex_i2(double[][] z_ij, int j){
        isShiftPossible = false;
        for (int i = (i_1 + 1); i < i_3; i++){
            i_2 = i;
            if (checkSumForIndex_i2(z_ij, j) < 1.0){
                isShiftPossible = true;
                return;
            }
        }
    }

    private double checkSumForIndex_i2(double[][] z_ij, int j){
        double sum = 0.0;
        isShiftPossible = false;
        for (int i = 0; i <= j; i++){
            sum = sum + z_ij[i_2][i];
        }
        return sum;
    }

    private void lookForIndex_i3(double[][] z_ij, int j, int n){
        isShiftPossible = false;
        for (int i = n-1; i > i_1; i--){
            if ((z_ij[i][j] > 0.0) && (z_ij[i][j] < 1.0) && (i != (i_1 + 1))){
                i_3 = i;
                isShiftPossible = true;
                return;
            } else if (i == (i_1 + 1)) {
                isShiftPossible = false;
                return;
            }
        }
    }

    private void lookForIndex_i1(double[][] z_ij, int j, int n){
        isShiftPossible = false;
        for (int i = 0; i < n; i++){
            if ((z_ij[i][j] > 0.0) && (z_ij[i][j] < 1.0)){
                i_1 = i;
                isShiftPossible = true;
                return;
            } else if ((z_ij[i][j] == 1.0) || (i == (n - 1))) {
                isShiftPossible = false;
                return;
            }
        }
    }

    private double[] intArrayToDouble(int[] x_i){
        int l = x_i.length;
        double[] double_x_i = new double[l];
        for (int i = 0; i < l; i++){
            double_x_i[i] = x_i[i];
        }
        return double_x_i;
    }

}