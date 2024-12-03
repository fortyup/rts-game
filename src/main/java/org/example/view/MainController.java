package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.example.model.*;

public class MainController {
    private GameManager manager;

    @FXML
    private GridPane mapGrid;

    @FXML
    private TextArea logArea;

    @FXML
    public void initialize() {
        this.manager = new GameManager(50, 50);
        appendLog("Welcome to RTS Game!");
        initializeMap();
    }

    private void initializeMap() {
        mapGrid.getChildren().clear();
        for (int x = 0; x < manager.getMap().getWidth(); x++) {
            Text colIndex = new Text(String.format("%3d", x));
            mapGrid.add(colIndex, x + 1, 0);
        }
        for (int y = 0; y < manager.getMap().getHeight(); y++) {
            Text rowIndex = new Text(String.format("%3d", y));
            mapGrid.add(rowIndex, 0, y + 1);
            for (int x = 0; x < manager.getMap().getWidth(); x++) {
                Building building = manager.getMap().getGrid()[x][y];
                String cell = (building == null) ? "." : building.getSymbol();
                Text cellText = new Text(String.format("%3s", cell));
                mapGrid.add(cellText, x + 1, y + 1);
            }
        }
    }

    @FXML
    private void handleAddBuilding() {
        appendLog("Add Building button clicked.");
        showAlert("Feature not yet implemented", "Adding buildings is not implemented in this demo.");
    }

    @FXML
    private void handleAssignResident() {
        appendLog("Assign Resident button clicked.");
        showAlert("Feature not yet implemented", "Assigning residents is not implemented in this demo.");
    }

    @FXML
    private void handleDisplayGameState() {
        appendLog("Display Game State button clicked.");
        showAlert("Feature not yet implemented", "Displaying game state is not implemented in this demo.");
    }

    @FXML
    private void handleNextTurn() {
        appendLog("Next Turn clicked. Resources consumed.");
        manager.consumeResources();
        manager.produceResources();
    }

    @FXML
    private void handleQuit() {
        appendLog("Game exited.");
        System.exit(0);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void appendLog(String log) {
        logArea.appendText(log + "\n");
    }
}
