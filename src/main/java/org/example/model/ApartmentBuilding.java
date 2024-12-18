package org.example.model;

import java.util.List;

public class ApartmentBuilding extends Building {
    public ApartmentBuilding() {
        super(
                "Apartment Building",
                3,
                2,
                60,
                0,
                List.of(new Resource("Wood", 50), new Resource("Stone", 50)),
                List.of(),
                List.of(),
                6
        );
    }

    @Override
    public void produce(Resource resource) {
        // Apartment buildings do not produce anything
        // Keep the method empty but aligned with the Building class method signature
    }

    @Override
    public void consume(Resource resource) {
        // Apartment buildings do not consume anything
        // Keep the method empty but aligned with the Building class method signature
    }

    @Override
    public String getSymbol() {
        return " A";
    }
}