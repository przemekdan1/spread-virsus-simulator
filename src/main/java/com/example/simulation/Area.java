package com.example.simulation;

public class Area {
    private double n;
    private double m;
    private long actualTime;

    public Area(double n, double m){
        this.n = n;
        this.m = m;
    }

    public double getN(){
        return n;
    }

    public double getM(){
        return this.m;
    }
    public void setActualTime(long actualTime){
        this.actualTime = actualTime;
    }
    public long getActualTime(){
        return this.actualTime;
    }
}
