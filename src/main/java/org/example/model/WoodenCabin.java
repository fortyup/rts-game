package org.example.model;

public class WoodenCabin extends Building {
    public WoodenCabin() {
        super(
                "Wooden Cabin",
                1,
                1,
                2,
                2,
                new Resource[]{new Resource("Wood", 1)},
                new Resource[]{},
                new Resource[]{new Resource("Wood", 2), new Resource("Food", 2)},
                2
        );
    }

    @Override
    public void produce(Resource resource) {
        // Use the parent class method
        super.produce(resource);
    }

    @Override
    public void consume(Resource resource) {
        // Wooden cabins do not consume anything
    }

    @Override
    public String getSymbol() {
        return "W";
    }
}