package org.example.model;

// Sous-classe ToolFactory
public class ToolFactory extends Building {
    public ToolFactory() {
        super(
                "Tool Factory",
                4,
                3,
                0,
                12,
                new Resource[]{new Resource("Wood", 50), new Resource("Stone", 50)},
                new Resource[]{new Resource("Steel", 4), new Resource("Coal", 4)},
                new Resource[]{new Resource("Tool", 4)},
                8
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
        System.out.println("House");
        System.out.println("Size: " + getSizeX() + "x" + getSizeY());
        System.out.println("Inhabitants: " + getInhabitants() + "/" + getMaxInhabitants());
        System.out.println("Workers: " + getWorkers() + "/" + getMaxWorkers());
    }
}
