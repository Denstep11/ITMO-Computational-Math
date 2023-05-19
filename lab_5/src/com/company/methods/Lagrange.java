package com.company.methods;

public class Lagrange {

    private double fx;
    private double[] x,y;

    public void init(double fx, double[] x, double[] y){
        this.fx = fx;
        this.x = x;
        this.y = y;
    }

    public double solve(){
        double ln=0;
        for(int i=0;i<y.length;i++){
            ln+=y[i]*(numerator(i)/denominator(i));
        }
        return ln;
    }

    private double numerator(int num){
        double s=1;
        double[] a = new double[y.length];
        for(int i=0;i<y.length;i++){
            a[i]=fx-x[i];
        }
        for(int i=0;i<y.length;i++){
            if(i!=num){
                s=s*a[i];
            }
        }
        return s;
    }

    private double denominator(int num){
        double s=1;
        double[] a = new double[y.length];
        for(int i=0;i<y.length;i++){
            a[i]=x[num]-x[i];
        }
        for(int i=0;i<y.length;i++){
            if(i!=num){
                s=s*a[i];
            }
        }
        return s;
    }
}
