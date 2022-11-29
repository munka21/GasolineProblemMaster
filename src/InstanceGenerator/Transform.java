package InstanceGenerator;

import java.util.*;

public class Transform extends Shift{

    List IndexesGreaterThanOne = new ArrayList();
    List IndexesSmallerThanOne = new ArrayList();
    double[] z_j;
    int current_i1;
    int current_i2;
    int current_i3;
    int current_jPrime;
    double delta;
    boolean isShiftPossible;

    public double[][] doTransform(double[][] z_ij, int j, int[] int_xi){
        double[] x_i = intArrayToDouble(int_xi);

        calculateFractionalValuesAndSetsLists(z_ij);
        setCurrent_i_jPrime(z_ij,j);//TODO:Nachdem Funktion man kann prüfen ob wir etwas drin haben, Fall nein abbrechen, so kann man unendliche Schleife brechen.

        System.out.println("\nz_j:\n");
        System.out.println(Arrays.toString(z_j));
        System.out.println("\nIndexes Greater Than One:\n");
        System.out.println(Arrays.toString(IndexesGreaterThanOne.toArray()));
        System.out.println("\nIndexes Smaller Than One:\n");
        System.out.println(Arrays.toString(IndexesSmallerThanOne.toArray()) + "\n");
        System.out.println("current_i2 : " + current_i2 + " current_jPrime : " + current_jPrime);
        System.out.println("\n*****************************\n");

        setDelta();
        z_ij = doShiftForTransform(z_ij);

        setList("clear", 0);


        return z_ij;
    }

    /*
        System.out.println("\nz_j:\n");
        System.out.println(Arrays.toString(z_j));
        System.out.println("\nIndexes Greater Than One:\n");
        System.out.println(Arrays.toString(IndexesGreaterThanOne.toArray()));
        System.out.println("\nIndexes Smaller Than One:\n");
        System.out.println(Arrays.toString(IndexesSmallerThanOne.toArray()) + "\n");
        System.out.println(Arrays.toString(setCurrent_i_jPrime(z_ij,j)));
        System.out.println("\n*****************************\n");
     */

    private double[][] doShiftForTransform(double[][] z_ij){
        return z_ij;
    }

    private void setDelta(){
        double toSubFrom_i2 = z_j[current_i2] - 1.0;//MaxDeltaFürZeile_i2
    }

    private double[][] useFormel (double[][] z_ij, double[] x_i, int j){
        double y_i1 = ((x_i[i_2]-x_i[i_3])/(x_i[i_1]-x_i[i_3]));
        double y_i3 = ((x_i[i_1]-x_i[i_2])/(x_i[i_1]-x_i[i_3]));
        z_ij[i_2][j] = z_ij[i_2][j] - delta;
        if (x_i[i_1] == x_i[i_3]){
            z_ij[i_1][j] = z_ij[i_1][j] + delta;
            z_ij[i_3][j] = z_ij[i_3][j];
        } else{
            z_ij[i_1][j] = z_ij[i_1][j] + (delta * y_i1);
            z_ij[i_3][j] = z_ij[i_3][j] + (delta * y_i3);
        }
        return z_ij;
    }
    private void setCurrent_i_jPrime(double[][] z_ij, int j){
        int n = z_ij.length;
        for (int i = 0; i < n; i++){//TODO:check if out of bound
            if (z_j[i] > 1.0){
                for (int j_prime = j + 1; j_prime < n; j_prime++){
                    if (z_ij[i][j_prime] > 0.00000000){
                        current_i2 = i;
                        current_jPrime = j_prime;
                        return;
                    }
                }
            }
        }
    }

    private void calculateFractionalValuesAndSetsLists(double[][] z_ij){
        int n = z_ij.length;
        z_j = new double[n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z_j[i] = z_j[i] + z_ij[i][j];
            }
            setList("set", i);
        }
    }

    private void setList(String whatToDo, int index){
        switch (whatToDo){
            case "clear":
                IndexesGreaterThanOne.clear();
                IndexesSmallerThanOne.clear();
                break;
            case "set":
                if ( z_j[index] > 1.0){
                    IndexesGreaterThanOne.add(index);
                }
                if ( z_j[index] < 1.0){
                    IndexesSmallerThanOne.add(index);
                }
                break;
        }
    }

}
