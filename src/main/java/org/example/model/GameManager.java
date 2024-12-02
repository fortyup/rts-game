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
            System.out.println("Checking building: " + building.getName() + " with " + building.getTimeToBuild() + " turns left.");
            if (building.getTimeToBuild() == 1) {
                completedBuildings.add(building);
                System.out.println("Building completed: " + building.getName());
            } else {
                building.decrementTimeToBuild();
                System.out.println("Decrementing time to build for: " + building.getName() + ". Turns left: " + building.getTimeToBuild());
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

    public void consumeResources() {
        for (Building building : buildings) {
            for (Resident worker : building.getWorkers()) {
                for (Resource consumedResource : building.getConsumption()) {
                    for (Resource globalResource : resources) {
                        if (globalResource.getName().equals(consumedResource.getName())) {
                            globalResource.removeQuantity(consumedResource.getQuantity());
                            System.out.println("Consumed " + consumedResource.getQuantity() + " " + globalResource.getName());
                        }
                    }
                }
                Resource foodResource = resources.stream()
                        .filter(r -> r.getName().equals("Food"))
                        .findFirst()
                        .orElse(null);
                if (foodResource != null) {
                    foodResource.removeQuantity(1);
                    System.out.println("Consumed 1 Food for worker " + worker.getName());
                }
            }
            for (Resident inhabitant : building.getInhabitants()) {
                Resource foodResource = resources.stream()
                        .filter(r -> r.getName().equals("Food"))
                        .findFirst()
                        .orElse(null);
                if (foodResource != null) {
                    foodResource.removeQuantity(1);
                    System.out.println("Consumed 1 Food for inhabitant " + inhabitant.getName());
                }
            }
        }
    }

    public void produceResources() {
        for (Building building : buildings) {
            if (!building.getWorkers().isEmpty()) {
                for (Resource resource : building.getProduction()) {
                    int baseQuantity = resource.getQuantity();
                    int productionAmount = baseQuantity * building.getWorkers().size();
                    resource.setQuantity(productionAmount);
                    for (Resource globalResource : resources) {
                        if (globalResource.getName().equals(resource.getName())) {
                            globalResource.addQuantity(productionAmount);
                            System.out.println("Produced " + productionAmount + " " + globalResource.getName());
                        }
                    }
                }
            }
        }
    }
}
