package org.example.model;

// Sous-classe SteelMill
public class SteelMill extends Building {
    public SteelMill() {
        super(
                "Steel Mill",
                4,
                3,
                0,
                40,
                new Resource[]{new Resource("Wood", 100), new Resource("Stone", 50)},
                new Resource[]{new Resource("Iron", 4), new Resource("Coal", 2)},
                new Resource[]{new Resource("Steel", 4)},
                6
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
        return "S";
    }
}
