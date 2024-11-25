package org.example.controller;

import org.example.model.*;
import org.example.view.ConsoleView;

public class GameController {
    private final GameManager manager;
    private final ConsoleView view;

    public GameController(GameManager manager, ConsoleView view) {
        this.manager = manager;
        this.view = view;
    }

    public void runGame() {
        view.displayWelcomeMessage();

        boolean running = true;
        while (running) {
            view.displayMenu();
            int choice = view.getUserChoice();

            switch (choice) {
                case 1 -> addBuilding();
                case 2 -> view.displayGameState(manager.getBuildings(), manager.getResources());
                case 3 -> simulateTurn();
                case 4 -> view.displayMap(manager.getMap());
                case 5 -> {
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
            if (manager.tryAddBuilding(building)) {
                view.displaySuccessMessage(building.getName() + " is under construction.");
            } else {
                view.displayErrorMessage("Not enough resources to build " + building.getName() + ".");
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
