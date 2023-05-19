package com.company.methods;

import com.company.exceptions.ReadDataException;

public class Gauss {

    private double fx, t, h;
    private int a;
    private double[] y, x;
    double[][] defy;

    public void init(double fx, double h, double[][] defy, double[] x, double[] y) {
        this.fx = fx;
        this.h = h;
        this.defy = defy;
        this.x = x;
        this.y = y;
        a = y.length / 2;
        t = (fx - x[a]) / h;
    }

    public double solve() throws ReadDataException {
        double pn = 0;
        if (fx > a) {
            pn = more();
        } else if (fx < a) {
            pn = less();
        } else {
            throw new ReadDataException();
        }
        return pn;
    }

    private double more() {
        double pn, tn, nt=t;
        int n;
        double t1 = t;
        pn = defy[a][0] + t * defy[a][1] + ((t * (t - 1)) / 2) * defy[a - 1][2];
        for (int i = 3; i < y.length; i++) {
            if (i % 2 == 1) {
                n = (i + 1) / 2;
                tn = (t + n - 1) * t1 * (t - n + 1);
                pn += (tn / factorial(i)) * defy[a - n-1][i];
                nt = tn;
            } else {
                n = i / 2;
                tn = (t + n - 1) * t1 * (t - n);
                pn += (tn / factorial(i)) * defy[a - n][i];
                t1 = nt;
            }
        }
        return pn;
    }

    private double less() {
        double pn, tn, nt=t;
        int n;
        double t1 = t;
        pn = defy[a][0] + t * defy[a - 1][1] + ((t * (t + 1)) / 2) * defy[a - 1][2];
        for (int i = 3; i < y.length; i++) {
            if (i % 2 == 1) {
                n = (i + 1) / 2;
                tn = (t + n - 1) * t1 * (t - n + 1);
                nt = tn;
            } else {
                n = i / 2;
                tn = (t + n) * (t + n - 1) * t1 * (t - n + 1);
                t1 = nt;
            }
            double fact = factorial(i);
            pn += (tn / fact) * defy[a - n][i];
        }
        return pn;
    }

    private double factorial(int n) {
        double s = 1;
        for (int i = 1; i <=n; i++) {
            s = s*i;
        }
        return s;
    }
}
