package InstanceGenerator;

import java.util.Arrays;

import static Tests.MatrixOp.*;

public class Transform extends Shift {
    int n;
    int current_i2;
    int current_i1;
    int current_i3;
    int j_prime;
    double[] z_j;
    boolean exits_i1;
    boolean exits_i3;
    double delta;

    int test_beide_true=0;
    int test_eine_true=0;

    private void test_print(double[][] z_ij, int j){
        System.out.println("\nz_j:");
        System.out.println(Arrays.toString(z_j)+ "\n");

        setCurrent_i2();
        set_j_prime(z_ij, j + 1);//Wir starten Suche nach j
        setCurrent_i1();
        setCurrent_i3();

        System.out.println("j_prime: " + j_prime);
        System.out.println("current_i2: " + current_i2);
        System.out.println("current_i1: " + current_i1 + " exits_i1: " + exits_i1);
        System.out.println("current_i3: " + current_i3 + " exits_i3: " + exits_i3);
        if ((exits_i1) && (exits_i3)){
            test_beide_true++;
        } else if ((exits_i1) || (exits_i3)) {
            test_eine_true++;
        }
        System.out.println("test_eine_true : " + test_eine_true);
        System.out.println("test_beide_true : " + test_beide_true);
        if (((exits_i1) && (exits_i3 == false)) || ((exits_i3) && (exits_i1 == false))){
            System.out.println("Es ist Hier\n");
        }
        else {
            System.out.println("Es ist nicht hier\n");
        }
    }

    public double[][] doTransform(double[][] z_ij, int j, int[] int_xi) {
        double[] x_i = intArrayToDouble(int_xi);
        int test = 0;
        do {
            test++;

            calculateFractionalValues(z_ij);

            test_print(z_ij,j);//TODO:löschen

            if ((exits_i1) && (exits_i3)){
                setDelta(z_ij, x_i);
                System.out.println("new Delta: " + delta);
                z_ij = useFormel(z_ij, x_i);
                System.out.println("After Shift fot Transform");
                testPrintMatrix2D(z_ij);
            } else if ((exits_i1) && (exits_i3 == false)) {
                /*
                Wie soll man die behandeln? eine neue angepasste set und
                use Funktionen oder eher es gibt eine, sondern behandlung,
                oder wie jetzt gar nicht beachten und ignorieren, beide Fälle
                kommen select vor, um die öfter zu treffen, die Interwall von
                JobsGröße muss klein sein, damit die Jobs sher ähnlich sind
                 */
                break;

            } else if ((exits_i1 == false) && (exits_i3)) {
                break;
            }


        } while (check_zj() != true);


        return z_ij;
    }

    private boolean check_zj(){
        for (int i = 0; i < n; i++){
            if ((round(z_j[i], 3) > 1.000) || ((round(z_j[i], 3) < 0.999))){
                return false;
            }
        }
        return true;
    }

    private double[][] useFormel (double[][] z_ij, double[] x_i){
        double y_i1 = ((x_i[current_i2]-x_i[current_i3])/(x_i[current_i1]-x_i[current_i3]));
        double y_i3 = ((x_i[current_i1]-x_i[current_i2])/(x_i[current_i1]-x_i[current_i3]));
        z_ij[current_i2][j_prime] = z_ij[current_i2][j_prime] - delta;
        if (x_i[current_i1] == x_i[current_i3]){
            z_ij[current_i1][j_prime] = z_ij[current_i1][j_prime] + delta;
            z_ij[current_i3][j_prime] = z_ij[current_i3][j_prime];
        } else{
            z_ij[current_i1][j_prime] = z_ij[current_i1][j_prime] + (delta * y_i1);
            z_ij[current_i3][j_prime] = z_ij[current_i3][j_prime] + (delta * y_i3);
        }
        return z_ij;
    }

    private void setDelta(double[][] z_ij, double[] x_i){
        double y_i1 = ((x_i[current_i2]-x_i[current_i3])/(x_i[current_i1]-x_i[current_i3]));
        double y_i3 = ((x_i[current_i1]-x_i[current_i2])/(x_i[current_i1]-x_i[current_i3]));
        double maxDelta_i1 = (1.0 - z_j[current_i1])/y_i1;
        double maxDelta_i3 = (1.0 - z_j[current_i3])/y_i3;
        delta = Math.min(maxDelta_i1, maxDelta_i3);
        double maxDelta_i2 = z_j[current_i2] - 1.0;//Erst wie viel in der Zeile müssen wir subtrahieren
        maxDelta_i2 = Math.min(maxDelta_i2, z_ij[current_i2][j_prime]);//Prüfen, ob wir es direkt in diese Spalte geht
        delta = Math.min(delta, maxDelta_i2);
    }

    private void setCurrent_i3(){
        for (int i = current_i2; i < n; i++){
            if (z_j[i] < 1.0){
                current_i3 = i;
                exits_i3 = true;
                return;
            }
        }
        exits_i3 = false;
    }

    private void setCurrent_i1(){
        for (int i = 0; i < current_i2; i++){
            if (z_j[i] < 1.0){
                current_i1 = i;
                exits_i1 = true;
                return;
            }
        }
        exits_i1 = false;
    }

    private void set_j_prime(double[][] z_ij, int j){
        for (; j < n; j++){
            if (z_ij[current_i2][j] > 0.0){
                j_prime = j;
                return;
            }
        }
    }

    private void setCurrent_i2(){
        isShiftPossible = false;
        for (int i = 0; i < n; i++){
            if (z_j[i] > 1.0){
                current_i2 = i;
                isShiftPossible = true;
                return;
            }
        }
    }

    private void calculateFractionalValues(double[][] z_ij){
        n = z_ij.length;
        z_j = new double[n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z_j[i] = z_j[i] + z_ij[i][j];
            }
        }
    }

}