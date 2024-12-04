package org.example.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Building {
    private final String name;
    private final int sizeX;
    private final int sizeY;
    private final int maxInhabitants;
    private final List<Resident> inhabitants;
    private final int maxWorkers;
    private final List<Resident> workers;
    private final List<Resource> materials;
    private final List<Resource> consumption;
    private final List<Resource> production;
    private int timeToBuild;

    protected Building(String name, int sizeX, int sizeY, int maxInhabitants, int maxWorkers, List<Resource> materials, List<Resource> consumption, List<Resource> production, int timeToBuild) {
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

    public List<Resource> getMaterials() {
        return materials;
    }

    public List<Resource> getConsumption() {
        return consumption;
    }

    public List<Resource> getProduction() {
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
            if (resident.isWorker()) {
                resident.removeAsWorker();
                workers.remove(resident);
            }
            inhabitants.add(resident);
            resident.assignAsInhabitant();
        } else {
            throw new IllegalStateException("Maximum number of inhabitants reached.");
        }
    }

    public void addWorker(Resident resident) {
        if (workers.size() < maxWorkers) {
            if (resident.isInhabitant()) {
                resident.removeAsInhabitant();
                inhabitants.remove(resident);
            }
            workers.add(resident);
            resident.assignAsWorker();
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
                "Materials: " + materials + "\n" +
                "Consumption: " + consumption + "\n" +
                "Production: " + production + "\n" +
                "Time to build: " + timeToBuild;
    }
}