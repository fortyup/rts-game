package org.example.model;

import java.util.List;

// Sous-classe Farm
public class Farm extends Building {
    private int foodProduction;

    public Farm() {
        super("Farm", 2);
        this.foodProduction = 10;
    }

    @Override
    public void produce(Resource resource) {
        resource.add(foodProduction);
        System.out.println("Farm produced " + foodProduction + " food.");
    }
}