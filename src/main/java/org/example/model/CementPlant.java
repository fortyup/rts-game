package org.example.model;

import java.util.List;

public class CementPlant extends Building {
    public CementPlant() {
        super(
                "Cement Plant",
                4,
                3,
                0,
                10,
                List.of(new Resource("Wood", 50), new Resource("Stone", 50)),
                List.of(new Resource("Stone", 4), new Resource("Coal", 4)),
                List.of(new Resource("Cement", 4)),
                4
        );
    }

    @Override
    public void produce(Resource resource) {
        super.produce(resource);
    }

    @Override
    public void consume(Resource resource) {
        super.consume(resource);
    }

    @Override
    public String getSymbol() {
        return " C";
    }
}