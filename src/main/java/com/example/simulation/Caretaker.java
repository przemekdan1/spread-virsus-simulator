package com.example.simulation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> mementos = new ArrayList<>();

    public void addMemento(Memento memento) {
        mementos.add(memento);
    }

    public Memento getMemento(int index) {
        return mementos.get(index);
    }

    public ObservableList<Memento> getMementos() {
        return FXCollections.observableArrayList(mementos);
    }
    public void clearMementos() {
        mementos.clear();
    }
}
