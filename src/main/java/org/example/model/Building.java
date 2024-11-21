package org.example.model;

public abstract class Building {
    private String name;
    private int foodConsumption;

    public Building(String name, int foodConsumption) {
        this.name = name;
        this.foodConsumption = foodConsumption;
    }

    public String getName() {
        return name;
    }

    public int getFoodConsumption() {
        return foodConsumption;
    }

    public abstract void produce(Resource resource);
}