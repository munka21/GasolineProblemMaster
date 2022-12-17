package Greedy;
import java.util.*;
import java.util.stream.Collectors;

public class GreedyAlgo {

    int absoluteDistanceFromZero;
    List<Integer> remaining_X;
    List<Integer> remaining_Y;

    public double[][] doGreedy(double[][] z_greedy, int[] y, int[] x){
        setParametersForFirstTime(y, x);
        lookFirstX();
        int n = remaining_Y.size();
        for (int j = 0; j < n; j++){
            subtractY();
            addX(z_greedy, j);
        }
        return z_greedy;
    }

    private void setParametersForFirstTime(int[] y, int[] x){
        absoluteDistanceFromZero = 0;
        remaining_X = Arrays.stream(x).boxed().collect(Collectors.toList());
        remaining_Y = Arrays.stream(y).boxed().collect(Collectors.toList());
    }

    private void lookFirstX(){
        absoluteDistanceFromZero = absoluteDistanceFromZero + remaining_X.get(remaining_X.size() - 1);
        remaining_X.remove(remaining_X.size() - 1);
    }

    private void subtractY(){
        absoluteDistanceFromZero = absoluteDistanceFromZero - remaining_Y.get(0);
        remaining_Y.remove(0);
    }

    private void addX(double[][] z_greedy, int j){
        if (remaining_X.isEmpty() == false){
            int min = Math.abs(absoluteDistanceFromZero + remaining_X.get(0));
            int index = 0;
            for (int i = 0; i < remaining_X.size(); i++){
                int min_temp = Math.abs(absoluteDistanceFromZero + remaining_X.get(i));
                if (min_temp < min){
                    min = min_temp;
                    index = i;
                }
            }
            absoluteDistanceFromZero = absoluteDistanceFromZero + remaining_X.get(index);
            remaining_X.remove(index);
            z_greedy[index][j] = 1.0;
        }
    }


}
