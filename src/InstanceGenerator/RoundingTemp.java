package InstanceGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class RoundingTemp {
    ArrayList<Integer> activeBlocks = new ArrayList<>();

    public double[][] doRounding(double[][] z_ij){
        for (int j = 0; j < z_ij.length; j++){
            lookForAktivBlock(z_ij, j);
            setSolutionMatrix(z_ij, j);
        }
        System.out.println("Result:\n");
        testPrintMatrix(z_ij);
        return z_ij;
    }

    private void lookForAktivBlock(double[][] z_ij, int j){
        for (int i = 0; i < z_ij.length; i++){
            if (z_ij[i][j] > 0.000000000000000){
                activeBlocks.add(i);
            }
        }
        activeBlocks.sort(Comparator.naturalOrder());
    }

    private double[][] setSolutionMatrix(double[][] z_ij, int j){
        for (int activeBlock : activeBlocks){
            if (checkIfEverywhereZero(z_ij, activeBlock, j)){
                z_ij[activeBlock][j] = 1.0;
                activeBlocks.remove(activeBlock);
                setColumnToZero(z_ij, j);
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

    private double[][] setColumnToZero(double[][] z_ij, int j){
        for (int i = 0; i < z_ij.length; i++){
            if (z_ij[i][j] < 1.0){
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
