package org.example.model;

public class Resident {
    private final String name;
    private boolean isWorker;

    public Resident(String name) {
        this.name = name;
        this.isWorker = false;
    }

    public String getName() {
        return name;
    }

    public boolean isWorker() {
        return isWorker;
    }

    public void assignAsWorker() {
        isWorker = true;
    }

    public void removeAsWorker() {
        isWorker = false;
    }

    @Override
    public String toString() {
        return name + (isWorker ? " (Worker)" : "");
    }
}
