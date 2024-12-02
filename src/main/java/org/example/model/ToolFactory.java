package org.example.model;

import java.util.List;// Sous-classe ToolFactory
public class ToolFactory extends Building {
    public ToolFactory() {
        super(
                "Tool Factory",
                4,
                3,
                0,
                12,
                List.of(new Resource("Wood", 50), new Resource("Stone", 50)),
                List.of(new Resource("Steel", 4), new Resource("Coal", 4)),
                List.of(new Resource("Tool", 4)),
                8
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
        return " T";
    }
}
