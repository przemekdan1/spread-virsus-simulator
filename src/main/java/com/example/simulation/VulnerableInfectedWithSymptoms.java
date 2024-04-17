package com.example.simulation;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class VulnerableInfectedWithSymptoms extends State{
    private double infectingChance = 1;
    private int distanceToInfect = 2 * frames;
    private long minTimeToInfect = 3_000_000_000L;
    private long timeSinceClose;
    private long infectionDuration;



    VulnerableInfectedWithSymptoms(Person person) {
        super(person);
        person.setTimeSinceInfected(person.getArea().getActualTime());
        this.infectionDuration =  (long)(20 + (int) (Math.random() * 11)) * 1_000_000_000L;
        this.timeSinceClose = 0;
    }

    @Override
    public void infect(List<Person> people) {
        for (Person otherPerson : people) {
            if (otherPerson.getState() instanceof VulnerableHealthy && !(otherPerson.equals(this.person))){
                double distance = calculateDistance(otherPerson);
                if (distance <= distanceToInfect){
                    otherPerson.setMeetingTime(otherPerson.getArea().getActualTime());
                    timeSinceClose = otherPerson.getArea().getActualTime() - otherPerson.getMeetingTime();
                }
                if (distance > distanceToInfect){
                    timeSinceClose = 0;
                }
                if (distance <= distanceToInfect && timeSinceClose >= minTimeToInfect) {
                    Random rand = new Random();
                    if (rand.nextDouble() < infectingChance){
                        if(rand.nextDouble() < 0.5){
                            otherPerson.changeState(new VulnerableInfectedWithSymptoms(otherPerson));
                            System.out.println("Infected by a person with symptoms");
                        }
                        else{
                            otherPerson.changeState(new VulnerableInfectedNoSymptoms(otherPerson));
                            System.out.println("Infected by a person with symptoms");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void getHealthy(){
        if (person.getArea().getActualTime() - person.getTimeSinceInfected() > infectionDuration){
            System.out.println("A person with symptoms got healthy");
            person.changeState(new Resistant(person));
        }
    }

    private double calculateDistance(Person otherPerson) {
        double dx = this.person.getXCoordinate() - otherPerson.getXCoordinate();
        double dy = this.person.getYCoordinate() - otherPerson.getYCoordinate();
        IVector distance = new Vector2D(dx, dy);
        return distance.abs();
    }
    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State otherState = (State) obj;

        // Replace the comments with actual attributes comparison logic
        // Example: if (this.attribute1 != otherState.attribute1) return false;
        // Example: if (this.attribute2 != otherState.attribute2) return false;

        return true;  // If all attributes are equal, consider the states equal
    }
}


