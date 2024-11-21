package org.example.model;

// Sous-classe House
public class House extends Building {
    public House() {
        super("House", 4);
    }

    @Override
    public void produce(Resource resource) {
        // Les maisons ne produisent rien
    }
}
