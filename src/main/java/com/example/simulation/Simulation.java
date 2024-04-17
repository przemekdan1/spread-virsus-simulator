package com.example.simulation;

import javafx.scene.shape.Rectangle;

import java.util.Iterator;
import java.util.Random;

public class Simulation {
    private Population population;
    private Area area;
    private int numberOfNewPeople = 1;
    private int nLength =  500; //nLength = (nLength/25)m
    private int mLength = 500; //mLength = (mLength/25)m
    private int initialPopulation = 200;
    private Random rand = new Random();


    public Simulation(){
        this.area = new Area(nLength, mLength);
        this.population = new Population(area);
        for(int i = 0; i < initialPopulation; i++){ //healthy population initiation

            double randomX, randomY;
            randomX = rand.nextDouble() * nLength;
            randomY = rand.nextDouble() * mLength;

            double randomVelocity = rand.nextDouble() * population.getUPPER_VELOCITY_LIMIT();

            double directionX = rand.nextDouble() * 2 - 1;
            double directionY = rand.nextDouble() * 2 - 1;
            IVector direction = new Vector2D(directionX, directionY);

            Person person = new Person(randomX, randomY, randomVelocity, direction,false, population, area);

            population.addPerson(person);
        }
    }

    public int getnLength(){return  this.nLength;}
    public int getmLength(){return  this.mLength;}
    public Area getArea(){
        return this.area;
    }

    public Population getPopulation() {
        return this.population;
    }

    public void performTimeStep(long actualTime) {
        area.setActualTime(actualTime);
        Iterator<Person> iterator = getPopulation().getPeople().iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            person.move();

            if (rand.nextDouble() < 0.05) {
                person.setVelocity((rand.nextDouble() * population.getUPPER_VELOCITY_LIMIT()));
            }

            if (rand.nextDouble() < 0.05) {
                person.setDirection(new Vector2D(rand.nextDouble() * 2 - 1, rand.nextDouble() * 2 - 1));
            }

            if (person.getState() instanceof VulnerableInfectedNoSymptoms || person.getState() instanceof VulnerableInfectedWithSymptoms) {
                person.getState().getHealthy();
                person.getState().infect(getPopulation().getPeople());
            }

            if (person.isMarkedForRemoval()) {
                iterator.remove();
            }
        }
        if (rand.nextDouble() < 0.05) {
            getPopulation().spawnNewPeople(numberOfNewPeople);
        }
    }
}

