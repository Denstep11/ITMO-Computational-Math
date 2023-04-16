package com.company.methods;

import com.company.Result;
import com.company.interfaces.Function;

public class MethodChord {

    private double a,b, eps, x;
    private Function function,  fDerivative1, fDerivative2;
    private final Result result = new Result();

    public MethodChord(){
    }

    public void init(double a, double b, Function function, double eps){
        this.a=a;
        this.b=b;
        this.function = function;
        this.eps = eps;
    }

    public void solve(){
        //хорд
        double it = 0;
        double x1 =a;
        double x2 =b;
        while(Math.abs(x1-x2)>eps){
            x1=x2;
            x = (a*function.calculate(b)-b*function.calculate(a))/(function.calculate(b)-function.calculate(a));
            if(function.calculate(a)*function.calculate(x)>0){
                a=x;
            }
            else if(function.calculate(b)*function.calculate(x)>0){
                b=x;
            }
            it++;
            x2=x;
        }
        result.setX(x);
        result.setFx(function.calculate(x));
        result.setIt(it);
    }

    public Result getResult() {
        return result;
    }
}
