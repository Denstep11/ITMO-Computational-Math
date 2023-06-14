package com.company.methods;

import com.company.interfaces.Function;

public class Milna {

    double x1, x2, y0, h, eps;
    double[] y, x;
    int n;
    Function f;

    public void init(Function f, double x1, double x2, double y0, double h, double eps) {
        this.f = f;
        this.x1 = x1;
        this.x2 = x2;
        this.y0 = y0;
        this.h = h;
        this.eps = eps;
        n = (int) ((x2 - x1) / h);
        y = new double[n + 1];
        x = new double[n + 1];
        y[0] = y0;
        x[0] = x1;
        for (int i = 1; i <= n; i++) {
            x[i] = x1 + i * h;
        }
    }

    public void run() {
        for (int i = 1; i <= 3; i++) {
            double k1, k2, k3, k4;
            k1 = h * f.calculate(x[i - 1], y[i - 1]);
            k2 = h * f.calculate(x[i-1]+h/2, y[i-1]+k1/2);
            k3 = h * f.calculate(x[i-1]+h/2, y[i-1]+k2/2);
            k4 = h * f.calculate(x[i-1]+h, y[i-1]+k3);
            y[i] = y[i-1]+(k1+2*k2+2*k3+k4)/6;
        }

        for (int i = 4; i <= n; i++) {
            double yp, yk;
            yp = y[i-4]+((4*h)/3)*(2*f.calculate(x[i-3], y[i-3])-f.calculate(x[i-2], y[i-2])+2*f.calculate(x[i-1], y[i-1]));
            yk = y[i-2]+(h/3)*(f.calculate(x[i-2], y[i-2])+4*f.calculate(x[i-1], y[i-1])+f.calculate(x[i],yp));
            while (Math.abs(yp-yk)>eps){
                yp=yk;
                yk = y[i-2]+(h/3)*(f.calculate(x[i-2], y[i-2])+4*f.calculate(x[i-1], y[i-1])+f.calculate(x[i],yp));
            }
            y[i]=yk;
        }

    }

    public void show() {
        System.out.println("Таблица приближенных значений Милна");
        for (int i = 0; i <= n; i++) {
            System.out.println(x[i] + " " + y[i]);
        }
    }

    public void epsilon(double[] yt){
        double max = -1;
        for (int i = 0; i <= n; i++) {
            if(Math.abs(yt[i]-y[i])>max){
                max = Math.abs(yt[i]-y[i]);
            }
        }
        System.out.println("Погрешность e = "+ max);
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }
}
