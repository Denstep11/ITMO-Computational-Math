package com.company;

public class Main {

    public static void main(String[] args)  {
        Solver solver = new Solver();
        try {
            solver.run();
        } catch (Exception e) {
            System.out.println("Ошибка ввода");
            System.out.println(e.getMessage());;
        }
    }
}
