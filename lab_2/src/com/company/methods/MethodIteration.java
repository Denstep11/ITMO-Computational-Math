package com.company.methods;

import com.company.Result;
import com.company.interfaces.Function;

public class MethodIteration {

    private double a,b, eps;
    private Function function, fDerivative1, fExpressed;
    private final Result result = new Result();

    public MethodIteration(){
    }

    public void init(double a, double b, Function function, double eps){
        this.a=a;
        this.b=b;
        this.function = function;
        this.eps = eps;
        fDerivative1 = x -> (function.calculate(x+0.00001)-function.calculate(x))/0.00001;
        double l = -1/(Math.max(fDerivative1.calculate(a), fDerivative1.calculate(b)));
        fExpressed = x -> (x+l*function.calculate(x));
        if(Math.abs((fExpressed.calculate(a+0.00001)-fExpressed.calculate(a))/0.00001)>=1){
            System.out.println("Условие сходимости не выполняется");
        }
        if(Math.abs((fExpressed.calculate(b+0.00001)-fExpressed.calculate(b))/0.00001)>=1){
            System.out.println("Условие сходимости не выполняется");
        }
    }

    public void solve(){
        //простых итераций
        double it = 0;
        double x2 = a;
        double x1;
        do {
            x1=x2;
            x2 = fExpressed.calculate(x1);
            it++;
        } while (Math.abs(x2-x1)>eps);
        result.setX(x2);
        result.setFx(function.calculate(x2));
        result.setIt(it);
    }

    public Result getResult() {
        return result;
    }

}
