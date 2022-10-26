package ModelG;
import gurobi.*;

import java.util.Arrays;

public class ModelGurobi {

    public static double[][] solveLP (int n, int x[], int y[]) throws GRBException {

        int constraintnummber = 0;
        double [][] Output = new double[n][n];
        /*
        Erstmal sind die Variablen für Test des Model fest
        gelegt, normalaweise werden die in andere Klasse
        generiert und an des Model weitergegeben
         */

        /*
        //Beispiel 2
        int n = 5;
        int y[] = {9, 4, 3, 7, 2};
        int x[] = {10, 5, 5, 3, 2};
         */

        /*
        //Beispiel 3
        int n = 10;
        int y[] ={9,111,5,18,13,12,18,20,41,30};
        int x[] ={150,40,35,26,10,6,4,3,2,1};
         */
        /*
        //Beispiel 1
        int n = 3;
        int y[] = {9, 4, 7};
        int x[] = {10, 5, 5};
        */


        // Create empty environment, set options, and start:
        GRBEnv env = new GRBEnv(true);
        env.set("logFile", "master.log");
        env.start();

        // Create empty model:
        GRBModel model = new GRBModel(env);

        // Create variables:
        GRBVar alpha = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS , "alpha");
        GRBVar beta = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "beta");
        GRBVar[][] z = new GRBVar[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.CONTINUOUS, "z");
            }
        }

        // Set objective:
        GRBLinExpr expr = new GRBLinExpr();
        expr.addTerm(1.0, beta); expr.addTerm(-1.0, alpha);
        model.setObjective(expr, GRB.MINIMIZE);

        // Add constraints:
        expr = new GRBLinExpr();
        for (int i = 0; i < n; i++){
            expr = new GRBLinExpr();
            for (int j = 0; j < n; j++){
                expr.addTerm(1.0, z[i][j]);
            }
            model.addConstr(expr, GRB.EQUAL, 1.0, "c" + constraintnummber);
            constraintnummber++;
        }

        expr = new GRBLinExpr();
        for (int j = 0; j < n; j++){
            expr = new GRBLinExpr();
            for (int i = 0; i < n; i++){
                expr.addTerm(1.0, z[i][j]);
            }
            model.addConstr(expr, GRB.EQUAL, 1.0, "c" + constraintnummber);
            constraintnummber++;
        }

        expr = new GRBLinExpr();
        int k;
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                expr.addTerm(x[i], z[i][j]);
            }
            if (j > 0){
                k = j - 1;
                expr.addConstant((-1) * y[k]);
            }
            model.addConstr(expr, GRB.LESS_EQUAL, beta, "c" + constraintnummber);
            constraintnummber++;
        }

        expr = new GRBLinExpr();
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                expr.addTerm(x[i], z[i][j]);
            }
            expr.addConstant((-1) * y[j]);
            model.addConstr(expr, GRB.GREATER_EQUAL, alpha, "c" + constraintnummber);
            constraintnummber++;
        }

        // Optimize model
        model.optimize();
        System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
        System.out.println(alpha.get(GRB.StringAttr.VarName) + " = " + alpha.get(GRB.DoubleAttr.X));
        System.out.println(beta.get(GRB.StringAttr.VarName) + " = " + beta.get(GRB.DoubleAttr.X));

        System.out.println("\nz["+ n + "][" + n + "] : ");
        for (int i = 0; i < n; i++){
            System.out.print("\n");
            for (int j = 0; j < n; j++){
                System.out.print(" " + z[i][j].get(GRB.DoubleAttr.X));
            }
        }
        //To Output
        for (int i = 0; i < n ; i++){
            for (int j = 0; j < n; j++){
                Output[i][j] = z[i][j].get(GRB.DoubleAttr.X);
            }
        }

        // Dispose of model and environment
        model.dispose();
        env.dispose();

        return Output;
    }
}
