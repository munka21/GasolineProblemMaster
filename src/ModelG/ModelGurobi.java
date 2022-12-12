package ModelG;
import gurobi.*;

import java.util.Arrays;

public class ModelGurobi {

    public static double[][] solveLP (int n, int x[], int y[]) throws GRBException {
        //Create Output
        double [][] output = new double[n][n];

        // Create empty environment
        GRBEnv environment = new GRBEnv(true);
        environment.start();

        // Create empty model:
        GRBModel model = new GRBModel(environment);

        // Create variables:
        GRBVar alpha = model.addVar(-GRB.INFINITY, 0.0, 0.0, GRB.CONTINUOUS , "alpha");
        GRBVar beta = model.addVar(0.0, GRB.INFINITY, 0.0, GRB.CONTINUOUS, "beta");
        GRBVar[][] z = new GRBVar[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                z[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.CONTINUOUS, "z" + i + j);
            }
        }

        // Set objective:
        GRBLinExpr objective = new GRBLinExpr();
        objective.addTerm(1, beta);
        objective.addTerm(-1, alpha);
        model.setObjective(objective, GRB.MINIMIZE);

        // Add constraints:
        //Constraint 1
        for (int i = 0; i < n; i++){
            GRBLinExpr cons1 = new GRBLinExpr();
            for (int j = 0; j < n; j++){
                cons1.addTerm(1.0, z[i][j]);
            }
            model.addConstr(cons1, GRB.EQUAL, 1.0, "c");
        }

        //Constraint 2
        for (int j = 0; j < n; j++){
            GRBLinExpr cons2 = new GRBLinExpr();
            for (int i = 0; i < n; i++){
                cons2.addTerm(1.0, z[i][j]);
            }
            model.addConstr(cons2, GRB.EQUAL, 1.0, "c" );
        }

        //Constraint 3
        for (int k = 0; k < n; k++){
            GRBLinExpr cons3 = new GRBLinExpr();
            for (int j = 0; j < k; j++){
                for (int i = 0; i < n; i++){
                    double xi = x[i];
                    cons3.addTerm(xi, z[i][j]);
                }
            }
            int sum = 0;
            for (int j = 0; j < k-1; j++){
                sum = sum + y[j];
            }
            double yi = (-1) * sum;
            cons3.addConstant(yi);
            model.addConstr(cons3, GRB.LESS_EQUAL, beta, "c");
        }

        //Constraint 4
        for (int k = 0; k < n; k++){
            GRBLinExpr cons4 = new GRBLinExpr();
            for (int j = 0; j < k; j++){
                for (int i = 0; i < n; i++){
                    double xi = x[i];
                    cons4.addTerm(xi, z[i][j]);
                }
            }
            int sum = 0;
            for (int j = 0; j < k; j++){
                sum = sum + y[j];
            }
            double yi = (-1) * sum;
            cons4.addConstant(yi);
            model.addConstr(cons4, GRB.GREATER_EQUAL, alpha, "c");
        }

        // Optimize model
        model.optimize();
        System.out.println("Obj: " + model.get(GRB.DoubleAttr.ObjVal));
        System.out.println(alpha.get(GRB.StringAttr.VarName) + " = " + alpha.get(GRB.DoubleAttr.X));
        System.out.println(beta.get(GRB.StringAttr.VarName) + " = " + beta.get(GRB.DoubleAttr.X));

        //To Output
        System.out.println("\nz["+ n + "][" + n + "] : ");
        for (int i = 0; i < n ; i++){
            for (int j = 0; j < n; j++){
                output[i][j] = z[i][j].get(GRB.DoubleAttr.X);
            }
            System.out.println(Arrays.toString(output[i]));
        }
        // Dispose of model and environment
        model.dispose();
        environment.dispose();
        return output;
    }

}