package InstanceGenerator;

import java.util.*;

public class Transform extends Shift{

    List<Integer> IndexesGreaterThanOne = new ArrayList<Integer>();
    List<Integer> IndexesSmallerThanOne = new ArrayList<Integer>();

    int current_i1;
    int current_i2;
    int current_i3;
    double[] z_j;
    int current_jPrime;
    double delta;
    boolean isShiftPossible;


    public double[][] doTransform(double[][] z_ij, int j, int[] int_xi){
        double[] x_i = intArrayToDouble(int_xi);

        calculateFractionalValuesAndSetsLists(z_ij);
        lookFor_jPrime(z_ij, j);

        if (checkExistOf_i3(z_ij) && checkExistOf_i1(z_ij)){
            setDelta(z_ij);
        }
        else if (checkExistOf_i1(z_ij) && checkExistOf_i3(z_ij) == false){
            setDelta(z_ij);
        }
        else if (checkExistOf_i3(z_ij) && checkExistOf_i1(z_ij) == false) {
            setDelta(z_ij);
        }

        z_ij = doShiftForTransform(z_ij);


        setList("clear", 0);
        return z_ij;
    }

    private boolean checkExistOf_i1(double[][] z_ij){
        for (int i : IndexesSmallerThanOne){
            if (i < current_i2){
                return true;
            }
        }
        return false;
    }

    private boolean checkExistOf_i3(double[][] z_ij){
        for (int i : IndexesSmallerThanOne){
            if (i > current_i2){
                return true;
            }
        }
        return false;
    }

    private void lookForCurrent_i1(double[][] z_ij){
        for (int ele: IndexesSmallerThanOne){
            if (ele < current_i2){
                current_i1 = ele;
                IndexesSmallerThanOne.remove(ele);//TODO: nicht hier, sonder wenn er fertig ist mid diese i
                return;
            }
        }
    }

    private void lookForCurrent_i3(double[][] z_ij){
        for (int ele: IndexesSmallerThanOne){
            if (ele > current_i2){
                current_i3 = ele;
                IndexesSmallerThanOne.remove(ele);//TODO: nicht hier, sonder wenn er fertig ist mid diese i
                return;
            }
        }
    }

    private void lookFor_jPrime(double[][] z_ij, int j){
        int n = z_ij.length;
        current_i2 = IndexesGreaterThanOne.get(0);
        IndexesGreaterThanOne.remove(0);//TODO: nicht hier, sonder wenn er fertig ist mid diese i
        for (int j_prime = j + 1; j_prime < n; j_prime++){
            if (z_ij[current_i2][j_prime] > 0.0000){
                current_jPrime = j_prime;
            }
        }
    }

    private double[][] doShiftForTransform(double[][] z_ij){
        return z_ij;
    }

    private void setDelta(double[][] z_ij){
        /*TODO: du musst prüfen ob xi1 == xi3 ist, weil dann wurde, und wenn nur i1 oder i2 exist dann komplete
           Delta zu diese i und die komplet von i, wenn mehre i erstmal lassen, also max was geht in den Fall zu
           eine von diese i, und fall noch was übrig dann zu nächste i.
           Dann haben wir useForm for meisten Fällen und separate für sonder Fälle.
           Am besten set boolen varablen ob beiden indexen i1 und i3 sind vorhanden Falls ja, dann es ist normal
           Fall falls nicht dann sonder Fall.
         */
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
