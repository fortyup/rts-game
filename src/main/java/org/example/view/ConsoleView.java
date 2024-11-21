package org.example.view;

import org.example.model.Building;
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

    public void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add Farm (produces food)");
        System.out.println("2. Add House (consumes food)");
        System.out.println("3. Display Buildings");
        System.out.println("4. Next Turn");
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

    public void displayBuildings(List<Building> buildings) {
        System.out.println("Buildings:");
        for (Building building : buildings) {
            System.out.println("- " + building.getName());
        }
    }

    public void displayResources(List<Resource> resources) {
        System.out.println("Resources:");
        for (Resource resource : resources) {
            System.out.println(resource);
        }
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
