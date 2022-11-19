package ModelG;
import gurobi.*;

import java.util.Arrays;

public class ModelGurobi {

    public static double[][] solveLP (int n, int x[], int y[]) throws GRBException {

        int constraintnummber = 0;
        double [][] Output = new double[n][n];

        // Create empty environment, set options, and start:
        GRBEnv env = new GRBEnv(true);
        env.set("logFile", "master.log");
        env.start();

        // Create empty model:
        GRBModel model = new GRBModel(env);

        // Create variables:
        //GRBVar alpha = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS , "alpha");
        GRBVar alpha = model.addVar(-GRB.INFINITY, 0.0, 0.0, GRB.CONTINUOUS , "alpha");
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

        //To Output
        //System.out.println("\nz["+ n + "][" + n + "]********* : ");
        for (int i = 0; i < n ; i++){
            for (int j = 0; j < n; j++){
                Output[i][j] = z[i][j].get(GRB.DoubleAttr.X);
            }
            //System.out.println(Arrays.toString(Output[i]));
        }

        // Dispose of model and environment
        model.dispose();
        env.dispose();

        return Output;
    }


}
