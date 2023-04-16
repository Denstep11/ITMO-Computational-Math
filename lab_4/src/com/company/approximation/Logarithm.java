package com.company.approximation;

import com.company.Result;
import com.company.interfaces.Function;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;

public class Logarithm {
    private double eps = 0;
    private double[] x, y, f, e;
    private double[][] functionTable;
    private double x_sum = 0, x2_sum = 0, y_sum = 0, xy_sum = 0;
    private double minx, miny;
    private Result result;

    public void init(double[][] functionTable) {
        this.functionTable = functionTable;
        for (int i = 0; i < functionTable.length; i++) {
            x_sum += Math.log(functionTable[i][0]);
            x2_sum += Math.pow(Math.log(functionTable[i][0]), 2);
            y_sum += functionTable[i][1];
            xy_sum += Math.log(functionTable[i][0]) * functionTable[i][1];
        }
        x = new double[functionTable.length];
        y = new double[functionTable.length];
        f = new double[functionTable.length];
        e = new double[functionTable.length];
    }

    public void solve() {
        double[][] matrix = new double[][]{
                {x2_sum, x_sum},
                {x_sum, functionTable.length},
        };

        double[] constants = new double[]{xy_sum, y_sum};
        DecompositionSolver solver = new LUDecomposition(new Array2DRowRealMatrix(matrix)).getSolver();
        double[] res = solver.solve(new ArrayRealVector(constants)).toArray();

        Function function = x -> (res[0] * Math.log(x) + res[1]);

        result = new Result();
        result.setNameFunction("Логарифмическая");
        result.setStrFunction(res[0] + "ln(x) + " + res[1]);

        for (int i = 0; i < functionTable.length; i++) {
            x[i] = functionTable[i][0] - minx;
            y[i] = functionTable[i][1] - miny;
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
