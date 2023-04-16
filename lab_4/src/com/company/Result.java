package com.company;

import com.company.interfaces.Function;

import java.util.Arrays;
import java.util.Objects;

public class Result {
    private String nameFunction;
    private String strFunction;
    private Function function;
    private double r;
    private double eps;
    private double[] x,y,f,e;

    public void setNameFunction(String nameFunction) {
        this.nameFunction = nameFunction;
    }


    public void setStrFunction(String strFunction) {
        this.strFunction = strFunction;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public void setE(double[] e) {
        this.e = e;
    }

    public void setF(double[] f) {
        this.f = f;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getStrFunction() {
        return strFunction;
    }

    public String getNameFunction() {
        return nameFunction;
    }


    public double getEps() {
        return eps;
    }

    public double[] getE() {
        return e;
    }

    public double[] getF() {
        return f;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public Function getFunction() {
        return function;
    }

    @Override
    public String toString() {
        if(Objects.equals(nameFunction, "Линейная")){
            return
                    "Функция : " + nameFunction + "\n" +
                            "Коэфицент Пирсона: " + r + "\n" +
                            "F(x) = " + strFunction + "\n" +
                            "Среднеквадратичное отклонение: " + eps + "\n" +
                            "x = " + Arrays.toString(x) + "\n" +
                            "y = " + Arrays.toString(y) + "\n" +
                            "f = " + Arrays.toString(f) + "\n" +
                            "e = " + Arrays.toString(e)+"\n";
        }
        else {
            return
                    "Функция : " + nameFunction + "\n" +
                            "F(x) = " + strFunction + "\n" +
                            "Среднеквадратичное отклонение: " + eps + "\n" +
                            "x = " + Arrays.toString(x) + "\n" +
                            "y = " + Arrays.toString(y) + "\n" +
                            "f = " + Arrays.toString(f) + "\n" +
                            "e = " + Arrays.toString(e)+"\n";
        }
    }
}
