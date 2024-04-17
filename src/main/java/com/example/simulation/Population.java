package com.example.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private double UPPER_VELOCITY_LIMIT = 2.5;
    private List<Person> people;
    private Random rand;
    private boolean isInfected = false;
    private Area area;

    public Population(Area area){
        this.people = new ArrayList<>();
        this.rand = new Random();
        this.area = area;
    }
    public void setState(List<Person> state) {
        this.people = new ArrayList<>(state);
        for (Person person : people){
            person.setTimeSinceInfected(area.getActualTime());
        }
    }

    public List<Person> getState() {
        return new ArrayList<>(people);
    }



public void setMemento(Memento memento) {
    List<Person> restoredPeople = new ArrayList<>();
    long currentTime = area.getActualTime();

    for (int i = 0; i < Math.min(people.size(), memento.getPeopleStates().size()); i++) {
        PersonState personState = memento.getPeopleStates().get(i);

        // Twórz nowe obiekty Person na podstawie stanu z Memento
        Person restoredPerson = new Person(
                personState.getXCoordinate(),
                personState.getYCoordinate(),
                personState.getVelocity(),
                personState.getDirection(),
                personState.isInfected(),
                this,
                area
        );
        restoredPerson.changeState(personState.getState()); // Przywracanie stanu

        long timeSinceInfected = personState.getTimeSinceInfected();
        restoredPerson.setTimeSinceInfected(currentTime - (currentTime - timeSinceInfected));
        restoredPeople.add(restoredPerson);
    }

    // Zastąp aktualną populację przywróconymi obiektami Person
    people = restoredPeople;
}

    public List<Person> getPeople(){
        return this.people;
    }

    public double getUPPER_VELOCITY_LIMIT(){
        return this.UPPER_VELOCITY_LIMIT;
    }

    public void addPerson(Person person){
        people.add(person);
    }

//    public void removePerson(Person person){
//        people.remove(person);
//    }

    public void spawnNewPeople(int n){
        for(int i = 0; i < n; i ++){
            if (rand.nextDouble() < 0.1) {
                isInfected = true;
            }

            int randomBorder = rand.nextInt(4);
            double xCoordinate = 0, yCoordinate = 0;

            if(randomBorder == 0){
                xCoordinate = 0;
                yCoordinate = rand.nextDouble() * area.getM();
            }
            else if(randomBorder == 1){
                xCoordinate = area.getN();
                yCoordinate = rand.nextDouble() * area.getM();
            }
            else if(randomBorder == 2){
                xCoordinate = rand.nextDouble() * area.getN();
                yCoordinate = 0;
            }
            else if(randomBorder == 3){
                xCoordinate = rand.nextDouble() * area.getN();
                yCoordinate = area.getM();
            }

            double randomVelocity = rand.nextDouble() * UPPER_VELOCITY_LIMIT;

            double directionX = rand.nextDouble() * 2 - 1;
            double directionY = rand.nextDouble() * 2 - 1;
            IVector direction = new Vector2D(directionX, directionY);

            Person person = new Person(xCoordinate, yCoordinate, randomVelocity, direction, isInfected, this, area);
            addPerson(person);
            isInfected = false;
        }
    }
}
