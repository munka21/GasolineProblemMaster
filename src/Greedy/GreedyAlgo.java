package Greedy;


import java.util.Arrays;

public class GreedyAlgo {
    int index = 0;
    int indexBiggestX = 0;
    boolean xExisit;

    private void searchForX(int []x, boolean[] isUsed, int y, int n, int tank){
        int toMinTank = x[indexBiggestX];
        for (int i = 0; i < n; i++){
            if (((x[i]+tank) >= y) && (isUsed[i] == false) && (toMinTank > x[i] - y)){
                toMinTank = x[i] - y;
                index = i;
                xExisit = true;
            }
        }
    }

    private int searchForBiggestX(boolean[] isUsed, int n){
        for (int i = 0; i < n; i++){
            if (isUsed[i] == false){
                return i;
            }
        }
        return 0;
    }

    //TODO: Prüfe ob es alles richtig löst, und ob es immer eine göltige Lösung ist
    public int[][] solveProblem(int n, int[] x, int[] y){
        int[][] R = new int[n][n];
        boolean[] isUsed = new boolean[n];
        int tank = 0;
        for (int j = 0; j < n; j++){
            searchForX(x, isUsed, y[j], n, tank);
            if (xExisit == true){
                tank = tank + x[index] - y[j];
                isUsed[index] = true;
                R[index][j] = 1;
                if (index == indexBiggestX){
                    indexBiggestX = searchForBiggestX(isUsed, n);
                }
                xExisit = false;
            }
            else if (xExisit == false){
                //Es wird größte x_i genomen um y_j zu verkleiner
                y[j] = y[j] - x[indexBiggestX];
                isUsed[index] = true;
                R[index][j] = 1;
                indexBiggestX = searchForBiggestX(isUsed, n);
                j--;
            }
        }
        return R;
    }

    public void printOutput(int[][] R, int n){
        System.out.println("\nThe solution of gasoline problem with greedy:");
        for (int i = 0; i < n; i++){
            System.out.println(Arrays.toString(R[i]));
        }
    }
}
