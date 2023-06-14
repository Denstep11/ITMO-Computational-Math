package com.company;

import com.company.interfaces.Function;
import com.company.methods.Euler;
import com.company.methods.Milna;
import com.company.methods.Runge;

import java.util.Scanner;

public class Solver {

    Scanner in;
    Function[] functions, fy, fc;
    String[] functionsString;
    double[] c, x, y, y1, y2, y3;
    double x1, x2, y0, h, eps;
    int equationNumber;

    public Solver() {
        in = new Scanner(System.in);
        c = new double[3];
    }

    public void run() {
        initFunction();
        readData();
        solution();
        show();
    }

    private void readData() {
        System.out.println("Выберите дифференциальное уравнение:");
        for (int i = 0; i < functions.length; i++) {
            System.out.println(i + ") " + functionsString[i]);
        }
        equationNumber = Integer.parseInt(in.next());
        System.out.println("Введите интервал дифференцирования:");
        x1 = Double.parseDouble(in.next());
        x2 = Double.parseDouble(in.next());
        System.out.print("Введите y(" + x1 + "): ");
        y0 = Double.parseDouble(in.next());
        System.out.print("Введите шаг: ");
        h = Double.parseDouble(in.next());
        System.out.print("Введите точность E: ");
        eps = Double.parseDouble(in.next());
        c[equationNumber] = fc[equationNumber].calculate(x1, y0);
    }

    private void solution() {
        Euler euler = new Euler();
        Runge runge = new Runge();
        Milna milna = new Milna();

        euler.init(functions[equationNumber], x1, x2, y0, h, eps);
        runge.init(functions[equationNumber], x1, x2, y0, h, eps);
        milna.init(functions[equationNumber], x1, x2, y0, h, eps);

        euler.run();
        runge.run();
        milna.run();

        euler.show();
        runge.show();
        milna.show();

        x = euler.getX();
        y1 = euler.getY();
        y2 = runge.getY();
        y3 = milna.getY();

        y = new double[x.length];
        for(int i=0;i<x.length;i++){
            y[i] = fy[equationNumber].calculate(x[i], 1);
        }
        milna.epsilon(y);
    }

    private void show(){
        Graphic graphic = new Graphic();
        graphic.init(x, y, y1, y2, y3);
        graphic.show();
    }

    private void initFunction() {
        Function f1 = (x, y) -> (y + (1 + x) * Math.pow(y, 2));
        Function f2 = (x, y) -> (Math.pow(x, 2) + y);
        Function f3 = (x, y) -> (Math.sin(x) * y);

        Function fy1 = (x, y) -> (-Math.exp(x) / (x * Math.exp(x) + c[0]));
        Function fy2 = (x, y) -> (c[1] * Math.exp(x) - Math.pow(x, 2) - 2 * x - 2);
        Function fy3 = (x, y) -> (c[2]/Math.exp(Math.cos(x)));

        Function c1 = (x, y) -> (-Math.exp(x) / y - x * Math.exp(x));
        Function c2 = (x, y) -> ((-y - Math.pow(x, 2) - 2 * x - 2) / (-Math.exp(x)));
        Function c3 = (x, y) -> (y * Math.exp(Math.cos(x)));

        String sf1 = "y'=y+(1+x)*y^2";
        String sf2 = "y'=x^2+y";
        String sf3 = "y'=sin(x)*y";

        functions = new Function[]{f1, f2, f3};
        functionsString = new String[]{sf1, sf2, sf3};
        fy = new Function[]{fy1, fy2, fy3};
        fc = new Function[]{c1, c2, c3};
    }
}
