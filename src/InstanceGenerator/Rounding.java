package InstanceGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class Rounding {
    ArrayList<Integer> activeBlocks = new ArrayList<>();

    public double[][] doRounding(double[][] z_ij){
        for (int j = 0; j < z_ij.length; j++){
            lookForAktivBlock(z_ij, j);
            z_ij = setSolutionMatrix(z_ij, j);
        }
        System.out.println("\nResult:");
        testPrintMatrix(z_ij);
        return z_ij;
    }

    private void lookForAktivBlock(double[][] z_ij, int j){
        for (int i = 0; i < z_ij.length; i++){
            if (z_ij[i][j] > 0.000000000000000){
                if (activeBlocks.contains(i) == false){
                    activeBlocks.add(i);
                }
            }
        }
        activeBlocks.sort(Comparator.naturalOrder());
    }

    private double[][] setSolutionMatrix(double[][] z_ij, int j){
        for (int activeBlock : activeBlocks){
            if (checkIfEverywhereZero(z_ij, activeBlock, j)){
                z_ij[activeBlock][j] = 1.0;
                setColumnToZero(z_ij, j, activeBlock);
                break;
            }
        }
        return z_ij;
    }

    private boolean checkIfEverywhereZero(double[][] z_ij, int i, int j){
        for (int  x = j - 1; x >= 0; x--){
            if (z_ij[i][x] > 0.00000000000000000){
                return false;
            }
        }
        return true;
    }

    private double[][] setColumnToZero(double[][] z_ij, int j, int setOne){
        for (int i = 0; i < z_ij.length; i++){
            if (i != setOne){
                z_ij[i][j] = 0.0;
            }
        }
        return z_ij;
    }

    private static void testPrintMatrix(double[][] z_ij){
        int n = z_ij.length;
        for (int i = 0; i < n; i++){
            System.out.println(Arrays.toString(z_ij[i]));
        }
    }


}
