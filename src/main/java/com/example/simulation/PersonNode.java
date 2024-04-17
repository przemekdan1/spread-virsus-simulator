package com.example.simulation;

import javafx.scene.shape.Circle;

public class PersonNode extends Circle {
    private static final double NODE_RADIUS = 15;

    private final Person person;

    public PersonNode(Person person) {
        super(NODE_RADIUS, person.getState().getColor());
        this.person = person;
        setCenterX(person.getXCoordinate());
        setCenterY(person.getYCoordinate());
    }

    public void updateNode() {
        // Update node properties based on the current state of the person
        setFill(person.getState().getColor());
        setCenterX(person.getXCoordinate());
        setCenterY(person.getYCoordinate());
    }
}
