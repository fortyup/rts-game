package org.example.controller;

import org.example.model.Farm;
import org.example.model.GameManager;
import org.example.model.House;
import org.example.view.ConsoleView;
import org.example.model.Quarry;

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
        System.out.println("3. Add Quarry (produces stone, iron, coal)");
        System.out.print("Choose a building to add: ");

        int choice = view.getUserChoice();
        switch (choice) {
            case 1:
                addFarm();
                break;
            case 2:
                addHouse();
                break;
            case 3:
                addQuarry();
                break;
            default:
                view.displayErrorMessage("Invalid choice. No building added.");
                break;
        }
    }

    // Méthode pour ajouter une ferme
    private void addFarm() {
        if (manager.getWood().getQuantity() >= 5 & manager.getStone().getQuantity() >= 5) {
            manager.addBuilding(new Farm());
        } else {
            view.displayErrorMessage("Not enough resources to add a Farm. Required: 5 Wood, 5 Stone.");
        }
    }

    // Méthode pour ajouter une maison
    private void addHouse() {
        if (manager.getWood().getQuantity() >= 2 & manager.getStone().getQuantity() >= 2) {
            manager.addBuilding(new House());
        } else {
            view.displayErrorMessage("Not enough resources to add a House. Required: 2 Wood, 2 Stone.");
        }
    }

    // Méthode pour ajouter une carrière
    private void addQuarry() {
        if (manager.getWood().getQuantity() >= 50) {
            manager.addBuilding(new Quarry());
        } else {
            view.displayErrorMessage("Not enough resources to add a Quarry. Required: 50 Wood.");
        }
    }

    // Méthode pour simuler un tour
    private void simulateTurn() {
        manager.simulateTurn();
        view.displayResources(manager.getResources()); // Afficher les ressources après un tour
    }
}
