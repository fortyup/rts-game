package org.example.model;

// Sous-classe CementPlant
public class CementPlant extends Building {
    public CementPlant() {
        super(
                "Cement Plant",
                4,
                3,
                0,
                10,
                new Resource[]{new Resource("Wood", 50), new Resource("Stone", 50)},
                new Resource[]{new Resource("Stone", 4), new Resource("Coal", 4)},
                new Resource[]{new Resource("Cement", 4)},
                4
        );
    }

    @Override
    public void produce(Resource resource) {
        resource.addQuantity(4);
    }

    @Override
    public void consume(Resource resource) {
        resource.removeQuantity(4);
    }

    @Override
    public void status() {
        System.out.println("Cement Plant");
        System.out.println("Size: " + getSizeX() + "x" + getSizeY());
        System.out.println("Inhabitants: " + getInhabitants() + "/" + getMaxInhabitants());
        System.out.println("Workers: " + getWorkers() + "/" + getMaxWorkers());
    }
}
