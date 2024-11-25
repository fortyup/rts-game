package org.example.model;

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

    public boolean canBuild(Building building) {
        for (Resource required : building.getMaterials()) {
            Resource available = resources.stream()
                    .filter(r -> r.getName().equals(required.getName()))
                    .findFirst()
                    .orElse(null);
            if (available == null || available.getQuantity() < required.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    public void addBuilding(Building building) {
        if (canBuild(building)) {
            for (Resource required : building.getMaterials()) {
                Resource available = resources.stream()
                        .filter(r -> r.getName().equals(required.getName()))
                        .findFirst()
                        .orElse(null);
                if (available != null) {
                    available.setQuantity(available.getQuantity() - required.getQuantity());
                }
            }
            buildings.add(building);
            System.out.println(building.getName() + " added.");
        } else {
            System.out.println("Not enough resources to build " + building.getName() + ".");
        }
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
        System.out.println("\n--- Next Turn ---");
        for (Building building : buildings) {
            for (Resource produced : building.getProduction()) {
                Resource available = resources.stream()
                        .filter(r -> r.getName().equals(produced.getName()))
                        .findFirst()
                        .orElse(null);
                if (available != null) {
                    building.produce(available);
                }
            }
        }
    }
}
