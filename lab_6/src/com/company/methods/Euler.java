package com.company.methods;

import com.company.interfaces.Function;

public class Euler {

    double x1, x2, y0, h, eps, r;
    double[] y, x;
    int n, k, stN;
    Function f;

    public void init(Function f, double x1, double x2, double y0, double h, double eps) {
        this.f = f;
        this.x1 = x1;
        this.x2 = x2;
        this.y0 = y0;
        this.h = h;
        this.eps = eps;
        n = (int) ((x2 - x1) / h);
        stN = n;
        y = new double[n + 1];
        x = new double[n + 1];
        y[0] = y0;
        x[0] = x1;
        for (int i = 1; i <= stN; i++) {
            x[i] = x1 + i * h;
        }
    }

    public void run() {
        double[] y1, y2, xh1, xh2;
        k=1;
        do{
            y1 = new double[n + 1];
            xh1 = new double[n + 1];
            y1[0] = y0;
            xh1[0] = x1;

            for (int i = 1; i <= n; i++) {
                xh1[i] = x1 + i * h;
            }
            for (int i = 1; i <= n; i++) {
                y1[i] = y1[i - 1] + (h / 2) * (f.calculate(xh1[i - 1], y1[i - 1]) +
                        f.calculate(xh1[i], y1[i - 1] + h * f.calculate(xh1[i - 1], y1[i - 1])));
                if(i%k==0){
                    y[i/k]=y1[i];
                }
            }

            h=h/2;
            n = (int) ((x2 - x1) / h);
            y2 = new double[n + 1];
            xh2 = new double[n + 1];
            y2[0] = y0;
            xh2[0] = x1;

            for (int i = 1; i <= n; i++) {
                xh2[i] = x1 + i * h;
            }
            for (int i = 1; i <= n; i++) {
                y2[i] = y2[i - 1] + (h / 2) * (f.calculate(xh2[i - 1], y2[i - 1]) + f.calculate(xh2[i], y2[i - 1] + h * f.calculate(xh2[i - 1], y2[i - 1])));
            }
           k*=2;
        }while (Math.abs(y1[n/2]-y2[n])/(Math.pow(2,2))>eps);
    }

    public void show(){
        System.out.println("Таблица приближенных значений Эйлера");
        for (int i = 0; i <= stN; i++) {
            System.out.println(x[i]+" "+y[i]);
        }
        System.out.println();
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }
}
