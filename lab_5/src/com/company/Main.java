package com.company;

import com.company.exceptions.ReadDataException;

public class Main {

    public static void main(String[] args) {
        Solver solver =  new Solver();
        try {
            solver.run();
        } catch (ReadDataException e) {
            e.printStackTrace();
        }
    }
}
