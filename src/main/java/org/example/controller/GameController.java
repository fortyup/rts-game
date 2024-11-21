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
                case 1 -> addBuilding();          // Ajouter un bâtiment
                case 2 -> view.displayGameState(manager.getBuildings(), manager.getResources());  // Afficher l'état du jeu
                case 3 -> simulateTurn();         // Passer au prochain tour
                case 4 -> {
                    running = false;
                    view.displayGoodbyeMessage();  // Quitter le jeu
                }
                default -> view.displayInvalidChoiceMessage(); // Choix invalide
            }
        }
    }

    // Méthode pour ajouter un bâtiment
    private void addBuilding() {
        System.out.println("\n--- Add Building ---");
        System.out.println("1. Add Farm (produces food)");
        System.out.println("2. Add House (consumes food)");
        System.out.print("Choose a building to add: ");

        int choice = view.getUserChoice();
        switch (choice) {
            case 1:
                addFarm();
                break;
            case 2:
                addHouse();
                break;
            default:
                view.displayErrorMessage("Invalid choice. No building added.");
                break;
        }
    }

    // Méthode pour ajouter une ferme
    private void addFarm() {
        if (manager.getWood().getQuantity() >= 10) {
            manager.addBuilding(new Farm());
            view.displaySuccessMessage("Farm added!");
        } else {
            view.displayErrorMessage("Not enough wood to add a Farm.");
        }
    }

    // Méthode pour ajouter une maison
    private void addHouse() {
        if (manager.getWood().getQuantity() >= 10) {
            manager.addBuilding(new House());
            view.displaySuccessMessage("House added!");
        } else {
            view.displayErrorMessage("Not enough wood to add a House.");
        }
    }

    // Méthode pour simuler un tour
    private void simulateTurn() {
        manager.simulateTurn();
        view.displayResources(manager.getResources()); // Afficher les ressources après un tour
    }
}
