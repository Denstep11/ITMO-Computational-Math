package com.company.methods;

import com.company.interfaces.Function;

public class MethodRectangleLeft {

    private double a,b, eps, h, result;
    private int n;
    private Function f;

    public void init(double a, double b, Function f, double eps){
        this.a = a;
        this.b = b;
        this.f = f;
        this.eps = eps;
        n=4;
    }

    private double integralSolution(int n){
        //прямоугольник левый
        h = (b - a) / n;
        double x = a;
        double result =0;
        for(int i=1; i<=n;i++){
            result = result + f.calculate(x);
            x = x + h;
        }
        return h * result;
    }

    public void solve(){
        double i0, i1;
        i1 = integralSolution(n);
        do {
            i0 = i1;
            n = n * 2;
            i1 = integralSolution(n);
        } while (Math.abs(i1 - i0)/(Math.pow(2,2)-1) > eps);
        result = i1;
    }

    @Override
    public String toString() {
        return  "Значение интеграла: " + result +
                "\nЧисло разбиений: " + n;
    }
}
