package org.example.model;

public class Resident {
    private final String name;
    private boolean isWorker;
    private boolean isInhabitant;

    public Resident(String name) {
        this.name = name;
        this.isWorker = false;
        this.isInhabitant = false;
    }

    public String getName() {
        return name;
    }

    public boolean isWorker() {
        return isWorker;
    }

    public boolean isInhabitant() {
        return isInhabitant;
    }

    public void assignAsWorker() {
        isWorker = true;
    }

    public void removeAsWorker() {
        isWorker = false;
    }

    public void assignAsInhabitant() {
        isInhabitant = true;
    }

    public void removeAsInhabitant() {
        isInhabitant = false;
    }

    @Override
    public String toString() {
        if (isWorker) {
            return name + " (Worker)";
        } else if (isInhabitant) {
            return name + " (Inhabitant)";
        } else {
            return name;
        }
    }
}
