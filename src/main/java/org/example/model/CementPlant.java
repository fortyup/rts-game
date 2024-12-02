package org.example.model;

public class CementPlant extends Building {
    public CementPlant() {
        super(
                "Cement Plant",
                4,
                3,
                0,
                10,
                new Resource[]{new Resource("Wood", 50), new Resource("Stone", 50)},
                new Resource[]{new Resource("Stone", 4), new Resource("Coal", 4)},
                new Resource[]{new Resource("Cement", 4)},
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
        return "C";
    }
}