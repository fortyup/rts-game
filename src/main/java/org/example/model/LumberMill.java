package org.example.model;

// Sous-classe LumberMill
public class LumberMill extends Building {
    public LumberMill() {
        super(
                "LumberMill",
                3,
                3,
                0,
                10,
                new Resource[]{new Resource("Wood", 50), new Resource("Stone", 50)},
                new Resource[]{new Resource("Wood", 4)},
                new Resource[]{new Resource("Lumber", 4)},
                4
        );
    }

    @Override
    public void produce(Resource resource) {
        super.produce(resource);
    }

    @Override
    public void consume(Resource resource) {
        for (Resource consumedResource : getConsumption()) {
            if (consumedResource.getName().equals(resource.getName())) {
                resource.removeQuantity(consumedResource.getQuantity());
                break;
            }
        }
    }

    @Override
    public String getSymbol() {
        return "L";
    }
}
