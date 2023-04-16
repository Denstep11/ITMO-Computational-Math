package com.company;

import com.company.interfaces.Function;
import com.company.interfaces.BinFunction;
import com.company.methods.MethodChord;
import com.company.methods.MethodIteration;
import com.company.methods.MethodIterationFoSystem;
import com.company.methods.MethodNewton;
import exeptions.IntervalException;
import exeptions.ReadDataException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Solver {
    private String filenameIn, filenameOut;
    private Function[] functions;
    private String[] functionsString;
    private String[] methodNames;
    private FSystem[] functionSystems;
    private String method;
    private int equationNumber;
    private Result result;
    public ResultSystem resultSystem;
    private Scanner scannerIn, scannerOut;
    private double[] error;
    private File fileIn, fileOut;
    private double eps, a, b;
    Scanner in;

    public Solver(){
        in  = new Scanner(System.in);
    }

    public void run() throws IntervalException, ReadDataException {
        System.out.print("Введитие имя файла для чтения или 0 если считать с клавиатуры: ");
        filenameIn = in.next();
        System.out.print("Введитие имя файла для записи или 0 вывод в консоль: ");
        filenameOut = in.next();
        init();
        initFunction();
        initMethods();
        readData();
        if(equationNumber<=3){
            intervalCheck(a, b, equationNumber);
            runChosenMethod();
            ShowGraphicFunction();
            writeResult(result.toString());
        }
        else{
            solveTheSystem();
            ShowGraphicSystem();
            writeResult(resultSystem.toString());
        }
    }

    private void init() throws ReadDataException {
        if(!Objects.equals(filenameIn, "0")){
            fileIn = new File(filenameIn);
            System.out.println("Ввод:"+filenameIn);
            try {
                scannerIn = new Scanner(fileIn);
            } catch (FileNotFoundException e) {
               throw new ReadDataException();
            }
        }
        else {
            scannerIn = new Scanner(System.in);
            System.out.println("Ввод с клавиатуры");
        }

        if(!Objects.equals(filenameOut, "0")){
            fileOut = new File(filenameOut);
            System.out.println("Вывод:"+filenameOut);
        }
        else {
            scannerOut = new Scanner(System.in);
            System.out.println("Вывод в консоль");
        }
    }

    private void readData() throws ReadDataException {
        System.out.println("Выберите уравнение/систему для решения:");
        for(int i=0; i<functionsString.length; i++){
            System.out.println(i+") "+ functionsString[i]);
        }
        equationNumber = Integer.parseInt(in.next());
        if(equationNumber>5 || equationNumber<0) throw new ReadDataException();

        if(Objects.equals(filenameIn, "0")) {
            System.out.println("Введите погрешность:");
        }
        eps = Double.parseDouble(scannerIn.next());
        System.out.println("Погрешность: "+eps);

        if(Objects.equals(filenameIn, "0")) {
            System.out.println("Введите границы интервала/начальные приближения:");
        }
        a = Double.parseDouble(scannerIn.next());
        b = Double.parseDouble(scannerIn.next());
        System.out.println("Границы интервала/начальные приближения: "+a+" "+b);

    }

    private void intervalCheck(double a, double b, int equationNumber) throws IntervalException {
        double lastResult = functions[equationNumber].calculate(a);
        boolean flag = false;
        if(a>=b){
            throw new IntervalException();
        }
        for(double i = a+0.01; i<b; i+=0.01) {
            if (functions[equationNumber].calculate(i) * lastResult < 0) {
                if(flag){
                    throw new IntervalException();
                }
                flag = true;
            }
            lastResult = functions[equationNumber].calculate(i);
        }
        if(!flag) throw new IntervalException();
    }

    private void writeResult(String result){
        if(Objects.equals(filenameOut, "0")){
            System.out.println(result);
        }
        else {
            try(FileWriter writer = new FileWriter(filenameOut, false))
            {
                writer.write(result);
                writer.flush();
                System.out.println("Результат записан в файл");
            }
            catch(IOException ex){
                System.out.println("Ошибка записи");
            }
        }
    }

    private void initFunction(){
        Function f1 = x -> (3*Math.pow(x,3)+2.2*Math.pow(x,2)-16.5*x-8);
        Function f2 = x -> (Math.pow(x,3)-x+3);
        Function f3 = x -> (2.5*Math.cos(x)-0.5);
        Function f4 = x -> (-1.38*Math.pow(x,3)-5.42*Math.pow(x,2)+2.57*x+10.95);
        BinFunction fb1_1 = (x, y) -> (0.1*Math.pow(x,2)+x+0.2*Math.pow(y,2)-0.3);
        BinFunction fb1_2 = (x, y) -> (0.2*Math.pow(x,2)+y+0.1*x*y-0.7);
        BinFunction fb2_1 = (x, y) -> (0.1*Math.pow(x,2)+0.1*x-0.2*y-0.1);
        BinFunction fb2_2 = (x, y) -> (0.1*Math.pow(y,2)+0.1*y+0.2*x-0.2);

        FSystem fSystem1 = new FSystem();
        fSystem1.setF1(fb1_1);
        fSystem1.setF2(fb1_2);
        FSystem fSystem2 = new FSystem();
        fSystem2.setF1(fb2_1);
        fSystem2.setF2(fb2_2);


        String sf1 = "3x^3+2.2x^2-16.5x-8";
        String sf2 = "x^3-x+3";
        String sf3 = "2.5*cos(x)-0.5";
        String sf4 = "-1.38x^3-5.42x^2+2.57x+10.95";
        String sf5 = "0.1x1^2+x1+0.2x2^2-0.3\n" +
                "   0.2x1^2+x2+0.1x1*x2-0.7";
        String sf6 = "0.1x1^2+0.1x1-0.2x2-0.1\n" +
                "   0.1x2^2+0.1x2+0.2x1-0.2";
        functions = new Function[]{f1,f2,f3,f4};
        functionsString = new String[]{sf1, sf2, sf3, sf4, sf5, sf6};
        functionSystems = new FSystem[]{fSystem1, fSystem2};
    }

    private void initMethods(){
        String m1 = "Метод хорд (chord)";
        String m2 = "Метод простых итераций (iter)";
        String m3 = "Метод Ньютона (newton)";
        methodNames = new String[]{m1,m2,m3};
    }

    private void runChosenMethod() throws ReadDataException {
        System.out.println("Выберите способ для решения:");
        for(int i=0; i<methodNames.length; i++){
            System.out.println(methodNames[i]);
        }
        method = in.next();

        switch (method){
            case "chord":
                System.out.println("Метод хорд");
                MethodChord methodChord = new MethodChord();
                methodChord.init(a,b,functions[equationNumber], eps);
                methodChord.solve();
                result = methodChord.getResult();
                break;
            case "iter":
                System.out.println("Метод простых итераций");
                MethodIteration methodIteration = new MethodIteration();
                methodIteration.init(a,b, functions[equationNumber], eps);
                methodIteration.solve();
                result = methodIteration.getResult();
                break;
            case "newton":
                System.out.println("Метод Ньютона");
                MethodNewton methodNewton = new MethodNewton();
                methodNewton.init(a,b,functions[equationNumber], eps);
                methodNewton.solve();
                result = methodNewton.getResult();
                break;
            default:
                System.out.println("Не правльно введено название метода!");
                throw new ReadDataException();
        }
    }

    private void solveTheSystem(){
        System.out.println("Решение системы методом простых итераций");
        MethodIterationFoSystem methodIterationFoSystem = new MethodIterationFoSystem();
        methodIterationFoSystem.init(a,b,functionSystems[equationNumber-4], eps);
        methodIterationFoSystem.solve();
        resultSystem= methodIterationFoSystem.getResult();
    }

    private void ShowGraphicSystem(){
        GraphicForSystem graphic = new GraphicForSystem();
        graphic.init(a,b, functionSystems[equationNumber-4]);
        graphic.show();
    }

    private void ShowGraphicFunction(){
        Graphic graphic = new Graphic();
        graphic.init(a,b, functionsString[equationNumber], functions[equationNumber]);
        graphic.show();
    }

}
