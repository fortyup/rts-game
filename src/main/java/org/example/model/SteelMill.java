package org.example.model;

import java.util.List;

// Sous-classe SteelMill
public class SteelMill extends Building {
    public SteelMill() {
        super(
                "Steel Mill",
                4,
                3,
                0,
                40,
                List.of(new Resource("Wood", 100), new Resource("Stone", 50)),
                List.of(new Resource("Iron", 4), new Resource("Coal", 2)),
                List.of(new Resource("Steel", 4)),
                6
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
        return " S";
    }
}
