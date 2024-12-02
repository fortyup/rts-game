package org.example.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Building {
    private String name;
    private int sizeX, sizeY;
    private int maxInhabitants;
    private List<Resident> inhabitants;
    private int maxWorkers;
    private List<Resident> workers;
    private Resource[] materials;
    private Resource[] consumption;
    private Resource[] production;
    private int timeToBuild;

    public Building(String name, int sizeX, int sizeY, int maxInhabitants, int maxWorkers, Resource[] materials, Resource[] consumption, Resource[] production, int timeToBuild) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.maxInhabitants = maxInhabitants;
        this.inhabitants = new ArrayList<>();
        this.maxWorkers = maxWorkers;
        this.workers = new ArrayList<>();
        this.materials = materials;
        this.consumption = consumption;
        this.production = production;
        this.timeToBuild = timeToBuild;
    }

    public String getName() {
        return name;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public List<Resident> getInhabitants() {
        return inhabitants;
    }

    public int getMaxInhabitants() {
        return maxInhabitants;
    }

    public List<Resident> getWorkers() {
        return workers;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public Resource[] getMaterials() {
        return materials;
    }

    public Resource[] getConsumption() {
        return consumption;
    }

    public Resource[] getProduction() {
        return production;
    }

    public int getTimeToBuild() {
        return timeToBuild;
    }

    public void decrementTimeToBuild() {
        if (timeToBuild > 0) {
            timeToBuild--;
        }
    }

    public abstract String getSymbol();

    public boolean isUnderConstruction() {
        return timeToBuild > 0;
    }

    public boolean isConstructed() {
        return timeToBuild == 0;
    }

    public void addInhabitant(Resident resident) {
        if (inhabitants.size() < maxInhabitants) {
            inhabitants.add(resident);
        } else {
            throw new IllegalStateException("Maximum number of inhabitants reached.");
        }
    }

    public void addWorker(Resident resident) {
        if (workers.size() < maxWorkers) {
            workers.add(resident);
        } else {
            throw new IllegalStateException("Maximum number of workers reached.");
        }
    }

    public void produce(Resource resource) {
        for (Resource prod : getProduction()) {
            int baseQuantity = prod.getQuantity();
            int productionAmount = baseQuantity * getWorkers().size();
            prod.setQuantity(productionAmount);
            resource.addQuantity(productionAmount);
        }
    }

    public void consume(Resource resource) {
        for (Resource consumedResource : getConsumption()) {
            System.out.println("ConsumedResource: " + consumedResource);
            if (consumedResource.getName().equals(resource.getName())) {
                resource.removeQuantity(consumedResource.getQuantity());
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Building: " + name + "\n" +
                "Size: " + sizeX + "x" + sizeY + "\n" +
                "Inhabitants: " + inhabitants.size() + "/" + maxInhabitants + "\n" +
                "Workers: " + workers.size() + "/" + maxWorkers + "\n" +
                "Materials: " + Arrays.toString(materials) + "\n" +
                "Consumption: " + Arrays.toString(consumption) + "\n" +
                "Production: " + Arrays.toString(production) + "\n" +
                "Time to build: " + timeToBuild;
    }
}