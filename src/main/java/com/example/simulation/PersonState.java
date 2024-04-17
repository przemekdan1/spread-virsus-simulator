package com.example.simulation;

public class PersonState {
    private final double xCoordinate;
    private final double yCoordinate;
    private final double velocity;
    private final IVector direction;
    private final boolean infected;
    private final State state;
    private long timeSinceInfected;


    public PersonState(double xCoordinate, double yCoordinate, double velocity, IVector direction, boolean infected, State state) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.velocity = velocity;
        this.direction = direction;
        this.infected = infected;
        this.state = state;
    }

    public double getXCoordinate() {
        return xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    public double getVelocity() {
        return velocity;
    }

    public IVector getDirection() {
        return direction;
    }

    public boolean isInfected() {
        return infected;
    }

    public State getState() {
        return state;
    }

    public long getTimeSinceInfected(){
        return this.timeSinceInfected;
    }
    public void setTimeSinceInfected(long timeSinceInfected){
        this.timeSinceInfected = timeSinceInfected;
    }
}
