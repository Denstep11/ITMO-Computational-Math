package com.company.methods;

import com.company.Result;
import com.company.interfaces.Function;

public class MethodNewton {
    private double a,b, eps;
    private Function function, fDerivative1, fDerivative2;
    private final Result result = new Result();

    public MethodNewton(){
    }

    public void init(double a, double b, Function function, double eps){
        this.a=a;
        this.b=b;
        this.function = function;
        this.eps = eps;
        fDerivative1 = x -> (function.calculate(x+0.00001)-function.calculate(x))/0.00001;
        fDerivative2 = x -> (fDerivative1.calculate(x+eps)-fDerivative1.calculate(x))/eps;
        if(function.calculate(a)*function.calculate(b)>=0){
            System.out.println("Условие сходимости не выполняется");
        }
        if(fDerivative1.calculate(a)*fDerivative1.calculate(b)<=0 || fDerivative2.calculate(a)*fDerivative2.calculate(b)<=0){
            System.out.println("Условие сходимости не выполняется");
        }
    }

    public void solve(){
        //Ньютон
        double it = 0;
        double x2;
        double x1;
        if(function.calculate(a)*fDerivative2.calculate(a)>0){
            x2 = a;
        }
        else x2=b;

        do {
            x1=x2;
            x2 = x1 - (function.calculate(x1)/fDerivative1.calculate(x1));
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
