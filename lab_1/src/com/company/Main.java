package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Solver solver = new Solver();
        solver.run();
    }
}

class Solver {

    private String filename;
    private double[][] matrix;
    private double[] d;
    private double[] x;
    private Scanner scanner;
    private double[] error;
    private File file;
    private double eps;
    private int n;

    Solver() {
    }

    public void run(){
        Scanner in = new Scanner(System.in);
        System.out.print("Введитие имя файла или 0 если считать с клавиатуры: ");
        filename = in.next();
        init();
        readMatrix();
        System.out.println('\n'+"Попытка достичь диагонального преобладания");
        diagonal();
        condition();
        transition();
        iterate();
    }

    public void init(){
        if(!Objects.equals(filename, "0")){
            file = new File(filename);
            System.out.println(filename);
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            scanner = new Scanner(System.in);
        }
    }

    public void readMatrix(){
        if(Objects.equals(filename, "0"))
            System.out.print("Введите погрешность: ");
        eps = scanner.nextDouble();
        if(Objects.equals(filename, "0"))
            System.out.print("Введите размер матрицы: ");
        n = scanner.nextInt();
        if(Objects.equals(filename, "0"))
            System.out.println("Введите матрицу: ");
        matrix = new double[n][n+1];
        d = new double[n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n+1;j++){
                matrix[i][j]= scanner.nextDouble();
            }

        System.out.print("Считана матрица:");
        for(int i=0;i<n;i++){
            System.out.println("");
            for(int j=0;j<n+1;j++){
                System.out.print(matrix[i][j]+" ");
            }
        }

    }

    public void transition(){
        double[][] newMatrix = new double[n][n+1];
        for(int i=0;i<n;i++)
            for(int j=0;j<n+1;j++){
                if(i==j){
                    newMatrix[i][j]=0;
                }
                else {
                    newMatrix[i][j]=-(matrix[i][j]/matrix[i][i]);
                }
                newMatrix[i][n]=(matrix[i][j]/matrix[i][i]);
            }

        matrix= newMatrix;
        for(int i=0;i<n;i++){
            for(int j=0;j<n+1;j++){
                System.out.print(newMatrix[i][j]+" ");
            }
            System.out.println("");
        }

        for(int i=0;i<n;i++){
            d[i]=matrix[i][n];
        }

    }

    public void condition(){
        for(int i=0;i<n;i++){
            double sum=0;
            for(int j=1;j<n;j++){
                sum+= Math.abs(matrix[i][j]);
            }
            sum=sum-matrix[i][i];
            if(matrix[i][i]<sum){
                System.out.println("Условие диагональных элементов не выполняется");
                return;
            }
        }
        System.out.println("Условие диагональных элементов выполняется");
    }

    public void diagonal(){

        double[] array;

        for(int j=0;j<n;j++){
            double maxValue = matrix[j][j];
            int maxId = j;
            for(int i=j;i<n;i++){
                if(matrix[i][j]>=maxValue){
                    maxId=i;
                    maxValue = matrix[i][j];
                }
            }

            array=matrix[maxId];
            matrix[maxId]=matrix[j];
            matrix[j]=array;
        }
    }

    public double max(double[] array1, double[] array2){
        double m;
        double[] array = new double[n];
        error = new double[n];

        for(int i=0;i<n;i++){
            array[i]=Math.abs(array1[i]-array2[i]);
            error[i] = array[i];
        }

        m=array[0];
        for(int i=1;i<n;i++){
            if(array[i]>m){
                m = array[i];
            }
        }
        return m;
    }

    private void iterate(){
        int iter = 1;
        double max;
        double[] x1 = new double[n];
        double[] x2 = new double[n];
        x = new double[n];

        for(int i=0;i<n;i++){
            double res =0;
            for(int j=0;j<n;j++) {
                res += matrix[i][j]*d[j];
            }
            res += matrix[i][n];
            x2[i] = res;
        }
        max = max(d,x2);

        while(max>eps){
            iter++;

            for(int i=0;i<n;i++){
                x1[i]=x2[i];
            }

            for(int i=0;i<n;i++){
                double res =0;
                for(int j=0;j<n;j++) {
                    res += matrix[i][j]*x1[j];
                }
                res += matrix[i][n];
                x2[i] = res;
            }
            max = max(x1,x2);

            for(int i=0;i<n;i++){
                x[i]=x2[i];
            }
        }

        System.out.print("Вектор решения: [");
        for(int i=0;i<n-1;i++){
            System.out.print(x[i]+", ");
        }
        System.out.print(x[n-1]+"]\n");

        System.out.print("Вектор погрешностии: [");
        for(int i=0;i<n-1;i++){
            System.out.print(error[i]+", ");
        }
        System.out.print(error[n-1]+"]\n");

        System.out.println("Количество итераций: "+ iter);
    }

}