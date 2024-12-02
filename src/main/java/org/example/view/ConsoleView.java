package org.example.view;

import org.example.model.*;

import java.util.Arrays;
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

    public void displayResidents(List<Resident> residents) {
        System.out.println("\n--- Residents ---");
        for (int i = 0; i < residents.size(); i++) {
            System.out.println((i + 1) + ". " + residents.get(i));
        }
    }

    public int selectResident() {
        System.out.print("Choose a resident (or cancel): ");
        return getUserChoice() - 1; // Adjust for 0-based indexing
    }

    public int selectBuilding(List<Building> buildings) {
        System.out.println("\n--- Buildings ---");
        for (int i = 0; i < buildings.size(); i++) {
            System.out.println((i + 1) + ". " + buildings.get(i).getName());
        }
        System.out.println((buildings.size() + 1) + ". Cancel");

        System.out.print("Choose a building (or cancel): ");
        int choice = getUserChoice();

        if (choice < 1 || choice > buildings.size() + 1) return -1;

        return choice - 1; // Adjust for 0-based indexing
    }

    public int selectAssignmentType() {
        System.out.println("\n--- Assignment Type ---");
        System.out.println("1. Assign as Inhabitant");
        System.out.println("2. Assign as Worker");
        System.out.println("3. Cancel");

        System.out.print("Choose assignment type: ");
        return getUserChoice();
    }

    public void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add Building");
        System.out.println("2. Assign Resident");
        System.out.println("3. View Game State");
        System.out.println("4. View Resident State");
        System.out.println("5. View Building Residents");
        System.out.println("6. Next Turn");
        System.out.println("7. View Map");
        System.out.println("8. Quit");
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
        System.out.println("1. Add Farm (Cost: " + Arrays.toString(new Farm().getMaterials()) + ")");
        System.out.println("2. Add House (Cost: " + Arrays.toString(new House().getMaterials()) + ")");
        System.out.println("3. Add Quarry (Cost: " + Arrays.toString(new Quarry().getMaterials()) + ")");
        System.out.println("4. Add WoodenCabin (Cost: " + Arrays.toString(new WoodenCabin().getMaterials()) + ")");
        System.out.println("5. Add ToolFactory (Cost: " + Arrays.toString(new ToolFactory().getMaterials()) + ")");
        System.out.println("6. Add CementPlant (Cost: " + Arrays.toString(new CementPlant().getMaterials()) + ")");
        System.out.println("7. Add SteelMill (Cost: " + Arrays.toString(new SteelMill().getMaterials()) + ")");
        System.out.println("8. Add LumberMill (Cost: " + Arrays.toString(new LumberMill().getMaterials()) + ")");
        System.out.println("9. Add ApartmentBuilding (Cost: " + Arrays.toString(new ApartmentBuilding().getMaterials()) + ")");
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

    public void displayBuildingResidents(Building building) {
        System.out.println("Building: " + building.getName());

        System.out.println("Inhabitants:");
        if (building.getInhabitants().isEmpty()) {
            System.out.println("No inhabitants assigned.");
        } else {
            for (Resident resident : building.getInhabitants()) {
                System.out.println("- " + resident);
            }
        }

        System.out.println("Workers:");
        if (building.getWorkers().isEmpty()) {
            System.out.println(building.getWorkers().size());
            System.out.println("No workers assigned.");
        } else {
            for (Resident resident : building.getWorkers()) {
                System.out.println(building.getWorkers().size());
                System.out.println("- " + resident);
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
