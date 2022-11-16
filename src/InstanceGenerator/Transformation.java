package InstanceGenerator;

import java.util.Arrays;

//TODO: teste alle Funktionen :(
//TODO: Fehler Behandlung

public class Transformation {
    /*
    i_1, j kann auch 0 sein, unsere Matrix ist von 0 nummeriert,
    also wenn wenn i_1 ist 1, das ist also zweite Zeile in Matrix.
     */
    boolean shiftIsPossible;
    boolean[] Alli_1Index;
    int i_1Index;
    boolean[] Alli_2Index;
    int i_2Index;
    boolean[] Alli_3Index;
    int i_3Index;

    public double[][] transform(int n, double[][] z_ij, int j, int i_1, int i_2, int i_3){
        double[][] z_prime = new double[n][n];
        return z_prime;
    }

    public double[] shift(int n, double[][] z_ij, int j, int i_1, int i_2, int i_3, double delta, int[] x_i){
        double[] a_i = new double[n];
        isBeforShiftValid(z_ij, j, i_1, i_2, i_3, delta, x_i);
        //For alle außer i_1, i_2, i_3
        if (shiftIsPossible){
            for (int i = 0; i < n; i++){
                if ((i != i_1) && (i != i_2) && (i != i_3)){
                    a_i[i] = z_ij[i][j];
                }
            }
        }
        //dann a_i ist einfach die Spalte in z_ij
        if (shiftIsPossible == false){
            for (int i = 0; i < n; i++){
                a_i[i] = z_ij[i][j];
            }
        }

        InitArraysToShift(n);
        boolean firstRun = true;

        while (shiftIsPossible){
            setArraysToShift(i_1, i_2, i_3);

            a_i[i_2] = z_ij[i_2][j] + delta;
            if ((firstRun == true) && (x_i[i_1] == x_i[i_3])){
                a_i[i_1] = z_ij[i_1][j] - delta;
                a_i[i_3] = z_ij[i_3][j];
            } else if (x_i[i_1] != x_i[i_3]) {
                a_i[i_1] = z_ij[i_1][j] - (delta*((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3])));
                a_i[i_3] = z_ij[i_3][j] -(delta*((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3])));
            }
            firstRun = false;
            //FOR i_1
            i_1 = setIndexi_1(a_i, i_1, i_2 ,i_3, delta);
            i_3 = setIndexi_3(a_i, i_1, i_2 ,i_3, delta);


        }
        return a_i;
    }

    private int setIndexi_2(double[] a_i, int i_1, int i_2, int i_3, double delta, int j, double[][] z_ij){
        /* Is Summe = 1 in Zeile,
        kann delte noch drauf addiert werden somit es nicht größer als 1 wird,
         falls neue Index wird für i_2 dann ist er in i_1 oder i_3

         wir prüfen bis j, aber mit weiteren Zeilen es wird größer als 1, macht schi keine Soregen erst darüber
         */

        double sum = 0.0;
        for (int index_j = 0; index_j < j; index_j++){
            sum = sum + z_ij[i_2][j];
        }
        if (sum >= 1.0){

        }
        return i_2;
    }


    private int setIndexi_1(double[] a_i, int i_1, int i_2, int i_3, double delta){
        if ((isDelatuseableForBounds(a_i[i_1], delta) == false) || (a_i[i_1] == 0.0)){
            shiftIsPossible = false;
            if ((Alli_2Index[i_1 + 1] == false) && (Alli_3Index[i_1 + 1] == false)){
                if ((i_1 + 1 != i_2) && (i_1 + 1 != i_3)){
                    i_1++;
                    shiftIsPossible = true;
                }
            }
        }
        return i_1;
    }

    private int setIndexi_3(double[] a_i, int i_1, int i_2, int i_3, double delta){
        if ((isDelatuseableForBounds(a_i[i_3], delta) == false) || (a_i[i_3] == 0.0)){
            shiftIsPossible = false;
            if ((Alli_1Index[i_3 - 1] == false) && (Alli_2Index[i_3 - 1] == false)){
                if ((i_3 - 1 != i_1) && (i_3 - 1 != i_2)){
                    i_3--;
                    shiftIsPossible = true;
                }
            }
        }
        return i_3;
    }

    private void InitArraysToShift(int n){
        Alli_1Index = new boolean[n];
        i_1Index = 0;
        Alli_2Index = new boolean[n];
        i_2Index = 0;
        Alli_3Index = new boolean[n];
        i_3Index = 0;
    }

    private void setArraysToShift(int i_1, int i_2, int i_3){
        Alli_1Index[i_1] = true;
        Alli_2Index[i_2] = true;
        Alli_3Index[i_3] = true;
    }

    private boolean isDelatuseableForBounds(double a_i, double sub){
        if ((a_i - sub) >= 0.0){
            return true;
        }
        return false;
    }

    private boolean interwallValidation(int i_1, int i_2, int i_3){
        if ((i_2 > i_1) && (i_3 > i_2) && (i_3 > (i_1 + 1))){
             return true;
        }
        return false;
    }

    private void isBeforShiftValid(double[][] z_ij, int j, int i_1, int i_2, int i_3, double delta, int[] x_i){
        if (interwallValidation(i_1, i_2, i_3)){
            do{
                shiftIsPossible = true;
                double temp_ai = z_ij[i_1][j] - (delta*((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3])));
                if (z_ij[i_1][j] <= 0.0 || temp_ai < 0.0){
                    shiftIsPossible = false;
                    i_1++;
                }
                temp_ai = z_ij[i_3][j] -(delta*((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3])));
                if (z_ij[i_3][j] <= 0.0 || (temp_ai < 0.0)){
                    shiftIsPossible = false;
                    i_3--;
                }
            }while (shiftIsPossible != true);
        }
        if (interwallValidation(i_1, i_2, i_3) == false){
            shiftIsPossible = false;
        }
    }

}
