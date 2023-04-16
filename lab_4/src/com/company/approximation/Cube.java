package com.company.approximation;

import com.company.Result;
import com.company.interfaces.Function;
import org.apache.commons.math3.linear.*;

public class Cube {
    private double eps = 0;
    private double[] x, y, f, e;
    private double[][] functionTable;
    private double x_sum = 0, x2_sum = 0, x3_sum = 0, x4_sum = 0, x5_sum = 0, x6_sum = 0, y_sum = 0, xy_sum = 0, x2y_sum = 0, x3y_sum = 0;
    private Result result;

    public void init(double[][] functionTable){
        this.functionTable = functionTable;
        for (int i = 0; i < functionTable.length; i++) {
            x_sum += functionTable[i][0];
            x2_sum += Math.pow(functionTable[i][0], 2);
            x3_sum += Math.pow(functionTable[i][0], 3);
            x4_sum += Math.pow(functionTable[i][0], 4);
            x5_sum += Math.pow(functionTable[i][0], 5);
            x6_sum += Math.pow(functionTable[i][0], 6);
            y_sum += functionTable[i][1];
            xy_sum += functionTable[i][0] * functionTable[i][1];
            x2y_sum += Math.pow(functionTable[i][0], 2) * functionTable[i][1];
            x3y_sum += Math.pow(functionTable[i][0], 3) * functionTable[i][1];
        }
        x = new double[functionTable.length];
        y = new double[functionTable.length];
        f = new double[functionTable.length];
        e = new double[functionTable.length];
    }

    public void solve(){
        double[][] matrix = new double[][]{
                {functionTable.length, x_sum, x2_sum, x3_sum},
                {x_sum, x2_sum, x3_sum, x4_sum},
                {x2_sum, x3_sum, x4_sum, x5_sum},
                {x3_sum, x4_sum, x5_sum, x6_sum},
        };

        double[] constants = new double[]{y_sum, xy_sum, x2y_sum, x3y_sum};
        DecompositionSolver solver = new LUDecomposition(new Array2DRowRealMatrix(matrix)).getSolver();
        double[] res = solver.solve(new ArrayRealVector(constants)).toArray();

        Function function = x -> (res[3] * Math.pow(x, 3) + res[2] * Math.pow(x, 2) + res[1]*x + res[0]);

        result = new Result();
        result.setNameFunction("Полиномиальная 3-й степени");
        result.setStrFunction(res[3]+"x^3 + " + res[2] + "x^2 + " + res[1] + "x + " + res[0]);

        for (int i = 0; i < functionTable.length; i++) {
            x[i] = functionTable[i][0];
            y[i] = functionTable[i][1];
            f[i] = function.calculate(x[i]);
            e[i] = f[i] - y[i];
            eps += Math.pow(e[i], 2);
        }
        eps = eps / functionTable.length;
        eps = Math.sqrt(eps);
        result.setX(x);
        result.setY(y);
        result.setF(f);
        result.setE(e);
        result.setEps(eps);
        result.setFunction(function);
    }

    public Result getResult() {
        return result;
    }

}
