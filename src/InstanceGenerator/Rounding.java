package InstanceGenerator;
import java.util.ArrayList;


public class Rounding {


    public double[][] doRoundingTemp(double[][] z_ij){
        Graph[] graphen = new Graph[z_ij.length];
        for (int j = 0; j < z_ij.length; j++){
            graphen[j] = new Graph();
            if (j == 0){
                graphen[j].setGraph(j, z_ij, graphen[j]);
                graphen[j].setAktivBlockForFirstGraph(graphen[j]);
            }
            else {
                graphen[j].setGraph(j, z_ij, graphen[j-1]);
                graphen[j].setAktivBlock(graphen[j], graphen[j-1]);
            }
            //graphen[j].printGraph();
            z_ij = roundingAlgo(z_ij, graphen[j]);
        }
        return z_ij;
    }

    public double[][] roundingAlgo(double[][] z_ij, Graph g){
        int j = g.getIndex_j();
        ArrayList<Integer> aktivBlock = g.getAktivBlock();
        z_ij = setSolutionMatrix(z_ij, j, aktivBlock);
        return z_ij;
    }

    private double[][] setSolutionMatrix(double[][] z_ij, int j, ArrayList<Integer> aktivBlock){
        for (int Block : aktivBlock){
            if (checkIfEverywhereZero(z_ij, Block, j)){
                z_ij[Block][j] = 1.0;
                z_ij =setColumnToZero(z_ij, j, Block);
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
}
