package org.example.model;

// Sous-classe LumberMill
public class LumberMill extends Building {
    public LumberMill() {
        super(
                "LumberMill",
                3,
                3,
                0,
                10,
                new Resource[]{new Resource("Wood", 50), new Resource("Stone", 50)},
                new Resource[]{new Resource("Wood", 4)},
                new Resource[]{new Resource("Lumber", 4)},
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
        System.out.println("Lumber Mill");
        System.out.println("Size: " + getSizeX() + "x" + getSizeY());
        System.out.println("Inhabitants: " + getInhabitants() + "/" + getMaxInhabitants());
        System.out.println("Workers: " + getWorkers() + "/" + getMaxWorkers());
    }
}
