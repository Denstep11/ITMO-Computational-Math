package com.company;

public class ResultSystem {
    private double x1, x2;
    private int it;
    private double er1, er2;

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public int getIt() {
        return it;
    }

    public void setIt(int it) {
        this.it = it;
    }

    public double getEr1() {
        return er1;
    }

    public void setEr1(double er1) {
        this.er1 = er1;
    }

    public double getEr2() {
        return er2;
    }

    public void setEr2(double er2) {
        this.er2 = er2;
    }

    @Override
    public String toString() {
        return  "Вектор решения: [" + x1 + ","+x2+"]"+
                "\nВектор погрешностей: [" + er1 + ","+er2+"]"+
                "\nКоличество итераций = " + it;
    }
}
