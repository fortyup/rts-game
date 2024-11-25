package org.example.view;

import org.example.model.*;

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

    public void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add Building");
        System.out.println("2. View Game State");
        System.out.println("3. Next Turn");
        System.out.println("4. View Map");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    public int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public int getBuildingChoice() {
        System.out.println("\n--- Add Building ---");
        System.out.println("1. Add Farm");
        System.out.println("2. Add House");
        System.out.println("3. Add Quarry");
        System.out.println("4. Add WoodenCabin");
        System.out.println("5. Add ToolFactory");
        System.out.println("6. Add CementPlant");
        System.out.println("7. Add SteelMill");
        System.out.println("8. Add LumberMill");
        System.out.println("9. Add ApartmentBuilding");
        System.out.println("10. Cancel");
        System.out.print("Choose a building to add: ");
        return getUserChoice();
    }

    public void displayGameState(List<Building> buildings, List<Resource> resources) {
        System.out.println("\n--- Game State ---");

        System.out.println("Buildings:");
        if (buildings.isEmpty()) {
            System.out.println("No buildings have been constructed yet.");
        } else {
            for (Building building : buildings) {
                System.out.println("- " + building.getName());
            }
        }

        System.out.println("\nResources:");
        if (resources.isEmpty()) {
            System.out.println("No resources available.");
        } else {
            for (Resource resource : resources) {
                System.out.println(resource);
            }
        }
    }

    public void displayResources(List<Resource> resources) {
        System.out.println("\nResources:");
        for (Resource resource : resources) {
            System.out.println(resource);
        }
    }

    public void displayUnderConstruction(List<Building> underConstruction) {
        System.out.println("\nBuildings under construction:");
        if (underConstruction.isEmpty()) {
            System.out.println("No buildings are currently under construction.");
        } else {
            for (Building building : underConstruction) {
                System.out.println("- " + building.getName() + " (Turns left: " + building.getTimeToBuild() + ")");
            }
        }
    }

    public void displayCompletedBuildings(List<Building> completedBuildings) {
        System.out.println("\nCompleted buildings:");
        if (completedBuildings.isEmpty()) {
            System.out.println("No buildings were completed this turn.");
        } else {
            for (Building building : completedBuildings) {
                System.out.println("- " + building.getName());
            }
        }
    }

    public void displayBuildings(List<Building> buildings) {
        System.out.println("\nBuildings:");
        if (buildings.isEmpty()) {
            System.out.println("No buildings have been constructed yet.");
        } else {
            for (Building building : buildings) {
                System.out.println("- " + building.getName());
            }
        }
    }

    public int getBuildingPositionX() {
        System.out.print("Enter the X position of the building: ");
        return getUserChoice();
    }

    public int getBuildingPositionY() {
        System.out.print("Enter the Y position of the building: ");
        return getUserChoice();
    }

    public void displayMap(Map map) {
        map.displayMap();
    }

    public void displaySuccessMessage(String message) {
        System.out.println("SUCCESS: " + message);
    }

    public void displayErrorMessage(String message) {
        System.out.println("ERROR: " + message);
    }

    public void displayInvalidChoiceMessage() {
        System.out.println("Invalid choice. Please try again.");
    }

    public void displayGoodbyeMessage() {
        System.out.println("Thanks for playing! Goodbye!");
    }
}
