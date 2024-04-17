package com.example.simulation;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    private final List<PersonState> peopleStates;

    public Memento(List<Person> people) {
        this.peopleStates = new ArrayList<>();
        for (Person person : people) {
            // Zapisz pełny stan każdej osoby
            PersonState personState = new PersonState(
                    person.getXCoordinate(),
                    person.getYCoordinate(),
                    person.getVelocity(),
                    person.getDirection(),
                    person.isInfected(),
                    person.getState()
            );

            personState.setTimeSinceInfected(person.getArea().getActualTime());

            peopleStates.add(personState);
        }
    }

    public List<PersonState> getPeopleStates() {
        return peopleStates;
    }
}
