package org.example.model;

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
        super.produce(resource);
    }

    @Override
    public void consume(Resource resource) {
        // Farms do not consume anything
        // Keep the method empty but aligned with the Building class method signature
    }

    @Override
    public String getSymbol() {
        return "F";
    }
}