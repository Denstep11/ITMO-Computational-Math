package com.company;

import com.company.interfaces.Function;
import com.company.methods.*;

import java.util.Objects;
import java.util.Scanner;

public class Solver {

    private Scanner in;
    private Function[] functions;
    private String[] functionsString, methodNames;
    private int functionNumber;
    private double a,b,eps;
    private String result;

     public Solver(){
        in = new Scanner(System.in);
     }

    public void run(){
         initFunction();
         initMethods();
         readData();
         chosenMethod();
         System.out.println(result);
    }

    private void readData(){
        System.out.println("Выберите функцию");
        for(int i=0; i<functionsString.length; i++){
            System.out.println(i+") "+ functionsString[i]);
        }
        functionNumber = Integer.parseInt(in.next());

        System.out.println("Введите границы интервала:");
        a = Double.parseDouble(in.next());
        b = Double.parseDouble(in.next());

        System.out.println("Введите погрешность:");
        eps = Double.parseDouble(in.next());


    }

    private void chosenMethod(){
        System.out.println("Выберите способ для решения:");
        for(int i=0; i<methodNames.length; i++){
            System.out.println(methodNames[i]);
        }
        String method = in.next();

        switch (method){
            case "recLeft":
                System.out.println("Метод левых прямоугольников");
                MethodRectangleLeft methodRectangleLeft = new MethodRectangleLeft();
                methodRectangleLeft.init(a,b,functions[functionNumber], eps);
                methodRectangleLeft.solve();
                result = methodRectangleLeft.toString();
                break;
            case "recRight":
                System.out.println("Метод правых прямоугольников");
                MethodRectangleRight methodRectangleRight = new MethodRectangleRight();
                methodRectangleRight.init(a,b,functions[functionNumber], eps);
                methodRectangleRight.solve();
                result = methodRectangleRight.toString();
                break;
            case "recCenter":
                System.out.println("Метод средних прямоугольников");
                MethodRectangleCenter methodRectangleCenter = new MethodRectangleCenter();
                methodRectangleCenter.init(a,b,functions[functionNumber], eps);
                methodRectangleCenter.solve();
                result = methodRectangleCenter.toString();
                break;
            case "trap":
                System.out.println("Метод трапеций");
                MethodTrapeze methodTrapeze = new MethodTrapeze();
                methodTrapeze.init(a,b,functions[functionNumber], eps);
                methodTrapeze.solve();
                result = methodTrapeze.toString();
                break;
            case "simpson":
                System.out.println("Метод Симпсона");
                MethodSimpson methodSimpson = new MethodSimpson();
                methodSimpson.init(a,b,functions[functionNumber], eps);
                methodSimpson.solve();
                result = methodSimpson.toString();
                break;
            default:
                result = "Не правльно введено название метода!";
        }
    }

    private void initMethods(){
        String m1 = "Метод левых прямоугольников(recLeft)";
        String m2 = "Метод правых прямоугольников (recRight)";
        String m3 = "Метод средних прямоугольников (recCenter)";
        String m4 = "Метод трапеций (trap)";
        String m5 = "Метод Симпсона (simpson)";
        methodNames = new String[]{m1,m2,m3,m4,m5};
    }

    private void initFunction(){
        Function f1 = x -> (-3*Math.pow(x,3)-5*Math.pow(x,2)+4*x-2);
        Function f2 = x -> (2*Math.sin(x)+1);
        Function f3 = x -> (Math.pow(x,2)-5*x);
        Function f4 = x -> (2*Math.sin(x)+6*Math.cos(x)+x);

        String sf1 = "-3x^3-5x^2+4x-2";
        String sf2 = "2sin(x)+1";
        String sf3 = "x^2-5x";
        String sf4 = "2*sin(x)+6*cos(x)+x";

        functions = new Function[]{f1,f2,f3,f4};
        functionsString = new String[]{sf1, sf2, sf3, sf4};
    }

}
