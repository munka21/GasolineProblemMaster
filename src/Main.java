import gurobi.GRBException;

public class Main extends GasolineProblemGenerator{
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++){
            startAlgo();
        }
    }

}