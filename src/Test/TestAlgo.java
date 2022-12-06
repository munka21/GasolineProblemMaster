package Test;

public class TestAlgo {
    public void doTest(double[][] z_ij, int[] x_i, int[] y_i) throws Exception {
        int n = z_ij.length;
        int tank = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (z_ij[i][j] > 0.0){
                    tank = tank + x_i[i] - y_i[j];
                    if (tank < 0){
                        throw new Exception("The result is incorrect");
                    }
                }
            }
        }
    }
}
