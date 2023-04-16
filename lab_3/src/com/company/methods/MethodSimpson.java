package com.company.methods;

import com.company.interfaces.Function;

public class MethodSimpson {

    private double a, b, eps, h, result;
    private int n;
    private Function f;

    public void init(double a, double b, Function f, double eps) {
        this.a = a;
        this.b = b;
        this.f = f;
        this.eps = eps;
        n = 4;
    }

    private double integralSolution(int n) {
        h = (b - a) / n;
        double result = 0;
        for (int i = 1; i < n; i++) {
            if (i % 2 == 1) {
                result = result + 4 * f.calculate(a + h * i);
            } else {
                result = result + 2 * f.calculate(a + h * i);
            }
        }
        result = result + f.calculate(a) + f.calculate(a + h * n);
        result = (h * result) / 3;
        return result;
    }

    public void solve() {
        double i0, i1;
        i1 = integralSolution(n);
        do {
            i0 = i1;
            n = n * 2;
            i1 = integralSolution(n);
        } while (Math.abs(i1 - i0)/(Math.pow(2,4)-1) > eps);
        result = i1;
    }


    @Override
    public String toString() {
        return  "Значение интеграла: " + result +
                "\nЧисло разбиений: " + n;
    }
}
