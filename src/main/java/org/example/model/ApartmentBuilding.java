package org.example.model;

// Sous-classe ApartmentBuilding
public class ApartmentBuilding extends Building {
    public ApartmentBuilding() {
        super(
                "Apartment Building",
                3,
                2,
                60,
                0,
                new Resource[]{new Resource("Wood", 50), new Resource("Stone", 50)},
                new Resource[]{},
                new Resource[]{},
                6
        );
    }

    @Override
    public void produce(Resource resource) {
        // Les immeubles d'habitation ne produisent rien
    }

    @Override
    public void consume(Resource resource) {
        // Les fermes ne consomment rien
    }

    @Override
    public String getSymbol() {
        return "A";
    }

    @Override
    public void status() {
        System.out.println("Apartment Building");
        System.out.println("Size: " + getSizeX() + "x" + getSizeY());
        System.out.println("Inhabitants: " + getInhabitants() + "/" + getMaxInhabitants());
        System.out.println("Workers: " + getWorkers() + "/" + getMaxWorkers());
    }
}
