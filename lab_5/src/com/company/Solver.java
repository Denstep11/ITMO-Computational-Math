package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Solver {

    private Scanner in, scannerIn;
    private String typeIn, fileName;
    private File fileIn;
    private double[] x, y;

    public Solver() {
        in = new Scanner(System.in);
    }

    public void run() {
        System.out.print("Выберете способ задания данных: ");
        System.out.println("a) в виде набора данных (таблицы x,y)");
        System.out.println("b) в виде сформированных в файле данных");
        System.out.println("c) Выбрать функцию из преложенных");
        typeIn = in.next();
        readData();
    }

    private void readData() {
        if (Objects.equals(typeIn, "a")) {
            System.out.print("Выберете количество значений: ");
            int n;
            n = Integer.parseInt(in.next());
            for (int i = 0; i < n; i++) {
                x[i] = Double.parseDouble(in.next());
                y[i] = Double.parseDouble(in.next());
            }
        } else if (Objects.equals(typeIn, "b")) {
            System.out.print("Введите путь к файлу: ");
            fileName = in.next();
            fileIn = new File(fileName);
            try {
                scannerIn = new Scanner(fileIn);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Файл ввода не найден!");
            }
            int n;
            n = Integer.parseInt(in.next());
            for (int i = 0; i < n; i++) {
                x[i] = Double.parseDouble(scannerIn.next());
                y[i] = Double.parseDouble(scannerIn.next());
            }
        } else if (Objects.equals(typeIn, "c")) {
            int n;
            System.out.print("Выберете фнукцию: ");
            System.out.print("1) sin(x)");
            System.out.print("2) cos(x)");
            n = Integer.parseInt(in.next());
            if(n!=2 && n!=1){

            }
        }
    }
}
