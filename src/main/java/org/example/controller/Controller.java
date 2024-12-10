// src/main/java/org/example/controller/Controller.java
package org.example.controller;

import javafx.event.ActionEvent;
import org.example.model.*;
import org.example.view.View;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final View view;
    private final Model model;
    private final CommandManager commandManager;
    private final List<Resource> resources;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.resources = new ArrayList<>();
        this.commandManager = new CommandManager();

        model.addObserver(view);
        initializeResources();
        updateResourcesView(); // Ajoutez cet appel
        initializeViewActions();
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

    private void initializeViewActions() {
        view.setOnAddBuildingAction((ActionEvent event) -> {
            view.showBuildingSelectionPopup((ActionEvent selectionEvent) -> {
                String buildingType = (String) selectionEvent.getSource();
                if (buildingType != null && !buildingType.trim().isEmpty()) {
                    int x = view.getSelectedX();
                    int y = view.getSelectedY();

                    Building building = createBuilding(buildingType, x, y);

                    // Vérifiez si les ressources nécessaires sont disponibles
                    if (areResourcesAvailable(building)) {
                        // Vérifiez si l'emplacement est libre
                        boolean isLocationFree = model.getBuildings().stream()
                                .noneMatch(existingBuilding ->
                                        existingBuilding.getX() < x + building.getSizeX() &&
                                                existingBuilding.getX() + existingBuilding.getSizeX() > x &&
                                                existingBuilding.getY() < y + building.getSizeY() &&
                                                existingBuilding.getY() + existingBuilding.getSizeY() > y
                                );

                        if (isLocationFree) {
                            // Déduisez les ressources nécessaires
                            deductResources(building);

                            building.setX(x);
                            building.setY(y);

                            commandManager.addCommand(() -> {
                                model.addBuilding(building);
                                view.updateBuildings(model.getBuildings());
                                updateResourcesView(); // Mettez à jour l'affichage des ressources
                            });

                            view.showPopup("Bâtiment ajouté : " + buildingType + " à la position (" + x + ", " + y + ").");
                        } else {
                            view.showPopup("Impossible de placer le bâtiment. Cet emplacement est déjà occupé.");
                        }
                    } else {
                        view.showPopup("Ressources insuffisantes pour construire ce bâtiment.");
                    }
                } else {
                    view.showPopup("Veuillez sélectionner un bâtiment valide.");
                }
            });
        });

        // Les autres actions restent inchangées
        view.setOnAddResidentAction((ActionEvent event) ->
                commandManager.addCommand(() -> {
                    String processedTicket = model.processTicket();
                    if (processedTicket != null) {
                        view.showPopup("Ticket traité : " + processedTicket);
                    } else {
                        view.showPopup("Aucun ticket à traiter.");
                    }
                })
        );

        view.setOnAddNextTurnAction((ActionEvent event) -> {
            commandManager.addCommand(() -> {
                model.incrementTurnCount();
                view.updateTurnCount(model.getTurnCount());
                view.updateBuildings(model.getBuildings());
            });
        });

        view.setOnAddQuitAction((ActionEvent event) -> {
            commandManager.addCommand(() -> {
                view.showPopup("Fermeture de l'application...");
                System.exit(0);
            });
        });
    }

    private boolean areResourcesAvailable(Building building) {
        for (Resource requiredResource : building.getMaterials()) {
            Resource availableResource = resources.stream()
                    .filter(resource -> resource.getName().equals(requiredResource.getName()))
                    .findFirst()
                    .orElse(null);
            if (availableResource == null || availableResource.getQuantity() < requiredResource.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private void deductResources(Building building) {
        for (Resource requiredResource : building.getMaterials()) {
            Resource availableResource = resources.stream()
                    .filter(resource -> resource.getName().equals(requiredResource.getName()))
                    .findFirst()
                    .orElse(null);
            if (availableResource != null) {
                availableResource.setQuantity(availableResource.getQuantity() - requiredResource.getQuantity());
            }
        }
    }

    private Building createBuilding(String buildingType, int x, int y) {
        // Create and return the appropriate Building subclass instance based on buildingType
        switch (buildingType) {
            case "Farm":
                return new Farm();
            case "House":
                return new House();
            case "Quarry":
                return new Quarry();
            case "WoodenCabin":
                return new WoodenCabin();
            case "ToolFactory":
                return new ToolFactory();
            case "CementPlant":
                return new CementPlant();
            case "SteelMill":
                return new SteelMill();
            case "LumberMill":
                return new LumberMill();
            case "ApartmentBuilding":
                return new ApartmentBuilding();
            default:
                throw new IllegalArgumentException("Unknown building type: " + buildingType);
        }
    }

    public void updateResourcesView() {
        StringBuilder resourcesInfo = new StringBuilder();
        for (Resource resource : resources) {
            resourcesInfo.append(resource.getName()).append(": ").append(resource.getQuantity()).append(", ");
        }
        // Remove the trailing comma and space
        if (!resourcesInfo.isEmpty()) {
            resourcesInfo.setLength(resourcesInfo.length() - 2);
        }
        view.updateResources(resourcesInfo.toString());
    }
}