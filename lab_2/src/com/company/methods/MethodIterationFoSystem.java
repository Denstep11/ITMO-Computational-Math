package com.company.methods;

import com.company.FSystem;
import com.company.Result;
import com.company.ResultSystem;
import com.company.interfaces.BinFunction;
import com.company.interfaces.Function;

public class MethodIterationFoSystem {

    private double x1,x2, eps;
    private FSystem system;
    private BinFunction f1, f2, f1Derivative1, f1Derivative2, f2Derivative1, f2Derivative2;
    private final ResultSystem result = new ResultSystem();

    public MethodIterationFoSystem(){
    }

    public void init(double x1, double x2, FSystem system, double eps){
        this.x1 = x1;
        this.x2 = x2;
        this.system = system;
        this.eps = eps;
        f1 = (x, y) -> (-system.getF1().calculate(x,y)+x);
        f2 = (x, y) -> (-system.getF2().calculate(x,y)+y);
        f1Derivative1 = (x, y) -> (f1.calculate(x+0.00001, y)-f1.calculate(x, y))/0.00001;
        f1Derivative2 = (x, y) -> (f1.calculate(x, y+0.00001)-f1.calculate(x, y))/0.00001;
        f2Derivative1 = (x, y) -> (f2.calculate(x+0.00001, y)-f2.calculate(x, y))/0.00001;
        f2Derivative2 = (x, y) -> (f2.calculate(x, y+0.00001)-f2.calculate(x, y))/0.00001;
        if(Math.max(Math.abs(f1Derivative1.calculate(x1,x2))+Math.abs(f1Derivative2.calculate(x1,x2)),
                Math.abs(f2Derivative1.calculate(x1,x2))+Math.abs(f2Derivative2.calculate(x1,x2)))<1){
            System.out.println("Процесс сходящийся");
        }
        else {
            System.out.println("Условие сходимости не выполняется");
        }
    }


    public void solve(){
        //протсых итераций система
        int it=0;
        double x1f, x1l, x2f, x2l;
        x1l=x1;
        x2l=x2;
        do {
            x1f = x1l;
            x2f = x2l;
            x1l = f1.calculate(x1f, x2f);
            x2l = f2.calculate(x1f, x2f);
            it++;
        } while (Math.max(Math.abs(x1l-x1f), Math.abs(x2l-x2f))>eps);
        result.setIt(it);
        result.setX1(x1l);
        result.setX2(x2l);
        result.setEr1(Math.abs(x1l-x1f));
        result.setEr2(Math.abs(x2l-x2f));
    }

    public ResultSystem getResult() {
        return result;
    }

}
