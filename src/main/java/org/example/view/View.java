// src/main/java/org/example/view/View.java
package org.example.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.model.Building;
import org.example.model.Observer;

import java.util.List;

public class View implements Observer {

    private Button addBuildingButton;
    private Button addResidentButton;
    private Button addNextTurnButton;
    private Button addQuitButton;
    private GridPane gameGrid;
    private Label resourcesLabel;
    private Label turnLabel;
    private int selectedX;
    private int selectedY;

    public View(Stage stage) {
        stage.setTitle("RTS Game");

        // Game grid 50x50
        gameGrid = new GridPane();
        gameGrid.setPadding(new Insets(10, 10, 10, 10));
        gameGrid.setVgap(1);
        gameGrid.setHgap(1);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Label cell = new Label();
                cell.setMinSize(30, 30);
                cell.setMaxSize(30, 30);
                cell.setStyle("-fx-border-color: black;");
                final int x = i;
                final int y = j;
                cell.setOnMouseClicked(event -> handleCellClick(event, x, y));
                gameGrid.add(cell, i, j);
            }
        }

        // Label to display resources
        resourcesLabel = new Label();

        // Label to display the number of turns
        turnLabel = new Label("Nombre de tours: 0");

        // Button to add a ticket
        addBuildingButton = new Button("Ajouter bâtiment");

        // Button to process (or handle) a ticket
        addResidentButton = new Button("Ajouter résident");

        // Button to pass the turn
        addNextTurnButton = new Button("Passer le tour");

        // Button to quit the game
        addQuitButton = new Button("Quitter");


        // Horizontal box layout
        HBox actionButton = new HBox(10, addBuildingButton, addResidentButton, addNextTurnButton, addQuitButton);
        // Vertical box layout
        VBox layout = new VBox(10, resourcesLabel, turnLabel, gameGrid, actionButton);
        layout.setPadding(new Insets(10, 20, 10, 20));  // Top, Right, Bottom, Left

        Scene scene = new Scene(layout, 800, 800);

        stage.setScene(scene);
        stage.show();
    }

    private void handleCellClick(MouseEvent event, int x, int y) {
        selectedX = x;
        selectedY = y;
        showPopup("Cell clicked at: (" + x + ", " + y + ")");
    }

    // Set action for the "Ajouter bâtiment" button
    public void setOnAddBuildingAction(EventHandler<ActionEvent> event) {
        addBuildingButton.setOnAction(event);
    }

    // Set action for the "Traiter ticket" button
    public void setOnAddResidentAction(EventHandler<ActionEvent> event) {
        addResidentButton.setOnAction(event);
    }

    // Set action for the "Passer le tour" button
    public void setOnAddNextTurnAction(EventHandler<ActionEvent> event) {
        addNextTurnButton.setOnAction(event);
    }

    // Set action for the "Quitter" button
    public void setOnAddQuitAction(EventHandler<ActionEvent> event) {
        addQuitButton.setOnAction(event);
    }

    // Afficher un message dans une fenêtre popup
    public void showPopup(String message) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Modality to block interaction with main window
        popupStage.setTitle("Message");

        // Create the Label with the message
        Label messageLabel = new Label(message);

        // Create the "OK" Button to close the popup
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> popupStage.close()); // Close the popup when clicked

        // VBox layout for the popup
        VBox popupLayout = new VBox(10, messageLabel, okButton);
        popupLayout.setPadding(new Insets(20));

        Scene popupScene = new Scene(popupLayout, 300, 150);
        popupStage.setScene(popupScene);
        popupStage.showAndWait(); // Show the popup and wait for it to be closed
    }

    public void showBuildingSelectionPopup(EventHandler<ActionEvent> onConfirm) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Sélectionner un bâtiment");

        Label label = new Label("Choisissez un bâtiment à ajouter :");
        ChoiceBox<String> buildingChoiceBox = new ChoiceBox<>();
        buildingChoiceBox.getItems().addAll("Farm", "House", "Quarry", "WoodenCabin", "ToolFactory", "CementPlant", "SteelMill", "LumberMill", "ApartmentBuilding");

        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(event -> {
            onConfirm.handle(new ActionEvent(buildingChoiceBox.getValue(), null));
            popupStage.close();
        });

        VBox layout = new VBox(10, label, buildingChoiceBox, confirmButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 200);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public void showResidentSelectionPopup(EventHandler<ActionEvent> onConfirm) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Sélectionner un résident");

        Label label = new Label("Choisissez un résident à ajouter :");
        ChoiceBox<String> residentChoiceBox = new ChoiceBox<>();
        residentChoiceBox.getItems().addAll("Alice", "Bob", "Charlie", "David", "Eve");

        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(event -> {
            onConfirm.handle(new ActionEvent(residentChoiceBox.getValue(), null));
            popupStage.close();
        });

        VBox layout = new VBox(10, label, residentChoiceBox, confirmButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 200);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public void showResidentTypeSelectionPopup(EventHandler<ActionEvent> onConfirm) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Sélectionner le type de résident");

        Label label = new Label("Choisissez le type de résident :");
        ChoiceBox<String> residentTypeChoiceBox = new ChoiceBox<>();
        residentTypeChoiceBox.getItems().addAll("Worker", "Inhabitant");

        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(event -> {
            onConfirm.handle(new ActionEvent(residentTypeChoiceBox.getValue(), null));
            popupStage.close();
        });

        VBox layout = new VBox(10, label, residentTypeChoiceBox, confirmButton);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 200);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    public int getSelectedX() {
        return selectedX;
    }

    public int getSelectedY() {
        return selectedY;
    }

    // Mettre à jour l'affichage des ressources
    public void updateResources(String resources) {
        resourcesLabel.setText("Ressources disponibles:\n" + resources);
    }

    // Mettre à jour l'affichage du nombre de tours
    public void updateTurnCount(int turnCount) {
        turnLabel.setText("Nombre de tours: " + turnCount);
    }

    public void placeBuildingOnMap(Building building, int x, int y) {
        int sizeX = building.getSizeX();
        int sizeY = building.getSizeY();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                Label buildingLabel = new Label(building.getSymbol());
                buildingLabel.setMinSize(30, 30);
                buildingLabel.setMaxSize(30, 30);
                if (building.isUnderConstruction()) {
                    buildingLabel.setStyle("-fx-background-color: yellow; -fx-border-color: black;");
                } else {
                    buildingLabel.setStyle("-fx-background-color: lightgreen; -fx-border-color: black;");
                }
                gameGrid.add(buildingLabel, x + i, y + j);
            }
        }
    }

    public void updateBuildings(List<Building> buildings) {
        for (Building building : buildings) {
            int x = building.getX();
            int y = building.getY();
            gameGrid.getChildren().removeIf(node ->
                    GridPane.getColumnIndex(node) == x &&
                            GridPane.getRowIndex(node) == y
            );
            placeBuildingOnMap(building, x, y);
            System.out.println("Placing building " + building.getName() + " at (" + x + ", " + y + ").");
        }
    }

    @Override
    public void update(List<String> tickets) {
        // Update the view with the new list of tickets
    }
}