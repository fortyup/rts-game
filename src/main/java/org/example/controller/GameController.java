package org.example.controller;

import org.example.model.Farm;
import org.example.model.GameManager;
import org.example.model.House;
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
                case 1 -> addFarm();
                case 2 -> addHouse();
                case 3 -> view.displayBuildings(manager.getBuildings());
                case 4 -> simulateTurn();
                case 5 -> {
                    running = false;
                    view.displayGoodbyeMessage();
                }
                default -> view.displayInvalidChoiceMessage();
            }
        }
    }

    private void addFarm() {
        if (manager.getWood().getQuantity() >= 10) {
            manager.addBuilding(new Farm());
            view.displaySuccessMessage("Farm added!");
        } else {
            view.displayErrorMessage("Not enough wood to add a Farm.");
        }
    }

    private void addHouse() {
        if (manager.getWood().getQuantity() >= 10) {
            manager.addBuilding(new House());
            view.displaySuccessMessage("House added!");
        } else {
            view.displayErrorMessage("Not enough wood to add a House.");
        }
    }

    private void simulateTurn() {
        manager.simulateTurn();
        view.displayResources(manager.getResources());
    }
}
