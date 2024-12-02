package org.example.controller;

import org.example.model.*;
import org.example.view.ConsoleView;

import java.util.List;

public class GameController {
    private final GameManager manager;
    private final ConsoleView view;

    public GameController(GameManager manager, ConsoleView view) {
        this.manager = manager;
        this.view = view;
    }

    public void assignToBuilding() {
        // Display available buildings
        view.displayBuildings(manager.getBuildings());

        // Get building selection
        int buildingChoice = view.selectBuilding(manager.getBuildings());
        if (buildingChoice == manager.getBuildings().size()) return; // User cancelled

        // Display and get resident selection
        view.displayResidents(manager.getResidents());
        int residentChoice = view.selectResident();
        if (residentChoice == manager.getResidents().size()) return; // User cancelled

        // Get assignment type
        int assignmentType = view.selectAssignmentType();

        Resident selectedResident = manager.getResidents().get(residentChoice);
        Building selectedBuilding = manager.getBuildings().get(buildingChoice);

        try {
            switch (assignmentType) {
                case 1 -> { // Assign as Inhabitant
                    manager.assignInhabitantToBuilding(selectedBuilding, selectedResident);
                    view.displaySuccessMessage(selectedResident.getName() + " assigned as inhabitant to " + selectedBuilding.getName());
                }
                case 2 -> { // Assign as Worker
                    manager.assignWorkerToBuilding(selectedBuilding, selectedResident);
                    selectedResident.assignAsWorker();
                    view.displaySuccessMessage(selectedResident.getName() + " assigned as worker to " + selectedBuilding.getName());
                }
                default -> view.displayErrorMessage("Invalid assignment type.");
            }
        } catch (IllegalStateException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    public void viewBuildingResidents() {
        List<Building> buildings = manager.getBuildings();
        if (buildings.isEmpty()) {
            view.displayErrorMessage("No buildings available.");
            return;
        }

        view.displayBuildings(buildings);

        int buildingChoice = view.selectBuilding(buildings);
        if (buildingChoice == -1) return; // User cancelled

        if (buildingChoice < 0 || buildingChoice >= buildings.size()) {
            view.displayErrorMessage("Invalid building selection.");
            return;
        }

        Building selectedBuilding = buildings.get(buildingChoice);
        view.displayBuildingResidents(selectedBuilding);
    }

    public void runGame() {
        view.displayWelcomeMessage();

        boolean running = true;
        while (running) {
            view.displayMap(manager.getMap());
            view.displayMenu();
            int choice = view.getUserChoice();

            switch (choice) {
                case 1 -> addBuilding();
                case 2 -> view.displayGameState(manager.getBuildings(), manager.getResources());
                case 3 -> simulateTurn();
                case 4 -> assignToBuilding(); // New option
                case 5 -> view.displayResidents(manager.getResidents());
                case 6 -> viewBuildingResidents();
                case 7 -> {
                    running = false;
                    view.displayGoodbyeMessage();
                }
                default -> view.displayInvalidChoiceMessage();
            }
        }
    }

    private void addBuilding() {
        int choice = view.getBuildingChoice();
        Building building = switch (choice) {
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

        if (building != null) {
            int x = view.getBuildingPositionX();
            int y = view.getBuildingPositionY();
            if (manager.getMap().placeBuilding(building, x, y)) {
                if (manager.tryAddBuilding(building)) {
                    view.displaySuccessMessage(building.getName() + " is under construction at (" + x + ", " + y + ").");
                } else {
                    view.displayErrorMessage("Not enough resources to build " + building.getName() + ".");
                }
            } else {
                view.displayErrorMessage("Invalid position or position already occupied.");
            }
        } else {
            view.displayErrorMessage("Invalid choice. No building added.");
        }
    }

    private void simulateTurn() {
        view.displayUnderConstruction(manager.getBuildingsUnderConstruction());
        view.displayCompletedBuildings(manager.completeBuildings());
        view.displayBuildings(manager.getBuildings());
        view.displayResources(manager.getResources());

    }
}
