package org.example.view;

import org.example.model.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ConsoleView {
    private static final String ERROR_PREFIX = "ERROR: ";
    private static final String SUCCESS_PREFIX = "SUCCESS: ";
    private static final String CANCEL_OPTION = "Cancel";
    private static final String INVALID_INPUT = "Invalid input. Please enter a number.";
    private static final String MENU_FORMAT = "%d. %s%n";

    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcomeMessage() {
        System.out.println("Welcome to the RTS Game!");
    }

    public void displayResidents(List<Resident> residents) {
        if (residents == null || residents.isEmpty()) {
            System.out.println("No residents available.");
            return;
        }

        System.out.println("\n--- Residents ---");
        IntStream.range(0, residents.size())
                .forEach(i -> System.out.printf(MENU_FORMAT, i + 1, residents.get(i)));
    }

    public int selectResident() {
        System.out.print("Choose a resident (or cancel): ");
        return getUserChoice() - 1;
    }

    public int selectBuilding(List<Building> buildings) {
        if (buildings == null || buildings.isEmpty()) {
            System.out.println("No buildings available.");
            return -1;
        }

        System.out.println("\n--- Buildings ---");
        IntStream.range(0, buildings.size())
                .forEach(i -> System.out.printf(MENU_FORMAT, i + 1, buildings.get(i).getName()));
        System.out.printf(MENU_FORMAT, buildings.size() + 1, CANCEL_OPTION);

        System.out.print("Choose a building (or cancel): ");
        int choice = getUserChoice();
        return (choice < 1 || choice > buildings.size() + 1) ? -1 : choice - 1;
    }

    public int selectAssignmentType() {
        System.out.println("\n--- Assignment Type ---");
        System.out.printf(MENU_FORMAT, 1, "Assign as Inhabitant");
        System.out.printf(MENU_FORMAT, 2, "Assign as Worker");
        System.out.printf(MENU_FORMAT, 3, CANCEL_OPTION);

        System.out.print("Choose assignment type: ");
        return getUserChoice();
    }

    public void displayMenu() {
        System.out.println("\n--- Menu ---");
        String[] menuOptions = {
                "Add Building", "Assign Resident", "View Game State",
                "View Resident State", "View Building Residents",
                "Next Turn", "View Map", "Quit"
        };
        IntStream.range(0, menuOptions.length)
                .forEach(i -> System.out.printf(MENU_FORMAT, i + 1, menuOptions[i]));
        System.out.print("Enter your choice: ");
    }

    public int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println(INVALID_INPUT);
            scanner.next();
        }
        return scanner.nextInt();
    }

    public int getBuildingChoice() {
        System.out.println("\n--- Add Building ---");
        Building[] buildingTypes = {
                new Farm(), new House(), new Quarry(), new WoodenCabin(),
                new ToolFactory(), new CementPlant(), new SteelMill(),
                new LumberMill(), new ApartmentBuilding()
        };

        IntStream.range(0, buildingTypes.length)
                .forEach(i -> System.out.printf(MENU_FORMAT,
                        i + 1,
                        buildingTypes[i].getClass().getSimpleName() + " - " + buildingTypes[i].getMaterials().toString()
                ));

        System.out.printf(MENU_FORMAT, buildingTypes.length + 1, CANCEL_OPTION);
        System.out.print("Choose a building to add: ");
        return getUserChoice();
    }

    public void displayGameState(List<Building> buildings, List<Resource> resources) {
        System.out.println("\n--- Game State ---");

        System.out.println("Buildings:");
        displayListOrDefault(buildings, "No buildings have been constructed yet.",
                building -> System.out.println("- " + building.getName()));

        System.out.println("\nResources:");
        displayListOrDefault(resources, "No resources available.",
                System.out::println);
    }

    private <T> void displayListOrDefault(List<T> list, String defaultMessage,
                                          java.util.function.Consumer<T> displayAction) {
        if (list == null || list.isEmpty()) {
            System.out.println(defaultMessage);
        } else {
            list.forEach(displayAction);
        }
    }

    public void displayResources(List<Resource> resources) {
        System.out.println("\nResources:");
        resources.forEach(System.out::println);
    }

    public void displayUnderConstruction(List<Building> underConstruction) {
        System.out.println("\nBuildings under construction:");
        displayListOrDefault(underConstruction,
                "No buildings are currently under construction.",
                building -> System.out.printf("- %s (Turns left: %d)%n",
                        building.getName(), building.getTimeToBuild())
        );
    }

    public void displayCompletedBuildings(List<Building> completedBuildings) {
        System.out.println("\nCompleted buildings:");
        displayListOrDefault(completedBuildings,
                "No buildings were completed this turn.",
                building -> System.out.println("- " + building.getName())
        );
    }

    public void displayBuildingResidents(Building building) {
        System.out.println("Building: " + building.getName());

        System.out.println("Inhabitants:");
        displayListOrDefault(building.getInhabitants(),
                "No inhabitants assigned.",
                resident -> System.out.println("- " + resident)
        );

        System.out.println("Workers:");
        displayListOrDefault(building.getWorkers(),
                "No workers assigned.",
                resident -> System.out.println("- " + resident)
        );
    }

    public void displayBuildings(List<Building> buildings) {
        System.out.println("\nBuildings:");
        displayListOrDefault(buildings,
                "No buildings have been constructed yet.",
                building -> System.out.println("- " + building.getName())
        );
    }

    public void displayBuildingDetails(Building building) {
        System.out.println(building.toString());
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
        System.out.println(SUCCESS_PREFIX + message);
    }

    public void displayErrorMessage(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public void displayInvalidChoiceMessage() {
        System.out.println("Invalid choice. Please try again.");
    }

    public void displayGoodbyeMessage() {
        System.out.println("Thanks for playing! Goodbye!");
    }
}