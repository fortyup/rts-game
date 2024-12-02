package org.example.controller;

import org.example.model.*;
import org.example.view.ConsoleView;

import java.util.List;
import java.util.Optional;

public class GameController {
    private final GameManager manager;
    private final ConsoleView view;

    public GameController(GameManager manager, ConsoleView view) {
        this.manager = manager;
        this.view = view;
    }

    public void runGame() {
        view.displayWelcomeMessage();
        processGameLoop();
    }

    private void processGameLoop() {
        boolean running = true;
        while (running) {
            view.displayMenu();
            running = processUserChoice(view.getUserChoice());
        }
        view.displayGoodbyeMessage();
    }

    private boolean processUserChoice(int choice) {
        switch (choice) {
            case 1 -> handleAddBuilding();
            case 2 -> handleAssignToBuilding();
            case 3 -> displayGameState();
            case 4 -> displayResidents();
            case 5 -> displayBuildingResidents();
            case 6 -> simulateTurn();
            case 7 -> displayMap();
            case 8 -> {
                return false;
            }
            default -> view.displayInvalidChoiceMessage();
        }
        return true;
    }

    private void handleAddBuilding() {
        int buildingChoice = view.getBuildingChoice();
        if (buildingChoice == 10) return;

        Optional<BuildingPosition> position = getBuildingPosition();
        position.ifPresentOrElse(
                pos -> addBuildingAtPosition(buildingChoice, pos),
                () -> view.displayErrorMessage("Invalid building position")
        );
    }

    private Optional<BuildingPosition> getBuildingPosition() {
        int x = view.getBuildingPositionX();
        int y = view.getBuildingPositionY();
        return Optional.of(new BuildingPosition(x, y));
    }

    private void addBuildingAtPosition(int buildingChoice, BuildingPosition position) {
        if (manager.addBuilding(buildingChoice, position.x(), position.y())) {
            view.displaySuccessMessage("Building is under construction at (" + position.x() + ", " + position.y() + ").");
        } else {
            view.displayErrorMessage("Invalid position, position already occupied, or not enough resources.");
        }
    }

    private void handleAssignToBuilding() {
        // Si il n'y a pas de bâtiments, on affiche un message d'erreur et on retourne
        if (manager.getBuildings().isEmpty()) {
            view.displayErrorMessage("No buildings available.");
            return;
        }
        Optional<BuildingAssignment> assignment = getBuildingAssignment();
        assignment.ifPresentOrElse(
                this::performBuildingAssignment,
                () -> view.displayErrorMessage("Building assignment cancelled")
        );
    }

    private Optional<BuildingAssignment> getBuildingAssignment() {
        List<Building> buildings = manager.getBuildings();
        view.displayBuildings(buildings);

        int buildingChoice = view.selectBuilding(buildings);
        if (buildingChoice == buildings.size()) return Optional.empty();

        view.displayResidents(manager.getResidents());
        int residentChoice = view.selectResident();
        if (residentChoice == manager.getResidents().size()) return Optional.empty();

        int assignmentType = view.selectAssignmentType();

        return Optional.of(new BuildingAssignment(
                manager.getBuildings().get(buildingChoice),
                manager.getResidents().get(residentChoice),
                assignmentType
        ));
    }

    private void performBuildingAssignment(BuildingAssignment assignment) {
        try {
            switch (assignment.assignmentType()) {
                case 1 -> assignInhabitant(assignment);
                case 2 -> assignWorker(assignment);
                default -> view.displayErrorMessage("Invalid assignment type.");
            }
        } catch (IllegalStateException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void assignInhabitant(BuildingAssignment assignment) {
        manager.assignInhabitantToBuilding(assignment.building(), assignment.resident());
        assignment.resident().assignAsInhabitant();
        view.displaySuccessMessage(
                assignment.resident().getName() + " assigned as inhabitant to " +
                        assignment.building().getName()
        );
    }

    private void assignWorker(BuildingAssignment assignment) {
        manager.assignWorkerToBuilding(assignment.building(), assignment.resident());
        assignment.resident().assignAsWorker();
        view.displaySuccessMessage(
                assignment.resident().getName() + " assigned as worker to " +
                        assignment.building().getName()
        );
    }

    private void displayGameState() {
        view.displayGameState(manager.getBuildings(), manager.getResources());
    }

    private void displayResidents() {
        view.displayResidents(manager.getResidents());
    }

    private void displayBuildingResidents() {
        List<Building> buildings = manager.getBuildings();
        if (buildings.isEmpty()) {
            view.displayErrorMessage("No buildings available.");
            return;
        }

        view.displayBuildings(buildings);
        int buildingChoice = view.selectBuilding(buildings);

        if (buildingChoice >= 0 && buildingChoice < buildings.size()) {
            view.displayBuildingResidents(buildings.get(buildingChoice));
        } else {
            view.displayErrorMessage("Invalid building selection.");
        }
    }

    private void simulateTurn() {
        manager.consumeResources();
        manager.produceResources();
        manager.completeBuildings();
        view.displayResources(manager.getResources());
    }

    private void displayMap() {
        view.displayMap(manager.getMap());
    }

    // Helper record to encapsulate building assignment details
    private record BuildingAssignment(
            Building building,
            Resident resident,
            int assignmentType
    ) {}

    // Helper record to encapsulate building position
    private record BuildingPosition(int x, int y) {}
}