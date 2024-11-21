package org.example;

import org.example.controller.GameController;
import org.example.model.GameManager;
import org.example.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        // Initialisation du modèle, de la vue et du contrôleur
        GameManager manager = new GameManager();
        ConsoleView view = new ConsoleView();
        GameController controller = new GameController(manager, view);

        // Lancer le jeu
        controller.runGame();
    }
}
