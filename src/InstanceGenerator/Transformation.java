package InstanceGenerator;
import java.util.Arrays;

import static Tests.MatrixOp.*;

public class Transformation {
    int i_1;
    int i_2;
    int i_3;
    double delta;
    double transformDelta;
    boolean isShiftPossible;
    double[] z_j;
    int n;
    int j_prime;

    public double[][] doTransformation(double[][] z_ij, int j, int[] int_xi){
        System.out.println("Vor Shift");//TODO: Löschen
        testPrintMatrix2D(z_ij);//TODO: Löschen
        double[] x_i = intArrayToDouble(int_xi);
        n = z_ij.length;
        do{
            lookForIndexAndSet(z_ij, j);
            if (isShiftPossible == true){
                setDelta(z_ij, j, x_i);/*
                if (checkDelta() == false){//TODO: Löschen
                    System.out.println("Small delta: " + delta );//TODO: Löschen
                }*/
                //delta = round(delta, 3);//TODO: Löschen
                z_ij = doShift(z_ij, x_i, j);
                //z_ij = Array2DRound(z_ij);//TODO: Löschen


                System.out.println("After Shift");//TODO: Löschen
                testPrintMatrix2D(z_ij);//TODO: Löschen


                calculateFractionalValues(z_ij);
                //z_j = ArrayRound(z_j);//TODO:

                System.out.println("\nz_j:");//TODO: Löschen
                System.out.println(Arrays.toString(z_j)+ "\n");//TODO: Löschen
                int preventionOfRoundingError = 0;

                while (check_zj() != true){
                    preventionOfRoundingError++;
                    set_j_prime(z_ij, j + 1);//TODO: Do only if one of z_j > 1
                    setTransformDelta(z_ij);
                    //transformDelta = round(transformDelta, 3);//TODO: Löschen
                    z_ij = doTransform(z_ij, x_i);
                    //z_ij = Array2DRound(z_ij);//TODO: Löschen
                    calculateFractionalValues(z_ij);
                    //z_j = ArrayRound(z_j);//TODO: Löschen

                    System.out.println("\nz_j After Transform:");//TODO: Löschen
                    System.out.println(Arrays.toString(z_j)+ "\n");//TODO: Löschen
                    System.out.println("After Transform");//TODO: Löschen
                    testPrintMatrix2D(z_ij);//TODO: Löschen

                    if((preventionOfRoundingError > 1000)){
                        System.out.println("Rounding Error");
                        break;
                    }
/*
                    if ((checkDelta() == false) || (checkTransformDelta() == false)){//TODO: Löschen
                        set_j_prime(z_ij, j);//TODO:check if funktioniert//TODO: Löschen
                        System.out.println("Small Delta or TransformDelta\n Delta: " + delta + " TransformDelta: " + transformDelta);//TODO: Löschen
                    }*/
                }
                continue;
            }
            break;
        }while (true);
        System.out.println("\nz_j Letzte:");//TODO: Löschen
        System.out.println(Arrays.toString(z_j)+ "\n");//TODO: Löschen
        return z_ij;
    }
/*//TODO: Löschen
    private boolean checkTransformDelta(){
        if (round(transformDelta,3) < 0.001){
            return false;
        }
        return true;
    }
//TODO: Löschen
    private boolean checkDelta(){
        if ((round(delta, 3)) < 0.001){
        return false;
        }
        return true;
    }
*/
    private void setTransformDelta(double[][] z_ij){
        if (delta >= z_ij[i_2][j_prime]){
            transformDelta = z_ij[i_2][j_prime];
            delta = delta - transformDelta;
        }
        else {
            transformDelta = delta;
        }
    }

    private boolean check_zj(){
        for (int i = 0; i < n; i++){
            if ((z_j[i] > 1.000000001) || (z_j[i] < 0.9999999)){
                return false;
            }
        }
        return true;
    }

    private void set_j_prime(double[][] z_ij, int j){
        for (; j < n; j++){
            if (z_ij[i_2][j] > 0.00000){
                j_prime = j;
                return;
            }
        }
    }

    private void calculateFractionalValues(double[][] z_ij){
        z_j = new double[n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z_j[i] = z_j[i] + z_ij[i][j];
            }
        }
    }

    private double[][] doTransform(double[][] z_ij, double[] x_i){
        double y_i1 = ((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3]));
        double y_i3 = ((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3]));
        z_ij[i_2][j_prime] = z_ij[i_2][j_prime] - transformDelta;
        if (x_i[i_1] == x_i[i_3]){
            z_ij[i_1][j_prime] = z_ij[i_1][j_prime] + transformDelta;
            z_ij[i_3][j_prime] = z_ij[i_3][j_prime];
        } else{
            z_ij[i_1][j_prime] = z_ij[i_1][j_prime] + (transformDelta * y_i1);
            z_ij[i_3][j_prime] = z_ij[i_3][j_prime] + (transformDelta * y_i3);
        }
        return z_ij;
    }

    private double[][] doShift(double[][] z_ij, double[] x_i, int j){
        double y_i1 = ((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3]));
        double y_i3 = ((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3]));
        z_ij[i_2][j] = z_ij[i_2][j] + delta;
        if (x_i[i_1] == x_i[i_3]){
            z_ij[i_1][j] = z_ij[i_1][j] - delta;
            z_ij[i_3][j] = z_ij[i_3][j];
        } else{
            z_ij[i_1][j] = z_ij[i_1][j] - (delta * y_i1);
            z_ij[i_3][j] = z_ij[i_3][j] - (delta * y_i3);
        }
        return z_ij;
    }

    private void setDelta(double[][] z_ij, int j, double[] x_i){
        /*
        Es ist ein Intelligent Wahl von Delta, wir bekommen immer das maximale Delta
        so das entweder eine von Schranken (i_1, i_3) 0 wird oder i_2 maximal erhört
        nur in einen Transformation Lauf. Frag ob es so bleiben kann unb erklärt wie es
        gemacht wurde.
        ai = z_ij - (delta * yi) => weil es gleich 0 sein sollte, dann setzen wir ai = 0
        (0 - z_ij)/yi = -delta
        delta = z_ij/yi
         */
        double y_i1 = ((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3]));
        double y_i3 = ((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3]));
        double sum_i2 = checkSumForIndex_i2(z_ij, j);
        double maxDelta_i1 = z_ij[i_1][j] / y_i1;
        double maxDelta_i3 = z_ij[i_3][j] / y_i3;
        double maxDelta_i2 = 1.0 - sum_i2;
        delta = Math.min(maxDelta_i1, maxDelta_i3);
        delta = Math.min(delta, maxDelta_i2);
        System.out.println("\nDelta:" + delta + "\n");//TODO: Löschen
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

    protected double[] intArrayToDouble(int[] x_i){
        int l = x_i.length;
        double[] double_x_i = new double[l];
        for (int i = 0; i < l; i++){
            double_x_i[i] = x_i[i];
        }
        return double_x_i;
    }

    protected static void testPrintMatrix2D(double[][] z_ij){
        int n = z_ij.length;
        for (int i = 0; i < n; i++){
            System.out.println(Arrays.toString(z_ij[i]));
        }
    }

}
