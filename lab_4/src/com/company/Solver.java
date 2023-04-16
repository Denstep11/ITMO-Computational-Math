package com.company;

import com.company.approximation.*;
import com.company.exceptions.NumberOfPointException;
import com.company.exceptions.ReadDataException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Solver {
    private String filenameIn, filenameOut;
    private File fileIn, fileOut;
    private Scanner in, scannerIn, scannerOut;
    private double[][] points, pointsPlus;
    private double minx, miny;
    private Result[] results;

    public Solver() {
        in = new Scanner(System.in);
    }

    public void run() throws NumberOfPointException, ReadDataException {
        System.out.print("Введитие имя файла для чтения или 0 если считать с клавиатуры: ");
        filenameIn = in.next();
        System.out.print("Введитие имя файла для записи или 0 вывод в консоль: ");
        filenameOut = in.next();
        init();
        readData();
        System.out.println("\n");
        approximation();
        showResult();
    }

    private void init() {
        if (!Objects.equals(filenameIn, "0")) {
            fileIn = new File(filenameIn);
            System.out.println("Ввод:" + filenameIn);
            try {
                scannerIn = new Scanner(fileIn);
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка чтения");
            }
        } else {
            scannerIn = new Scanner(System.in);
            System.out.println("Ввод с клавиатуры");
        }

        if (!Objects.equals(filenameOut, "0")) {
            fileOut = new File(filenameOut);
            System.out.println("Вывод:" + filenameOut);
        } else {
            scannerOut = new Scanner(System.in);
            System.out.println("Вывод в консоль");
        }
        results = new Result[6];
    }

    private void readData() throws NumberOfPointException, ReadDataException {
        System.out.println("Введите количество точек:");
        int counter_points = Integer.parseInt(scannerIn.next());
        if(counter_points<8 || counter_points>12) {
            throw new NumberOfPointException();
        }
        points = new double[counter_points][2];
        pointsPlus = new double[counter_points][2];
        System.out.println("Введите точки:");
        for (int i = 0; i < counter_points; i++) {
            try {
                points[i][0] = Double.parseDouble(scannerIn.next());
                points[i][1] = Double.parseDouble(scannerIn.next());
            }
            catch (NumberFormatException e){
                throw new ReadDataException();
            }
        }
    }

    private void approximation() {
        Linear linear = new Linear();
        linear.init(points);
        linear.solve();
        results[0] = linear.getResult();

        Square square = new Square();
        square.init(points);
        square.solve();
        results[1] = square.getResult();

        Cube cube = new Cube();
        cube.init(points);
        cube.solve();
        results[2] = cube.getResult();

        Exponential exponential = new Exponential();
        exponential.init(points);
        exponential.solve();
        results[3] = exponential.getResult();

        Logarithm logarithm = new Logarithm();
        logarithm.init(points);
        logarithm.solve();
        results[4] = logarithm.getResult();

        Degree degree = new Degree();
        degree.init(points);
        degree.solve();
        results[5] = degree.getResult();
    }

    private void showResult() {
        Result minResult = results[0];

        if(Objects.equals(filenameOut, "0")){
            for (int i = 0; i < results.length; i++) {
                if (results[i].getEps() < minResult.getEps()) {
                    minResult = results[i];
                }
                System.out.println(results[i]);
                Graphic graphic = new Graphic();
                graphic.init(results[i].getNameFunction(), points, results[i].getFunction());
                graphic.show();
            }
        }
        else {
            try(FileWriter writer = new FileWriter(filenameOut, false))
            {
                for (int i = 0; i < results.length; i++) {
                    if (results[i].getEps() < minResult.getEps()) {
                        minResult = results[i];
                    }
                    writer.write(results[i].toString());
                    writer.flush();
                    Graphic graphic = new Graphic();
                    graphic.init(results[i].getNameFunction(), points, results[i].getFunction());
                    graphic.show();
                }
                writer.write("Наилучшая аппроксимация:\n");
                writer.write(minResult.toString());
                writer.flush();
                System.out.println("Результат записан в файл");
            }
            catch(IOException ex){
                System.out.println("Ошибка записи");
            }
        }

        System.out.println("Наилучшая аппроксимация:");
        System.out.println(minResult);
        Graphic graphic = new Graphic();
        graphic.init(minResult.getNameFunction(), points, minResult.getFunction());
        graphic.show();
    }
}
