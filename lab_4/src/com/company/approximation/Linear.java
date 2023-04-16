package com.company.approximation;

import com.company.Result;
import com.company.interfaces.Function;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;

public class Linear {
    private double eps = 0;
    private double[] x, y, f, e;
    private double[][] functionTable;
    private double x_sum = 0, x2_sum = 0, y_sum = 0, xy_sum = 0;
    private Result result;

    public void init(double[][] functionTable){
        this.functionTable = functionTable;
        for (int i = 0; i < functionTable.length; i++) {
            x_sum += functionTable[i][0];
            x2_sum += Math.pow(functionTable[i][0], 2);
            y_sum += functionTable[i][1];
            xy_sum += functionTable[i][0] * functionTable[i][1];
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

        Function function = x -> (res[0]*x+res[1]);

        result = new Result();
        result.setNameFunction("Линейная");
        result.setStrFunction(res[0]+"x + "+ res[1]);

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
        result.setR(coefficient());
        result.setFunction(function);
    }

    private double coefficient(){
        double r;
        double xc=0, yc=0;
        double nm=0, dn1=0, dn2=0, dn=0;
        for(int i=0;i< functionTable.length;i++){
            xc+=x[i];
            yc+=y[i];
        }
        xc=xc/functionTable.length;
        yc=yc/functionTable.length;

        for(int i=0;i< functionTable.length;i++){
            nm +=(x[i]-xc)*(y[i]-yc);
            dn1 += Math.pow(x[i]-xc,2);
            dn2 += Math.pow(y[i]-yc,2);
        }
        dn=dn1*dn2;
        dn=Math.sqrt(dn);
        r=nm/dn;
        return r;
    }

    public Result getResult() {
        return result;
    }

}
