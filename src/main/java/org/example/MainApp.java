package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controller.Controller;
import org.example.model.Model;
import org.example.view.View;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Model model = new Model();
        View view = new View(primaryStage);
        new Controller(view, model); // Relie la vue et le modèle via le contrôleur
    }

    public static void main(String[] args) {
        launch(args);
    }
}
