package org.example.model;

public class Farm extends Building {
    public Farm() {
        super(
                "Farm",
                3,
                3,
                5,
                3,
                new Resource[]{new Resource("Wood", 5), new Resource("Stone", 5)},
                new Resource[]{},
                new Resource[]{new Resource("Food", 10)},
                2
        );
    }

    @Override
    public void produce(Resource resource) {
        System.out.println("Producing resource: " + resource.getName());
        System.out.println("Number of workers: " + getWorkers().size());
        for (Resource prodResource : getProduction()) {
            System.out.println("Producing: " + prodResource.getName() + " with quantity: " + prodResource.getQuantity());
            int productionAmount = prodResource.getQuantity() * getWorkers().size();
            prodResource.setQuantity(productionAmount);
            System.out.println("New quantity: " + prodResource.getQuantity());
        }
        super.produce(resource);
    }

    @Override
    public void consume(Resource resource) {
        // Farms do not consume anything
        // Keep the method empty but aligned with the Building class method signature
    }

    @Override
    public String getSymbol() {
        return "F";
    }
}