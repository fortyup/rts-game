// src/main/java/org/example/model/Model.java
package org.example.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Model implements Subject {
    private final Queue<String> tickets = new LinkedList<>();
    private final List<Observer> observers = new ArrayList<>();
    private final List<Building> buildings = new ArrayList<>();
    private int turnCount = 0;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(new ArrayList<>(tickets));
        }
    }

    public void incrementTurnCount() {
        turnCount++;
        decrementTimeToBuild();
        notifyObservers();
    }

    public void decrementTimeToBuild() {
        buildings.forEach(Building::decrementTimeToBuild);
        notifyObservers();
    }

    public void addBuilding(Building building) {
        buildings.add(building);
        notifyObservers();
    }

    public List<Building> getBuildings() {
        return new ArrayList<>(buildings);
    }

    public void addResident(Resident resident) {
        notifyObservers();
    }

    public int getTurnCount() {
        return turnCount;
    }

    public String processTicket() {
        String ticket = tickets.poll();
        notifyObservers();
        return ticket;
    }
}