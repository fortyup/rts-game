package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private List<Building> buildings;
    private List<Building> underConstruction;
    private List<Resource> resources;
    private List<Resident> residents;
    private Map map;

    public GameManager(int mapWidth, int mapHeight) {
        this.buildings = new ArrayList<>();
        this.underConstruction = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.residents = new ArrayList<>();
        this.map = new Map(mapWidth, mapHeight);

        // Initialize resources
        this.resources.add(new Resource("Food", 100));
        this.resources.add(new Resource("Wood", 50));
        this.resources.add(new Resource("Stone", 20));
        this.resources.add(new Resource("Coal", 0));
        this.resources.add(new Resource("Iron", 0));
        this.resources.add(new Resource("Steel", 0));
        this.resources.add(new Resource("Cement", 0));
        this.resources.add(new Resource("Lumber", 0));
        this.resources.add(new Resource("Tool", 0));

        // Initialize some default residents
        this.residents.add(new Resident("Alice"));
        this.residents.add(new Resident("Bob"));
        this.residents.add(new Resident("Charlie"));
        this.residents.add(new Resident("David"));
        this.residents.add(new Resident("Eve"));
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void addResident(Resident resident) {
        residents.add(resident);
    }

    public Map getMap() {
        return map;
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

    private void deductResources(Building building) {
        for (Resource required : building.getMaterials()) {
            Resource available = resources.stream()
                    .filter(r -> r.getName().equals(required.getName()))
                    .findFirst()
                    .orElse(null);
            if (available != null) {
                available.setQuantity(available.getQuantity() - required.getQuantity());
            }
        }
    }

    public boolean tryAddBuilding(Building building) {
        if (canBuild(building)) {
            deductResources(building);
            underConstruction.add(building);
            return true;
        }
        return false;
    }

    public void displayBuildingDetails(Building building) {
        System.out.println(building.toString());
    }

    public List<Building> completeBuildings() {
        List<Building> completedBuildings = new ArrayList<>();
        for (Building building : underConstruction) {
            building.decrementTimeToBuild();
            if (building.isConstructed()) {
                completedBuildings.add(building);
                displayBuildingDetails(building);
            }
        }
        underConstruction.removeAll(completedBuildings);
        buildings.addAll(completedBuildings);
        return completedBuildings;
    }

    public void assignWorkerToBuilding(Building building, Resident resident) {
        building.addWorker(resident);
    }

    public void assignInhabitantToBuilding(Building building, Resident resident) {
        building.addInhabitant(resident);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public List<Building> getBuildingsUnderConstruction() {
        return underConstruction;
    }
}
