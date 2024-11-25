package org.example.model;

// Sous-classe Farm
public class Farm extends Building {
    public Farm() {
        super(
                "Farm",
                3,
                3,
                5,
                3,
                new Resource[]{new Resource("Wood", 5), new Resource("Stone", 5)},
                new Resource[]{},
                new Resource[]{new Resource("Food", 10)},
                2
        );
    }

    @Override
    public void produce(Resource resource) {
        resource.addQuantity(10);
    }

    @Override
    public void consume(Resource resource) {
        // Les fermes ne consomment rien
    }

    @Override
    public String getSymbol() {
        return "F";
    }

    @Override
    public void status() {
        System.out.println("House");
        System.out.println("Size: " + getSizeX() + "x" + getSizeY());
        System.out.println("Inhabitants: " + getInhabitants() + "/" + getMaxInhabitants());
        System.out.println("Workers: " + getWorkers() + "/" + getMaxWorkers());
    }
}
