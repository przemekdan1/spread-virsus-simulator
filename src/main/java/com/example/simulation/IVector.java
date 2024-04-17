package com.example.simulation;

public interface IVector {
    public double abs(); //vector magnitude
    public double cdot(IVector param); //scalar product
    public double[] getComponents(); //returns vector components
}

