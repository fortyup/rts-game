package org.example.model;

import java.util.List;

public class Farm extends Building {
    public Farm() {
        super(
                "Farm",
                3,
                3,
                5,
                3,
                List.of(new Resource("Wood", 5), new Resource("Stone", 5)),
                List.of(),
                List.of(new Resource("Food", 10)),
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
    }

    @Override
    public String getSymbol() {
        return " F";
    }
}