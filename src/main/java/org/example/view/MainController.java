package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {
    private GameManager manager;
    private int selectedX = -1;
    private int selectedY = -1;
    private String selectedBuildingType = null;
    private int turnCounter = 0;

    @FXML
    private GridPane mapGrid;

    @FXML
    private TextArea logArea;

    @FXML
    private Text resourcesText;

    @FXML
    private Label turnCounterLabel;

    @FXML
    public void initialize() {
        this.manager = new GameManager(50, 50);
        appendLog("Welcome to RTS Game!");
        updateResourcesDisplay();
        initializeMap();
        mapGrid.setOnMouseClicked(this::handleMapClick);
        turnCounterLabel.setText("Turn: " + turnCounter);
    }

    @FXML
    private void handleAddBuilding() {
        Optional<String> buildingTypeOptional = showBuildingTypeSelectionDialog();
        if (buildingTypeOptional.isEmpty()) return;

        selectedBuildingType = buildingTypeOptional.get();
        appendLog("Selected building type: " + selectedBuildingType);
        appendLog("Please select a location on the map.");
    }

    private void handleMapClick(MouseEvent event) {
        if (selectedBuildingType == null) {
            appendLog("Please select a building type first.");
            return;
        }

        Node clickedNode = event.getPickResult().getIntersectedNode();

        if (clickedNode == null || clickedNode.getId() == null || !clickedNode.getId().startsWith("cell-")) {
            appendLog("Clicked outside valid cells.");
            return;
        }

        String[] parts = clickedNode.getId().split("-");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        selectedX = x;
        selectedY = y;

        appendLog("Selected coordinates: (" + x + ", " + y + ")");
        displayBuildingPreview(x, y);

        if (confirmSelection()) {
            int buildingChoice = getBuildingChoice(selectedBuildingType);
            if (manager.addBuilding(buildingChoice, selectedX, selectedY)) {
                appendLog("Building added at position (" + selectedX + ", " + selectedY + ").");
                initializeMap();
                updateResourcesDisplay();
            } else {
                appendLog("Failed to add building. Check position or resources.");
            }
            resetSelection();
        } else {
            initializeMap();
        }
    }

    private void displayBuildingPreview(int x, int y) {
        Building building = manager.createBuilding(getBuildingChoice(selectedBuildingType));
        if (building == null) return;

        int width = building.getSizeX();
        int height = building.getSizeY();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Text previewText = new Text(String.format("%3s", selectedBuildingType.charAt(0)));
                previewText.setStyle("-fx-fill: blue;");
                mapGrid.add(previewText, x + 1 + i, y + 1 + j);
            }
        }
    }

    private boolean confirmSelection() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Selection");
        alert.setHeaderText("Confirm Building Placement");
        alert.setContentText("Do you want to place the building at (" + selectedX + ", " + selectedY + ")?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void resetSelection() {
        selectedX = -1;
        selectedY = -1;
        selectedBuildingType = null;
    }

    private Optional<String> showBuildingTypeSelectionDialog() {
        List<String> buildingTypes = List.of(
                "House", "Farm", "Factory", "Warehouse", "Quarry", "WoodenCabin",
                "ToolFactory", "CementPlant", "SteelMill", "LumberMill", "ApartmentBuilding"
        );
        ChoiceDialog<String> dialog = new ChoiceDialog<>(buildingTypes.get(0), buildingTypes);
        dialog.setTitle("Select Building Type");
        dialog.setHeaderText("Choose a building type:");

        // Add a listener to update the content text with the required resources
        dialog.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String resources = getRequiredResources(newValue);
                dialog.setContentText("Required resources: " + resources);
            }
        });

        // Set initial content text
        dialog.setContentText("Required resources: " + getRequiredResources(buildingTypes.get(0)));

        // Set the size of the dialog
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setPrefWidth(400);  // Set the preferred width
        dialogPane.setPrefHeight(300); // Set the preferred height

        return dialog.showAndWait();
    }

    private String getRequiredResources(String buildingType) {
        Building building;
        switch (buildingType) {
            case "Farm": building = new Farm(); break;
            case "House": building = new House(); break;
            case "Quarry": building = new Quarry(); break;
            case "WoodenCabin": building = new WoodenCabin(); break;
            case "ToolFactory": building = new ToolFactory(); break;
            case "CementPlant": building = new CementPlant(); break;
            case "SteelMill": building = new SteelMill(); break;
            case "LumberMill": building = new LumberMill(); break;
            case "ApartmentBuilding": building = new ApartmentBuilding(); break;
            default: throw new IllegalArgumentException("Unknown building type: " + buildingType);
        }
        return building.getMaterials().toString();
    }

    private int getBuildingChoice(String buildingType) {
        switch (buildingType) {
            case "Farm": return 1;
            case "House": return 2;
            case "Quarry": return 3;
            case "WoodenCabin": return 4;
            case "ToolFactory": return 5;
            case "CementPlant": return 6;
            case "SteelMill": return 7;
            case "LumberMill": return 8;
            case "ApartmentBuilding": return 9;
            default: throw new IllegalArgumentException("Unknown building type: " + buildingType);
        }
    }

    @FXML
    private void handleAssignResident() {
        if (manager.getBuildings().isEmpty()) {
            appendLog("No buildings available to assign residents.");
            return;
        }

        appendLog("Please select a building on the map.");
        mapGrid.setOnMouseClicked(event -> {
            Node clickedNode = event.getPickResult().getIntersectedNode();
            if (clickedNode == null || clickedNode.getId() == null || !clickedNode.getId().startsWith("cell-")) {
                appendLog("Clicked outside valid cells.");
                return;
            }

            String[] parts = clickedNode.getId().split("-");
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);

            Building building = manager.getMap().getGrid()[x][y];
            if (building == null) {
                appendLog("No building at the selected location.");
                return;
            }

            Optional<Resident> residentOptional = showResidentSelectionDialog(manager.getResidents());
            if (residentOptional.isEmpty()) return;

            Resident resident = residentOptional.get();

            List<String> roles = new ArrayList<>();
            if (building.getMaxInhabitants() > 0) {
                roles.add("Inhabitant");
            }
            if (building.getMaxWorkers() > 0) {
                roles.add("Worker");
            }

            if (roles.isEmpty()) {
                appendLog("The selected building cannot have inhabitants or workers.");
                return;
            }

            ChoiceDialog<String> roleDialog = new ChoiceDialog<>(roles.get(0), roles);
            roleDialog.setTitle("Select Role");
            roleDialog.setHeaderText("Choose the role for the resident:");
            Optional<String> roleOptional = roleDialog.showAndWait();
            if (roleOptional.isEmpty()) return;

            String role = roleOptional.get();
            if (role.equals("Inhabitant")) {
                manager.assignInhabitantToBuilding(building, resident);
                appendLog(resident.getName() + " assigned as an inhabitant to " + building.getName());
            } else {
                manager.assignWorkerToBuilding(building, resident);
                appendLog(resident.getName() + " assigned as a worker to " + building.getName());
            }

            mapGrid.setOnMouseClicked(this::handleMapClick); // Reset the map click handler
        });
    }

    @FXML
    private void handleDisplayGameState() {
        appendLog("Please select a building on the map to view its state.");
        mapGrid.setOnMouseClicked(event -> {
            Node clickedNode = event.getPickResult().getIntersectedNode();
            if (clickedNode == null || clickedNode.getId() == null || !clickedNode.getId().startsWith("cell-")) {
                appendLog("Clicked outside valid cells.");
                return;
            }

            String[] parts = clickedNode.getId().split("-");
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);

            Building building = manager.getMap().getGrid()[x][y];
            if (building == null) {
                appendLog("No building at the selected location.");
                return;
            }

            displayBuildingState(building);
            mapGrid.setOnMouseClicked(this::handleMapClick); // Reset the map click handler
        });
    }

    private void displayBuildingState(Building building) {
        StringBuilder buildingState = new StringBuilder("Building State:\n\n");
        buildingState.append("Building: ").append(building.getName()).append("\n");
        buildingState.append("Size: ").append(building.getSizeX()).append("x").append(building.getSizeY()).append("\n");
        buildingState.append("Materials: ").append(building.getMaterials()).append("\n");
        buildingState.append("Time to build: ").append(building.getTimeToBuild()).append("\n");
        buildingState.append("Inhabitants: ").append(building.getInhabitants().size()).append("/")
                .append(building.getMaxInhabitants()).append("\n");
        buildingState.append("Workers: ").append(building.getWorkers().size()).append("/")
                .append(building.getMaxWorkers()).append("\n");

        building.getInhabitants().forEach(inhabitant ->
                buildingState.append(" - Inhabitant: ").append(inhabitant.getName()).append("\n")
        );

        building.getWorkers().forEach(worker ->
                buildingState.append(" - Worker: ").append(worker.getName()).append("\n")
        );

        appendLog(buildingState.toString());
    }

    @FXML
    private void handleNextTurn() {
        manager.consumeResources();
        manager.produceResources();
        manager.completeBuildings();
        initializeMap();
        updateResourcesDisplay();
        turnCounter++;
        turnCounterLabel.setText("Turn: " + turnCounter);
        appendLog("Turn progressed: resources consumed and produced. Current turn: " + turnCounter);
    }

    @FXML
    private void handleQuit() {
        System.exit(0);
    }

    private Optional<Building> showBuildingSelectionDialog(List<Building> buildings) {
        ChoiceDialog<Building> dialog = new ChoiceDialog<>(null, buildings);
        dialog.setTitle("Select Building");
        dialog.setHeaderText("Choose a building to assign a resident:");
        return dialog.showAndWait();
    }

    private Optional<Resident> showResidentSelectionDialog(List<Resident> residents) {
        ChoiceDialog<Resident> dialog = new ChoiceDialog<>(null, residents);
        dialog.setTitle("Select Resident");
        dialog.setHeaderText("Choose a resident to assign to a building:");
        return dialog.showAndWait();
    }

    private void updateResourcesDisplay() {
        StringBuilder resourceDisplay = new StringBuilder("Resources:\n");
        manager.getResources().forEach(resource ->
                resourceDisplay.append(resource.getName()).append(": ").append(resource.getQuantity()).append("\n")
        );
        resourcesText.setText(resourceDisplay.toString());
    }

    private void appendLog(String log) {
        logArea.appendText(log + "\n");
    }

    private void initializeMap() {
        mapGrid.getChildren().clear();

        // Ajouter les indices des colonnes en haut
        for (int x = 0; x < manager.getMap().getWidth(); x++) {
            Text colIndex = new Text(String.format("%3d", x));
            mapGrid.add(colIndex, x + 1, 0);
        }

        // Ajouter les indices des lignes sur la gauche
        for (int y = 0; y < manager.getMap().getHeight(); y++) {
            Text rowIndex = new Text(String.format("%3d", y));
            mapGrid.add(rowIndex, 0, y + 1);

            // Ajouter les bâtiments ou cellules vides
            for (int x = 0; x < manager.getMap().getWidth(); x++) {
                Building building = manager.getMap().getGrid()[x][y];
                String cell = (building == null) ? "." : building.getSymbol();

                Text cellText = new Text(String.format("%3s", cell));

                // Si un bâtiment est présent, appliquer la couleur correspondante
                if (building != null) {
                    cellText.setStyle("-fx-fill: " + getBuildingColor(building) + ";");
                }

                cellText.setId("cell-" + x + "-" + y); // Assigner un ID unique
                mapGrid.add(cellText, x + 1, y + 1);  // Placer dans la grille
            }
        }
    }

    private String getBuildingColor(Building building) {
        return building.isConstructed() ? "green" : "red";
    }
}