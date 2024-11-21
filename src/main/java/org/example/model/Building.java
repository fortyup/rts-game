package org.example.model;

public abstract class Building {
    private String name;
    private int sizeX, sizeY;
    private int maxInhabitants;
    private int maxWorkers;
    private Resource[] materials;
    private Resource[] consumption;
    private Resource[] production;
    private int timeToBuild;

    public Building(String name, int sizeX, int sizeY, int maxInhabitants, int maxWorkers, Resource[] materials, Resource[] consumption, Resource[] production, int timeToBuild) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.maxInhabitants = maxInhabitants;
        this.maxWorkers = maxWorkers;
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

    public int getInhabitants() {
        return 0;
    }

    public int getMaxInhabitants() {
        return maxInhabitants;
    }

    public int getWorkers() {
        return 0;
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

    public abstract void produce(Resource resource);

    public abstract void consume(Resource resource);

    public void status() {
        System.out.println("Building: " + name);
        System.out.println("Size: " + sizeX + "x" + sizeY);
        System.out.println("Max inhabitants: " + maxInhabitants);
        System.out.println("Max workers: " + maxWorkers);
        System.out.println("Materials: ");
        for (Resource material : materials) {
            System.out.println(material);
        }
        System.out.println("Consumption: ");
        for (Resource resource : consumption) {
            System.out.println(resource);
        }
        System.out.println("Production: ");
        for (Resource resource : production) {
            System.out.println(resource);
        }
        System.out.println("Time to build: " + timeToBuild);
    }
}