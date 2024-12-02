package org.example.model;

import java.util.List;// Sous-classe LumberMill
public class LumberMill extends Building {
    public LumberMill() {
        super(
                "LumberMill",
                3,
                3,
                0,
                10,
                List.of(new Resource("Wood", 50), new Resource("Stone", 50)),
                List.of(new Resource("Wood", 4)),
                List.of(new Resource("Lumber", 4)),
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
        return " L";
    }
}
