package InstanceGenerator;

import java.util.*;

public class Graph {

    private int index_j;
    private ArrayList<Integer> nodesGreaterThanZero = new ArrayList<Integer>();
    private ArrayList<Edge> edges = new ArrayList<Edge>();;
    HashMap mapOfEdges = new HashMap();

    public void setGraph(int j, double[][] z_ij, Graph g_last){
        setIndex_j(j);
        setNodes(j, z_ij);
        setEdges(g_last);
    }

    private void setIndex_j(int j){
        index_j = j;
    }

    private void setNodes(int j, double[][] z_ij){
        for (int i = 0; i < z_ij.length; i++){
            if (z_ij[i][j] > 0.0000000000){
                nodesGreaterThanZero.add(i);
            }
        }
    }

    private void setEdgesAndMapFromLastGraph(Graph g_last){
        edges = g_last.edges;
        mapOfEdges = g_last.mapOfEdges;
    }

    private void setEdges(Graph g_last){
        if (g_last.edges != null){
            setEdgesAndMapFromLastGraph(g_last);
        }
        for (int i = 0; i < nodesGreaterThanZero.size(); i++){
            for (int j = i + 1; j < nodesGreaterThanZero.size(); j++){
                Edge e = new Edge();
                e.node_1 = nodesGreaterThanZero.get(i);
                e.node_2 = nodesGreaterThanZero.get(j);
                String str = Integer.toString(e.node_1) + Integer.toString(e.node_2);
                if (mapOfEdges.containsKey(str) == false){
                    edges.add(e);
                    mapOfEdges.put(str, e);
                }
            }
        }
    }

    public void printGraph(){
        System.out.println("Index_j: " + index_j);
        System.out.println("Nodes: " + Arrays.toString(nodesGreaterThanZero.toArray()));
        for (int i = 0; i < edges.size(); i++){
            System.out.println("Edge: " + edges.get(i).node_1 + " --- " + edges.get(i).node_2);
        }
    }


}

class Edge{
    int node_1;
    int node_2;
}
