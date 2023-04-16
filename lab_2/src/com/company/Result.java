package com.company;

public class Result {
    private double x, fx, it;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getFx() {
        return fx;
    }

    public void setFx(double fx) {
        this.fx = fx;
    }

    public double getIt() {
        return it;
    }

    public void setIt(double it) {
        this.it = it;
    }

    @Override
    public String toString() {
        return  "Корень уравнения x = " + x +
                "\nf(x) = " + fx +
                "\nКоличество итераций = " + it;
    }
}
