package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.example.model.*;

import java.util.List;
import java.util.Optional;

public class MainController {
    private GameManager manager;
    private int selectedX = -1;
    private int selectedY = -1;
    private String selectedBuildingType = null;

    @FXML
    private GridPane mapGrid;

    @FXML
    private TextArea logArea;

    @FXML
    private Text resourcesText;

    @FXML
    public void initialize() {
        this.manager = new GameManager(50, 50);
        appendLog("Welcome to RTS Game!");
        updateResourcesDisplay();
        initializeMap();
        mapGrid.setOnMouseClicked(this::handleMapClick);
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
        return dialog.showAndWait();
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

        Optional<Building> buildingOptional = showBuildingSelectionDialog(manager.getBuildings());
        if (buildingOptional.isEmpty()) return;

        Building building = buildingOptional.get();

        Optional<Resident> residentOptional = showResidentSelectionDialog(manager.getResidents());
        if (residentOptional.isEmpty()) return;

        Resident resident = residentOptional.get();

        manager.assignInhabitantToBuilding(building, resident);
        appendLog(resident.getName() + " assigned to " + building.getName());
    }

    @FXML
    private void handleDisplayGameState() {
        StringBuilder gameState = new StringBuilder("Game State:\n\n");
        gameState.append("Resources:\n");
        manager.getResources().forEach(resource ->
                gameState.append(resource.getName()).append(": ").append(resource.getQuantity()).append("\n")
        );
        gameState.append("\nBuildings:\n");
        manager.getBuildings().forEach(building ->
                gameState.append(building.getName()).append(" at (")
                        .append(building.getSizeX()).append(", ").append(building.getSizeY()).append(")\n")
        );
        appendLog(gameState.toString());
    }

    @FXML
    private void handleNextTurn() {
        manager.consumeResources();
        manager.produceResources();
        manager.completeBuildings();
        initializeMap();
        updateResourcesDisplay();
        appendLog("Turn progressed: resources consumed and produced.");
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