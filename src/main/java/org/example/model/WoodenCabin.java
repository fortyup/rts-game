package org.example.model;

// Sous-classe WoodenCabin
public class WoodenCabin extends Building {
    public WoodenCabin() {
        super(
                "Wooden Cabin",
                1,
                1,
                2,
                2,
                new Resource[]{new Resource("Wood", 1)},
                new Resource[]{},
                new Resource[]{new Resource("Wood", 2), new Resource("Food", 2)},
                2
        );
    }

    @Override
    public void produce(Resource resource) {
        resource.addQuantity(2);
    }

    @Override
    public void consume(Resource resource) {
        // Les cabanes en bois ne consomment rien
    }

    @Override
    public String getSymbol() {
        return "W";
    }

    @Override
    public void status() {
        System.out.println("Wooden Cabin");
        System.out.println("Size: " + getSizeX() + "x" + getSizeY());
        System.out.println("Inhabitants: " + getInhabitants() + "/" + getMaxInhabitants());
        System.out.println("Workers: " + getWorkers() + "/" + getMaxWorkers());
    }
}
