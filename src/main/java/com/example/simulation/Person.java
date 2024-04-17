package com.example.simulation;

import java.util.Random;
public class Person {
    private double xCoordinate;
    private double yCoordinate;
    private double velocity;
    private IVector direction;
    private State state;
    private boolean isInfected;
    private Random rand;
    private Area area;
    private Population population;
    private boolean markedForRemoval = false;
    private boolean meetingOccurred = false;
    private long meetingTime;
    private long timeSinceInfected;

    public void changeState(State newState) {
        if (!this.state.equals(newState)) {
                this.state = newState;
        }
    }

    public void setMeetingTime(long meetingTime){
        if(!meetingOccurred) {
            this.meetingTime = meetingTime;
            meetingOccurred = true;
        }
    }
    public long getMeetingTime(){
        return this.meetingTime;
    }

    public State getState() {
        return state;
    }

    public Area getArea(){
        return  this.area;
    }

    public Person(double xCoordinate, double yCoordinate, double velocity, IVector direction, boolean isInfected, Population population, Area area) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.velocity = velocity;
        this.direction = direction;
        this.isInfected = isInfected;
        this.rand = new Random();
        this.area = area;
        this.population = population;

        if (!isInfected){
            if (rand.nextDouble() < 0.5) {
                this.state = new Resistant(this);
            } else {
                this.state = new VulnerableHealthy(this);
            }
        }
        else{
            if (rand.nextDouble() < 0.5) {
                this.state = new VulnerableInfectedNoSymptoms(this);
            } else {
                this.state = new VulnerableInfectedWithSymptoms(this);
            }
        }
    }

    public void setVelocity(double velocity){
        this.velocity = velocity;
    }

    public void setDirection(IVector direction){
        this.direction = direction;
    }
    public void setXCoordinate(double xCoordinate){
        this.xCoordinate = xCoordinate;
    }
    public void setYCoordinate(double yCoordinate){
        this.yCoordinate = yCoordinate;
    }
    public void setTimeSinceInfected(long timeSinceInfected) {
        this.timeSinceInfected = timeSinceInfected;
    }
    public long getTimeSinceInfected() {
        return timeSinceInfected;
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
        return isInfected;
    }

    public void markForRemoval() {
        markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    public void move() {
        double[] components = direction.getComponents();
        double newX = xCoordinate + (velocity * components[0]);
        double newY = yCoordinate + (velocity * components[1]);

        if (newX >= 0 && newY >= 0 && newX < area.getN() && newY < area.getM()){
            this.xCoordinate = newX;
            this.yCoordinate = newY;
        }
        else{
            if (rand.nextDouble() < 0.1) {
                markForRemoval();
            }
            else{
                this.direction = new Vector2D(-components[0], - components[1]);
            }
        }
    }
}

