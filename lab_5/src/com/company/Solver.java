package com.company;

import com.company.exceptions.ReadDataException;
import com.company.interfaces.Function;
import com.company.methods.Gauss;
import com.company.methods.Lagrange;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Solver {

    private Scanner in, scannerIn;
    private String typeIn, fileName;
    private File fileIn;
    private double[][] defy;
    private double[] x, y;
    private double fx, ln, pn, h;
    private Function[] functions;

    public Solver() {
        in = new Scanner(System.in);
        functions = new Function[2];
        functions[0] = x -> (2*Math.sin(x)+1);
        functions[1]= x -> (Math.cos(x)-2);
    }

    public void run() throws ReadDataException {
        System.out.println("Выберете способ задания данных: ");
        System.out.println("a) в виде набора данных (таблицы x,y)");
        System.out.println("b) в виде сформированных в файле данных");
        System.out.println("c) Выбрать функцию из преложенных");
        typeIn = in.next();
        readData();
        differenceTable();
        printDefTable();
        interpolation();
    }

    private void readData() throws ReadDataException {
        if (Objects.equals(typeIn, "a")) {
            System.out.print("Введите количество значений: ");
            int n;
            n = Integer.parseInt(in.next());
            x =  new double[n];
            y =  new double[n];
            System.out.println("Введите значения: ");
            for (int i = 0; i < n; i++) {
                x[i] = Double.parseDouble(in.next());
                y[i] = Double.parseDouble(in.next());
            }
            h=x[1]-x[0];
        } else if (Objects.equals(typeIn, "b")) {
            System.out.println("Введите путь к файлу: ");
            fileName = in.next();
            fileIn = new File(fileName);
            try {
                scannerIn = new Scanner(fileIn);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Файл ввода не найден!");
                throw new ReadDataException();
            }
            int n;
            n = Integer.parseInt(scannerIn.next());
            x =  new double[n];
            y =  new double[n];
            for (int i = 0; i < n; i++) {
                x[i] = Double.parseDouble(scannerIn.next());
                y[i] = Double.parseDouble(scannerIn.next());
            }
            h=x[1]-x[0];
        } else if (Objects.equals(typeIn, "c")) {
            int f, n;
            double x1, x2;
            System.out.print("Выберете фнукцию: ");
            System.out.print("1) 2*sin(x)+1");
            System.out.print("2) cos(x)-2");
            f = Integer.parseInt(in.next())-1;
            if(f!=0 && f!=1){
                throw new ReadDataException();
            }
            System.out.print("Введите исследуемый итервал");
            x1 = Integer.parseInt(in.next());
            x2 = Integer.parseInt(in.next());
            if(x2<=x1){
                throw new ReadDataException();
            }
            System.out.print("Введите количество точек на интервале");
            n = Integer.parseInt(in.next());
            h = (x2-x1)/n;
            x =  new double[n];
            y =  new double[n];
            for(int i=0;i<=n;i++){
                x[i] = x1+i*h;
                y[i] = functions[f].calculate(x1+i*h);
            }
        }
        else {
            throw new ReadDataException();
        }
        if(h<=0){
            throw new ReadDataException();
        }
        System.out.println("Введите значение аргумента функции");
        fx = Double.parseDouble(in.next());
    }

    private void differenceTable(){
        defy = new double[y.length][y.length];
        for(int i=0;i<y.length;i++){
            defy[i][0]=y[i];
        }
        for(int i=1;i<y.length;i++){
            for(int j=0;j< y.length-i;j++){
                defy[j][i]=defy[j+1][i-1]-defy[j][i-1];
            }
        }
    }

    private void printDefTable(){
        System.out.println("Таблица конечных разностей");
        for(int i=0;i<y.length;i++){
            for(int j=0;j< y.length;j++){
                System.out.print(defy[i][j]+" ");
            }
            System.out.println();
        }
    }

    private void interpolation() throws ReadDataException {
        Lagrange lagrange = new Lagrange();
        lagrange.init(fx, x, y);
        ln = lagrange.solve();
        System.out.println("Приближенное значение функции методом Лагранжа f("+fx+") = "+ln);

        Gauss gauss = new Gauss();
        gauss.init(fx, h, defy, x, y);
        pn = gauss.solve();
        System.out.println("Приближенное значение функции методом Гаусса f("+fx+") = "+pn);
    }
}
