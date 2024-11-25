package org.example.view;

import org.example.model.Building;
import org.example.model.Farm;
import org.example.model.House;
import org.example.model.Quarry;
import org.example.model.Resource;


import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println("Welcome to the RTS Game!");
    }

    // Menu principal
    public void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add Building");
        System.out.println("2. View Game State");
        System.out.println("3. Next Turn");
        System.out.println("4. Quit");
        System.out.print("Enter your choice: ");
    }

    public int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();  // Consomme la mauvaise entrée
        }
        return scanner.nextInt();
    }

    // Ajout d'un bâtiment : Fermes, Maisons ou Carrières
    public void addBuilding(List<Building> buildings) {
        System.out.println("\n--- Add Building ---");
        System.out.println("1. Add Farm");
        System.out.println("2. Add House");
        System.out.println("3. Add Quarry");
        System.out.print("Choose a building to add: ");

        int choice = getUserChoice();
        switch (choice) {
            case 1:
                buildings.add(new Farm());
                break;
            case 2:
                buildings.add(new House());
                break;
            case 3:
                buildings.add(new Quarry());
            default:
                System.out.println("Invalid choice. No building added.");
                break;
        }
    }

    // Affiche l'état actuel du jeu : bâtiments et ressources
    public void displayGameState(List<Building> buildings, List<Resource> resources) {
        System.out.println("\n--- Game State ---");

        // Afficher les bâtiments
        if (buildings.isEmpty()) {
            System.out.println("No buildings have been constructed yet.");
        } else {
            System.out.println("Buildings:");
            for (Building building : buildings) {
                System.out.println("- " + building.getName());
            }
        }

        // Afficher les ressources
        System.out.println("\nResources:");
        if (resources.isEmpty()) {
            System.out.println("No resources available.");
        } else {
            for (Resource resource : resources) {
                System.out.println(resource);  // Utilise la méthode toString de la classe Resource
            }
        }
    }

    // Affiche les ressources
    public void displayResources(List<Resource> resources) {
        System.out.println("\nResources:");
        for (Resource resource : resources) {
            System.out.println(resource);
        }
    }

    // Affiche le message de succès
    public void displaySuccessMessage(String message) {
        System.out.println("SUCCESS: " + message);
    }

    // Affiche le message d'erreur
    public void displayErrorMessage(String message) {
        System.out.println("ERROR: " + message);
    }

    // Affiche un message d'option invalide
    public void displayInvalidChoiceMessage() {
        System.out.println("Invalid choice. Please try again.");
    }

    // Affiche le message de fin du jeu
    public void displayGoodbyeMessage() {
        System.out.println("Thanks for playing! Goodbye!");
    }

    // Affiche un message concernant la production ou la consommation de ressources
    public void displayResourceChange(String resourceName, int amount, boolean isProduced) {
        if (isProduced) {
            System.out.println(resourceName + " produced: " + amount + " units.");
        } else {
            System.out.println(resourceName + " consumed: " + amount + " units.");
        }
    }

    // Affiche un message en cas de manque de ressources
    public void displayInsufficientResources(String resourceName, int needed) {
        System.out.println("Not enough " + resourceName + "! You need " + needed + " more units.");
    }

    // Affiche un message lors de l'ajout de ressources
    public void displayResourceAdded(String resourceName, int amount) {
        System.out.println(amount + " units of " + resourceName + " added.");
    }
}
