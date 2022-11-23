package InstanceGenerator;

public class Shift {

    int i_1;
    int i_2;
    int i_3;
    double delta;
    boolean isShiftPossible;

    public double[][] doShift(double[][] z_ij, int j, int[] x_i){
        double[] double_x_i = intArrayToDouble(x_i);
        do{
            lookForIndexAndSet(z_ij, j);
            setDelta(z_ij);
            z_ij = useFormel(z_ij, double_x_i);
        }while (isShiftPossible == true);
        return z_ij;
    }

    private double[][] useFormel (double[][] z_ij, double[] x_i){
        return z_ij;
    }

    private void setDelta(double[][] z_ij){
        /*
        Hier wird minimale Delta gewählt
         */
    }

    private void lookForIndexAndSet(double[][] z_ij, int j){
        /*
        Hier wird ein Valide Interwall gesucht
         */
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
