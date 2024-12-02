package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameManager {
    private final List<Building> buildings;
    private final List<Building> underConstruction;
    private final List<Resource> resources;
    private final List<Resident> residents;
    private final Map map;

    public GameManager(int mapWidth, int mapHeight) {
        this.buildings = new ArrayList<>();
        this.underConstruction = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.residents = new ArrayList<>();
        this.map = new Map(mapWidth, mapHeight);

        initializeResources();
        initializeResidents();
    }

    private void initializeResources() {
        resources.addAll(List.of(
                new Resource("Food", 100),
                new Resource("Wood", 50),
                new Resource("Stone", 20),
                new Resource("Coal", 0),
                new Resource("Iron", 0),
                new Resource("Steel", 0),
                new Resource("Cement", 0),
                new Resource("Lumber", 0),
                new Resource("Tool", 0)
        ));
    }

    private void initializeResidents() {
        residents.addAll(List.of(
                new Resident("Alice"),
                new Resident("Bob"),
                new Resident("Charlie"),
                new Resident("David"),
                new Resident("Eve")
        ));
    }

    public List<Resident> getResidents() {
        return new ArrayList<>(residents);
    }

    public void addResident(Resident resident) {
        residents.add(resident);
    }

    public Map getMap() {
        return map;
    }

    public List<Building> getBuildings() {
        return new ArrayList<>(buildings);
    }

    public List<Resource> getResources() {
        return new ArrayList<>(resources);
    }

    public List<Building> getBuildingsUnderConstruction() {
        return new ArrayList<>(underConstruction);
    }

    private Optional<Resource> findResource(String name) {
        return resources.stream()
                .filter(resource -> resource.getName().equals(name))
                .findFirst();
    }

    private boolean hasSufficientResources(Building building) {
        return building.getMaterials().stream()
                .allMatch(required ->
                        findResource(required.getName())
                                .map(available -> available.getQuantity() >= required.getQuantity())
                                .orElse(false)
                );
    }

    private void consumeResources(Building building) {
        building.getMaterials().forEach(required ->
                findResource(required.getName())
                        .ifPresent(resource -> resource.removeQuantity(required.getQuantity()))
        );
    }

    public boolean tryAddBuilding(Building building) {
        if (hasSufficientResources(building)) {
            consumeResources(building);
            underConstruction.add(building);
            return true;
        }
        return false;
    }

    public void completeBuildings() {
        List<Building> completedBuildings = new ArrayList<>();

        underConstruction.removeIf(building -> {
            building.decrementTimeToBuild();
            if (building.isConstructed()) {
                completedBuildings.add(building);
                return true; // Remove from underConstruction
            }
            return false;
        });

        buildings.addAll(completedBuildings);
    }

    public void assignWorkerToBuilding(Building building, Resident resident) {
        building.addWorker(resident);
    }

    public void assignInhabitantToBuilding(Building building, Resident resident) {
        building.addInhabitant(resident);
    }

    public void consumeResources() {
        buildings.forEach(building -> {
            building.getWorkers().forEach(worker ->
                    consumeResourceForEntity(worker.getName())
            );
            building.getInhabitants().forEach(inhabitant ->
                    consumeResourceForEntity(inhabitant.getName())
            );
            building.getConsumption().forEach(this::consumeResource);
        });
    }

    private void consumeResourceForEntity(String entityName) {
        findResource("Food").ifPresent(resource -> resource.removeQuantity(1));
    }

    private void consumeResource(Resource consumedResource) {
        findResource(consumedResource.getName())
                .ifPresent(globalResource -> globalResource.removeQuantity(consumedResource.getQuantity()));
    }

    public void produceResources() {
        buildings.forEach(building -> {
            int workerCount = building.getWorkers().size();
            if (workerCount > 0) {
                building.getProduction().forEach(produced ->
                        findResource(produced.getName()).ifPresent(globalResource ->
                                globalResource.addQuantity(produced.getQuantity() * workerCount)
                        )
                );
            }
        });
    }

    public boolean addBuilding(int choice, int x, int y) {
        Building building = createBuilding(choice);
        if (building == null || !isValidPosition(x, y) || !tryAddBuilding(building)) {
            return false;
        }
        return map.placeBuilding(building, x, y);
    }

    private Building createBuilding(int choice) {
        return switch (choice) {
            case 1 -> new Farm();
            case 2 -> new House();
            case 3 -> new Quarry();
            case 4 -> new WoodenCabin();
            case 5 -> new ToolFactory();
            case 6 -> new CementPlant();
            case 7 -> new SteelMill();
            case 8 -> new LumberMill();
            case 9 -> new ApartmentBuilding();
            default -> null;
        };
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < map.getWidth() && y < map.getHeight();
    }
}
