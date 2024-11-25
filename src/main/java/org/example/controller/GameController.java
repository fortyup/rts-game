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

    // Méthode pour ajouter un bâtiment générique
    private void addBuilding(Building building) {
        boolean canBuild = true;
        for (Resource required : building.getMaterials()) {
            Resource available = manager.getResources().stream()
                    .filter(r -> r.getName().equals(required.getName()))
                    .findFirst()
                    .orElse(null);
            if (available == null || available.getQuantity() < required.getQuantity()) {
                canBuild = false;
                break;
            }
        }

        if (canBuild) {
            manager.addBuilding(building);
        } else {
            StringBuilder errorMessage = new StringBuilder("Not enough resources to add a " + building.getName() + ". Required: ");
            for (Resource required : building.getMaterials()) {
                errorMessage.append(required.getQuantity()).append(" ").append(required.getName()).append(", ");
            }
            view.displayErrorMessage(errorMessage.substring(0, errorMessage.length() - 2) + ".");
        }
    }

    // Méthode pour ajouter un bâtiment en fonction du choix de l'utilisateur
    private void addBuilding() {
        int choice = view.getBuildingChoice();
        switch (choice) {
            case 1:
                addBuilding(new Farm());
                break;
            case 2:
                addBuilding(new House());
                break;
            case 3:
                addBuilding(new Quarry());
                break;
            case 4:
                addBuilding(new WoodenCabin());
                break;
            case 5:
                addBuilding(new ToolFactory());
                break;
            case 6:
                addBuilding(new CementPlant());
                break;
            case 7:
                addBuilding(new SteelMill());
                break;
            case 8:
                addBuilding(new LumberMill());
                break;
            case 9:
                addBuilding(new ApartmentBuilding());
                break;
            default:
                view.displayErrorMessage("Invalid choice. No building added.");
                break;
        }
    }

    // Méthode pour simuler un tour
    private void simulateTurn() {
        manager.simulateTurn();
        view.displayResources(manager.getResources()); // Afficher les ressources après un tour
    }
}
