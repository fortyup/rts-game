package org.example;

import org.example.controller.GameController;
import org.example.model.GameManager;
import org.example.view.ConsoleView;

public class MainConsole {
    public static void main(String[] args) {
        // Initialisation du modèle, de la vue et du contrôleur
        ConsoleView view = new ConsoleView();
        GameManager manager = new GameManager(50, 50);
        GameController controller = new GameController(manager, view);

        // Lancer le jeu
        controller.runGame();
    }
}
