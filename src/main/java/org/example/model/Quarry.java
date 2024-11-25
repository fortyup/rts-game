package org.example.model;

// Sous-classe Quarry
public class Quarry extends Building {
    public Quarry() {
        super(
                "Quarry",
                2,
                2,
                2,
                30,
                new Resource[]{new Resource("Wood", 50)},
                new Resource[]{},
                new Resource[]{new Resource("Stone", 4), new Resource("Iron", 4), new Resource("Coal", 4)},
                2
        );
    }

    @Override
    public void produce(Resource resource) {
        resource.addQuantity(4);
    }

    @Override
    public void consume(Resource resource) {
        // Les carrières ne consomment rien
    }

    @Override
    public String getSymbol() {
        return "Q";
    }

    @Override
    public void status() {
        System.out.println("House");
        System.out.println("Size: " + getSizeX() + "x" + getSizeY());
        System.out.println("Inhabitants: " + getInhabitants() + "/" + getMaxInhabitants());
        System.out.println("Workers: " + getWorkers() + "/" + getMaxWorkers());
    }
}
