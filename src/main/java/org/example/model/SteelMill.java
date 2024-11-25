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
        resource.addQuantity(4);
    }

    @Override
    public void consume(Resource resource) {
        if (resource.getName().equals("Iron")) {
            resource.removeQuantity(4);
        } else if (resource.getName().equals("Coal")) {
            resource.removeQuantity(2);
        }
    }

    @Override
    public String getSymbol() {
        return "S";
    }

    @Override
    public void status() {
        System.out.println("Steel Mill");
        System.out.println("Size: " + getSizeX() + "x" + getSizeY());
        System.out.println("Inhabitants: " + getInhabitants() + "/" + getMaxInhabitants());
        System.out.println("Workers: " + getWorkers() + "/" + getMaxWorkers());
    }
}
