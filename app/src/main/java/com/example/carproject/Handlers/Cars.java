package com.example.carproject.Handlers;

public class Cars implements Comparable<Cars>{

    //For KNN Algorithm Usage
    public double Mpg;
    public double Displacement;
    public double HorsePower;
    public double Weight;
    public double Acceleration;
    public String Origin;
    public double Distance;

    //For The Rest Of Algorithms
    public String Mpg2;
    public String Displacement2;
    public String HorsePower2;
    public String Weight2;
    public String Acceleration2;
    public String Origin2;

    public Cars(double mpg, double displacement, double horsePower, double weight, double acceleration) {
        Mpg = mpg;
        Displacement = displacement;
        HorsePower = horsePower;
        Weight = weight;
        Acceleration = acceleration;
    }

    public Cars(double mpg, double displacement, double horsePower, double weight, double acceleration, String origin) {
        Mpg = mpg;
        Displacement = displacement;
        HorsePower = horsePower;
        Weight = weight;
        Acceleration = acceleration;
        Origin = origin;
    }

    public Cars(String mpg2, String displacement2, String horsePower2, String weight2, String acceleration2, String origin2) {
        Mpg2 = mpg2;
        Displacement2 = displacement2;
        HorsePower2 = horsePower2;
        Weight2 = weight2;
        Acceleration2 = acceleration2;
        Origin2 = origin2;
    }

    public Cars(String mpg2, String displacement2, String horsePower2, String weight2, String acceleration2) {
        Mpg2 = mpg2;
        Displacement2 = displacement2;
        HorsePower2 = horsePower2;
        Weight2 = weight2;
        Acceleration2 = acceleration2;
    }

    @Override
    public String toString() {
        return "Cars [origin is : "+ Origin + "  DistanceFromSample is  :"+Distance+"]";
    }

    @Override
    public int compareTo(Cars o) {
        return (int)((this.Distance-o.Distance)*100);
    }
}
