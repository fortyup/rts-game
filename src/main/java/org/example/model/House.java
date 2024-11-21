package org.example.model;

// Sous-classe House
public class House extends Building {
    public House() {
        super(
                "House",
                2,
                2,
                4,
                0,
                new Resource[]{new Resource("Wood", 2), new Resource("Stone", 2)},
                new Resource[]{},
                new Resource[]{},
                4
        );
    }

    @Override
    public void produce(Resource resource) {
        // Les maisons ne produisent rien
    }

    @Override
    public void consume(Resource resource) {
        // Les maisons ne consomment rien
    }

    @Override
    public void status() {
        System.out.println("House");
        System.out.println("Size: " + getSizeX() + "x" + getSizeY());
        System.out.println("Inhabitants: " + getInhabitants() + "/" + getMaxInhabitants());
        System.out.println("Workers: " + getWorkers() + "/" + getMaxWorkers());
    }
}
