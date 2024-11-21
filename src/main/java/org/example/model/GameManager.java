package org.example.model;

import org.example.model.Building;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private List<Building> buildings;
    private List<Resource> resources;

    public GameManager() {
        buildings = new ArrayList<>();
        resources = new ArrayList<>();
        resources.add(new Resource("Food", 100));
        resources.add(new Resource("Wood", 50));
        resources.add(new Resource("Stone", 20));
        resources.add(new Resource("Coal", 0));
        resources.add(new Resource("Iron", 0));
        resources.add(new Resource("Steel", 0));
        resources.add(new Resource("Cement", 0));
        resources.add(new Resource("Lumber", 0));
        resources.add(new Resource("Toos", 0));

    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public Resource getWood() {
        return resources.stream()
                .filter(r -> r.getName().equals("Wood"))
                .findFirst()
                .orElse(null);
    }

    public void simulateTurn() {
        int totalFoodConsumption = 0;

        // Consommation
        for (Building building : buildings) {
            totalFoodConsumption += building.getFoodConsumption();
        }

        Resource food = resources.stream()
                .filter(r -> r.getName().equals("Food"))
                .findFirst()
                .orElse(null);

        if (food != null && !food.remove(totalFoodConsumption)) {
            System.out.println("Not enough food! Some buildings might stop working.");
        } else {
            System.out.println("Food consumed: " + totalFoodConsumption);
        }

        // Production
        for (Building building : buildings) {
            building.produce(food);
        }
    }
}
