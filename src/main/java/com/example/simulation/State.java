package com.example.simulation;
import javafx.scene.paint.Color;

import java.util.List;

public abstract class State {
    protected int frames = 25;
    Person person;
    State (Person person){
        this.person = person;
    }

    public abstract void infect(List<Person> people);
    public abstract void getHealthy();
    public abstract Color getColor();

}

