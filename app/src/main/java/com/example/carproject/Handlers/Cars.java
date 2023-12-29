package com.example.carproject.Handlers;

public class Cars implements Comparable<Cars>{

    public double Mpg;
    public double Displacement;
    public double HorsePower;
    public double Weight;
    public double Acceleration;
    public String Origin;
    public double Distance;

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

    @Override
    public String toString() {
        return "Cars [origin is : "+ Origin + "  DistanceFromSample is  :"+Distance+"]";
    }

    @Override
    public int compareTo(Cars o) {
        return (int)((this.Distance-o.Distance)*100);
    }
}
