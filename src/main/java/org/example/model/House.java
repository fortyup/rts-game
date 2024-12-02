package org.example.model;

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
        // Houses do not produce anything
        // Keep the method empty but aligned with the Building class method signature
    }

    @Override
    public void consume(Resource resource) {
        // Houses do not consume anything
        // Keep the method empty but aligned with the Building class method signature
    }

    @Override
    public String getSymbol() {
        return "H";
    }
}