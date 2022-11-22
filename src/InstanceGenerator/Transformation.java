package InstanceGenerator;

//TODO: teste alle Funktionen :(
//TODO: Fehler Behandlung

public class Transformation {
    /*
    i_1, j kann auch 0 sein, unsere Matrix ist von 0 nummeriert,
    also wenn wenn i_1 ist 1, das ist also zweite Zeile in Matrix.
     */
    boolean shiftIsPossible;
    int i_1;
    int i_2;
    int i_3;
    boolean[] Alli_1Index;
    boolean[] Alli_2Index;
    boolean[] Alli_3Index;

    private void InitArraysToShift(int n){
        Alli_1Index = new boolean[n];
        Alli_2Index = new boolean[n];
        Alli_3Index = new boolean[n];
    }

    private void setArraysToShift(){
        Alli_1Index[i_1] = true;
        Alli_2Index[i_2] = true;
        Alli_3Index[i_3] = true;
    }

    private double[] intArrayToDouble(int[] x_i){
        int l = x_i.length;
        double[] double_x_i = new double[l];
        for (int i = 0; i < l; i++){
            double_x_i[i] = x_i[i];
        }
        return double_x_i;
    }

    public void setFirstIndexs(int i1, int i2, int i3){
        i_1 = i1;
        i_2 = i2;
        i_3 = i3;
    }

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

    private double[] ifShiftIsNotPossible(double[] a_i, double[][] z_ij, int n, int j){
        //TODO: wenn return z_ij, dann man kann es löschen
        for (int i = 0; i < n; i++){
            a_i[i] = z_ij[i][j];
        }
        return a_i;
    }

    public double[][] shift(int n, double[][] z_ij, int j, int i1, int i2, int i3, double delta, int[] x_i){
        setFirstIndexs(i1,i2,i3);
        double[] a_i = new double[n];
        double[] double_x_i = intArrayToDouble(x_i);
        isBeforShiftValid(z_ij, j, delta, double_x_i);
        double sum = calculateSumBeforShift(z_ij, i_2, j);
        if (checkSum(sum, delta) == false){
            shiftIsPossible = false;
        }
        //For alle außer i_1, i_2, i_3
        //TODO:Frag ob es für alle i_1, i_2 und i_3 richtig ist
        /*
        if (shiftIsPossible){
            for (int i = 0; i < n; i++){
                if ((i != i_1) && (i != i_2) && (i != i_3)){
                    a_i[i] = z_ij[i][j];
                }
            }
        }
        */
        //dann a_i ist einfach die Spalte in z_ij
        //TODO: wenn return z_ij, dann man kann es löschen
        if (shiftIsPossible == false){
            a_i = ifShiftIsNotPossible(a_i, z_ij, n, j);
        }
        InitArraysToShift(n);
        boolean firstRun = true;

        while (shiftIsPossible){
            setArraysToShift();
            a_i[i_2] = z_ij[i_2][j] + delta;
            if ((firstRun == true) && (double_x_i[i_1] == double_x_i[i_3])){
                a_i[i_1] = z_ij[i_1][j] - delta;
                a_i[i_3] = z_ij[i_3][j];
            } else{
                a_i[i_1] = z_ij[i_1][j] - (delta*((double_x_i[i_2]-double_x_i[i_3])/(double_x_i[i_1]-double_x_i[i_3])));
                a_i[i_3] = z_ij[i_3][j] - (delta*((double_x_i[i_1]-double_x_i[i_2])/(double_x_i[i_1]-double_x_i[i_3])));
            }
            z_ij = swapColumn(z_ij, a_i, j, n);
            firstRun = false;
            setIndexi_1(a_i, delta);
            setIndexi_3(a_i, delta);
            setIndexi_2(a_i, delta, j, z_ij);
        }
        //TODO: check ob es funkiniert
        a_i = setRestIndexes_ai(a_i, z_ij, j, n);
        z_ij = swapColumn(z_ij, a_i, j, n);
        //return a_i;
        return z_ij;
    }

    private double[] setRestIndexes_ai(double[] a_i, double[][] z_ij, int j, int n){
        if (shiftIsPossible){
            for (int i = 0; i < n; i++){
                if ((Alli_1Index[i] == false) && (Alli_2Index[i] == false) && (Alli_3Index[i] == false)){
                    a_i[i] = z_ij[i][j];
                }
            }
        }
        return a_i;
    }

    private double isSumForIndex_i2(double[][] z_ij, int i_2, int j){
        double sum = 0.0;
        for (int i = 0; i <= i_2; i++){
            sum = sum + z_ij[i][j];
        }
        return sum;
    }


    private void setIndexi_2(double[] a_i, double delta, int j, double[][] z_ij){
        double sumForIndex_i2 = isSumForIndex_i2(z_ij, i_2, j);
        if (checkSum(sumForIndex_i2, delta) == false){
            shiftIsPossible = false;
            while (true) {
                i_2--;
                if(Alli_1Index[i_2] == false){
                    sumForIndex_i2 = isSumForIndex_i2(z_ij, i_2, j);
                    if (checkSum(sumForIndex_i2, delta)){
                        shiftIsPossible = true;
                        return;
                    }
                }
                if (Alli_1Index[i_2] == true){
                    break;
                }
            }
            while (true){
                i_2++;
                if (Alli_3Index[i_2] == false){
                    sumForIndex_i2 = isSumForIndex_i2(z_ij, i_2, j);
                    if (checkSum(sumForIndex_i2, delta)){
                        shiftIsPossible = true;
                        return;
                    }
                }
                if (Alli_3Index[i_2] == true){
                    break;
                }
            }
        }
        return;
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


    private void setIndexi_1(double[] a_i, double delta){
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
                    return;
                }
            }
        }while (test != true);
        return;
    }

    private void setIndexi_3(double[] a_i, double delta){
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
                    return;
                }
            }
        }while (test != true);
        return;
    }

    private boolean isDelatuseableForBounds(double a_i, double sub){
        if ((a_i - sub) >= 0.0){
            return true;
        }
        return false;
    }

    private boolean interwallValidation(){
        if ((i_2 > i_1) && (i_3 > i_2) && (i_3 > (i_1 + 1))){
             return true;
        }
        return false;
    }

    private boolean isSumForIndex_i2BeforShiftSmallerAsOne(double[][] z_ij, int i_2, int j, double delta){
        double sum = 0.0;
        for (int i = 0; i <= j; i++){
            sum = sum + z_ij[i_2][i];
            if ((sum >= 1.0) || (sum + delta > 1.0)){
                return false;
            }
        }
        return true;
    }

    private void isBeforShiftValid(double[][] z_ij, int j, double delta, double[] x_i){
        //TODO: suche neue i_2 hier um zu überprüfen am besten neue Funktion
        if ((interwallValidation() == false) || (isSumForIndex_i2BeforShiftSmallerAsOne(z_ij, i_2, j, delta) == false)){
            shiftIsPossible = false;
            return;
        }

        while(true){
            shiftIsPossible = true;
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
