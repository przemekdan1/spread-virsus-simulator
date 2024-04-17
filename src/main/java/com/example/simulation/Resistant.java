package com.example.simulation;

import javafx.scene.paint.Color;

import java.util.List;

public class Resistant extends State{
    private double infectingChance = 0;

    Resistant(Person person) {
        super(person);
        person.setTimeSinceInfected(person.getArea().getActualTime());
    }

    @Override
    public void infect(List<Person> people) {
        //Healthy person can't infect other people
    }
    @Override
    public void getHealthy(){
        //Healthy person can't get healthier
    }
    @Override
    public Color getColor() {
        return Color.GREEN;
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

