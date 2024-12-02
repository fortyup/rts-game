package org.example.model;

import java.util.List;// Sous-classe Quarry
public class Quarry extends Building {
    public Quarry() {
        super(
                "Quarry",
                2,
                2,
                2,
                30,
                List.of(new Resource("Wood", 50)),
                List.of(),
                List.of(new Resource("Stone", 4), new Resource("Iron", 4), new Resource("Coal", 4)),
                2
        );
    }

    @Override
    public void produce(Resource resource) {
        super.produce(resource);
    }

    @Override
    public void consume(Resource resource) {
        // Les carrières ne consomment rien
    }

    @Override
    public String getSymbol() {
        return " Q";
    }
}
