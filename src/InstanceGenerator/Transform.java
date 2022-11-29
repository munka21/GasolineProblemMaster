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
        current_i1 = IndexesSmallerThanOne.get(0);//TODO: vereinfachen mit diese Abkurzung und analog für current_i3 TODO: check cb es funktioniert
        current_i3 = IndexesSmallerThanOne.get(IndexesSmallerThanOne.size() - 1);
        if (checkExistOf_i3(z_ij) && checkExistOf_i1(z_ij)){
            setDelta(z_ij, x_i);
            useFormel(z_ij, x_i, current_jPrime);
            //TODO: es wird alles in jede Schleife-Verlauf alles gesucht und gelöscht, es geht effizenzer, mit aktiven Löschen und aktualisieren von zJ, macht wenn funktioniert
            //IndexesSmallerThanOne.remove(0);
            //IndexesSmallerThanOne.remove(IndexesSmallerThanOne.size() - 1);
        }
        else if (checkExistOf_i1(z_ij) && checkExistOf_i3(z_ij) == false){
            setDeltaForOnlyOne_i1(z_ij, x_i);
            //IndexesSmallerThanOne.remove(0);
        }
        else if (checkExistOf_i3(z_ij) && checkExistOf_i1(z_ij) == false) {
            setDeltaForOnlyOne_i3(z_ij, x_i);
            //IndexesSmallerThanOne.remove(IndexesSmallerThanOne.size() - 1);
        }
        //IndexesGreaterThanOne.remove(0);
        //setAgain_zj();
        setList("clear", 0);

        return z_ij;
    }

    private double[][] useFormel (double[][] z_ij, double[] x_i, int j){
        double y_i1 = ((x_i[current_i2]-x_i[current_i3])/(x_i[current_i1]-x_i[current_i3]));
        double y_i3 = ((x_i[current_i1]-x_i[current_i2])/(x_i[current_i1]-x_i[current_i3]));
        z_ij[current_i2][j] = z_ij[current_i2][j] - delta;
        if (x_i[current_i1] == x_i[current_i3]){
            z_ij[current_i1][j] = z_ij[current_i1][j] + delta;
            z_ij[current_i3][j] = z_ij[current_i3][j];
        } else{
            z_ij[current_i1][j] = z_ij[current_i1][j] + (delta * y_i1);
            z_ij[current_i3][j] = z_ij[current_i3][j] + (delta * y_i3);
        }
        return z_ij;
    }

    private void setDelta(double[][] z_ij, double[] x_i){
        /*TODO: du musst prüfen ob xi1 == xi3 ist, weil dann wurde, und wenn nur i1 oder i2 exist dann komplete
           Delta zu diese i und die komplet von i, wenn mehre i erstmal lassen, also max was geht in den Fall zu
           eine von diese i, und fall noch was übrig dann zu nächste i.
           Dann haben wir useForm for meisten Fällen und separate für sonder Fälle.
           Am besten set boolen varablen ob beiden indexen i1 und i3 sind vorhanden Falls ja, dann es ist normal
           Fall falls nicht dann sonder Fall.
         */
        double y_i1 = ((x_i[current_i2]-x_i[current_i3])/(x_i[current_i1]-x_i[current_i3]));
        double y_i3 = ((x_i[current_i1]-x_i[current_i2])/(x_i[current_i1]-x_i[current_i3]));
        double maxDelta_i1 = (1.0 - z_j[current_i1])/y_i1;
        double maxDelta_i3 = (1.0 - z_j[current_i3])/y_i3;
        double maxDelta_i2 = z_j[current_i2] - 1.0;
        delta = Math.min(maxDelta_i1, maxDelta_i2);
        delta = Math.min(delta, maxDelta_i3);
    }

    private void setDeltaForOnlyOne_i1(double[][] z_ij, double[] x_i){
        /*
        double maxDelta = 1.0 - z_j[current_i1];//TODO: löschen aus dem list
        double maxDelta_i2 = z_j[current_i2] - 1.0;
        delta = Math.min(maxDelta, maxDelta_i2);
        or
        1.0 = z_j + delta*yi
        1-z_j = delta * yi
    (   1-z_j)/yi = delta
        */
        //TODO: check this
        double y_i1 = ((x_i[current_i2]-x_i[current_i3])/(x_i[current_i1]-x_i[current_i3]));
        double maxDelta = (1.0 - z_j[current_i1])/y_i1;
        double maxDelta_i2 = z_j[current_i2] - 1.0;
        delta = Math.min(maxDelta, maxDelta_i2);
    }

    private void setDeltaForOnlyOne_i3(double[][] z_ij, double[] x_i){
        //TODO: check this
        double y_i3 = ((x_i[current_i1]-x_i[current_i2])/(x_i[current_i1]-x_i[current_i3]));
        double maxDelta = (1.0 - z_j[current_i3])/y_i3;
        double maxDelta_i2 = z_j[current_i2] - 1.0;
        delta = Math.min(maxDelta, maxDelta_i2);
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

    private void lookFor_jPrime(double[][] z_ij, int j){
        int n = z_ij.length;
        current_i2 = IndexesGreaterThanOne.get(0);
        //IndexesGreaterThanOne.remove(0);//TODO: nicht hier, sonder wenn er fertig ist mid diese i
        for (int j_prime = j + 1; j_prime < n; j_prime++){
            if (z_ij[current_i2][j_prime] > 0.0000){
                current_jPrime = j_prime;
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
