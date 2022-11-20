package InstanceGenerator;

//TODO: teste alle Funktionen :(
//TODO: Fehler Behandlung

public class Transformation {
    /*
    i_1, j kann auch 0 sein, unsere Matrix ist von 0 nummeriert,
    also wenn wenn i_1 ist 1, das ist also zweite Zeile in Matrix.
     */
    boolean shiftIsPossible;
    boolean[] Alli_1Index;
    boolean[] Alli_2Index;
    boolean[] Alli_3Index;


    public double[][] transform(int n, double[][] z_ij, int j, int i_1, int i_2, int i_3){
        /*
        In Shift es kann sein, dass mehre i_2 genztzt wurde, soll dann es für jede gemacht werden?
         */
        double[][] z_prime = new double[n][n];
        return z_prime;
    }

    public double[][] swapColumn(double[][] z_ij, double[] a_i, int j, int n){
        for (int i = 0; i < n; i++){
            z_ij[i][j] = a_i[i];
        }
        return z_ij;
    }

    public double[] shift(int n, double[][] z_ij, int j, int i_1, int i_2, int i_3, double delta, int[] x_i){
        double[] a_i = new double[n];
        isBeforShiftValid(z_ij, j, i_1, i_2, i_3, delta, x_i);
        double sum = calculateSumBeforShift(z_ij, i_2, j);
        if (checkSum(sum, delta) == false){
            shiftIsPossible = false;
        }
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


        //TODO:marker wird nicht gändert
        while (shiftIsPossible){
            setArraysToShift(i_1, i_2, i_3);

            //TODO immer z_ij swap muss noch gemacht werden damit es funktionier, sonst machen wir immer das selbe :(
            a_i[i_2] = z_ij[i_2][j] + delta;
            if ((firstRun == true) && (x_i[i_1] == x_i[i_3])){
                a_i[i_1] = z_ij[i_1][j] - delta;
                a_i[i_3] = z_ij[i_3][j];
            } else if (x_i[i_1] != x_i[i_3]) {
                //TODO: alles ist int ==>> entweder 0 oder 1 ==>> Konwentieren zu Double
                a_i[i_1] = z_ij[i_1][j] - (delta*((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3])));
                a_i[i_3] = z_ij[i_3][j] -(delta*((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3])));
            }

            firstRun = false;
            //TODO: macht es als globale Variablen und ändert int auf void, es kann vielleicht funktioniert, er geht in set i_1 und i_3 nicht reinv
            i_1 = setIndexi_1(a_i, i_1, delta);
            i_3 = setIndexi_3(a_i, i_3, delta);
            i_2 = setIndexi_2(a_i, i_2, delta, j, z_ij);
        }
        return a_i;
    }


    private int setIndexi_2(double[] a_i, int i_2, double delta, int j, double[][] z_ij){
        double sum = calculateSum(a_i, z_ij, i_2, j);
        if (checkSum(sum, delta) == false){
            shiftIsPossible = false;
            int temp_i_2 = i_2;
            while (true) {
                temp_i_2--;
                if(Alli_1Index[temp_i_2] == false){
                    sum = calculateSum(a_i, z_ij, temp_i_2, j);
                    if (checkSum(sum, delta) == true){
                        shiftIsPossible = true;
                        return temp_i_2;
                    }
                }
                if (Alli_1Index[temp_i_2] == true){
                    break;
                }
            }
            temp_i_2 = i_2;
            while (true){
                temp_i_2++;
                if (Alli_3Index[temp_i_2] == false){
                    sum = calculateSum(a_i, z_ij, temp_i_2, j);
                    if (checkSum(sum, delta) == true){
                        shiftIsPossible = true;
                        return temp_i_2;
                    }
                }
                if (Alli_3Index[temp_i_2] == true){
                    break;
                }
            }
        }
        return i_2;
    }

    private boolean checkSum(double sum, double delta){
        if ((sum >= 1.0) || (sum + delta > 1.0)){
            return false;
        }
        return true;
    }

    private double calculateSumBeforShift(double[][] z_ij, int index, int j){
        double sum = 0.0;
        for (int index_j = 0; index_j < j; index_j++){
            sum = sum + z_ij[index][j];
        }
        return sum;
    }

    private double calculateSum(double[] a_i, double[][] z_ij, int index, int j){
        double sum = 0.0;
        for (int index_j = 0; index_j < j-1; index_j++){
            sum = sum + z_ij[index][j];
        }
        sum = sum + a_i[index];
        return sum;
    }


    private int setIndexi_1(double[] a_i, int i_1, double delta){
        boolean test;
        do {
            test = true;
            if ((isDelatuseableForBounds(a_i[i_1], delta) == false) || (a_i[i_1] == 0.0)){
                shiftIsPossible = false;
                test =false;
                if ((Alli_2Index[i_1 + 1] == false) && (Alli_3Index[i_1 + 1] == false)){
                    i_1++;
                    shiftIsPossible = true;
                } else if ((Alli_2Index[i_1 + 1] == true) || (Alli_3Index[i_1 + 1] == true)) {
                    shiftIsPossible = false;
                    return i_1;
                }
            }
        }while (test != true);
        return i_1;
    }

    private int setIndexi_3(double[] a_i, int i_3, double delta){
        boolean test;
        do {
            test = true;
            if ((isDelatuseableForBounds(a_i[i_3], delta) == false) || (a_i[i_3] == 0.0)){
                shiftIsPossible = false;
                test = false;
                if ((Alli_1Index[i_3 - 1] == false) && (Alli_2Index[i_3 - 1] == false)){
                    i_3--;
                    shiftIsPossible = true;
                } else if ((Alli_1Index[i_3 - 1] == true) || (Alli_2Index[i_3 - 1] == true)) {
                    shiftIsPossible = false;
                    return i_3;
                }
            }
        }while (test != true);
        return i_3;
    }

    private void InitArraysToShift(int n){
        Alli_1Index = new boolean[n];
        Alli_2Index = new boolean[n];
        Alli_3Index = new boolean[n];
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
        if (interwallValidation(i_1, i_2, i_3) == false){
            shiftIsPossible = false;
            return;
        }
        while(true){
            shiftIsPossible = true;
            //Set temp_ai
            double temp_ai1;
            double temp_ai3;
            if (x_i[i_1] == x_i[i_3]){
                temp_ai1 = z_ij[i_1][j] - delta;
                temp_ai3 = z_ij[i_3][j];
            }
            else {
                temp_ai1 = z_ij[i_1][j] - (delta*((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3])));
                temp_ai3 = z_ij[i_3][j] - (delta*((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3])));
            }

            //set i_1, i_3 and marker shidtIsPossible
            if (z_ij[i_1][j] <= 0.0 || temp_ai1 < 0.0){
                shiftIsPossible = false;
                i_1++;
            }

            if (z_ij[i_3][j] <= 0.0 || (temp_ai3 < 0.0)){
                shiftIsPossible = false;
                i_3--;
            }

            if (shiftIsPossible){
                return;
            }

            if ((i_1 == i_2) || (i_1 == i_3) || (i_2 == i_3)){
                shiftIsPossible = false;
                return;
            }

        }
    }

    public double[] generateFractionalValues(double[][] z_ij, int[] x_i , int n){
        double[] z_j = new double[n];
        for (int j = 0; j < n; j++){
            for (int i = 0; i <n; i++){
                z_j[i] = z_j[i] + (z_ij[i][j] * x_i[i]);
            }
        }
        return z_j;
    }


}
