package org.example.model;

public class Map {
    private final int width;
    private final int height;
    private final Building[][] grid;

    // Constructeur
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Building[width][height];
    }

    // Vérifie si un bâtiment peut être placé à une position donnée
    public boolean canPlaceBuilding(Building building, int startX, int startY) {
        int sizeX = building.getSizeX();
        int sizeY = building.getSizeY();

        // Vérifie si le bâtiment dépasse les limites de la carte
        if (startX < 0 || startY < 0 || startX + sizeX > width || startY + sizeY > height) {
            return false;
        }

        // Vérifie si l'emplacement est déjà occupé
        for (int x = startX; x < startX + sizeX; x++) {
            for (int y = startY; y < startY + sizeY; y++) {
                if (grid[x][y] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Place un bâtiment sur la carte
    public boolean placeBuilding(Building building, int startX, int startY) {
        if (!canPlaceBuilding(building, startX, startY)) {
            return false;
        }

        int sizeX = building.getSizeX();
        int sizeY = building.getSizeY();

        // Place le bâtiment dans la grille
        for (int x = startX; x < startX + sizeX; x++) {
            for (int y = startY; y < startY + sizeY; y++) {
                grid[x][y] = building;
            }
        }
        return true;
    }

    // Affiche une représentation de la carte dans la console
    public void displayMap() {
        // Affiche la bordure supérieure avec les indices des colonnes
        System.out.print("    "); // Espace pour les indices des lignes
        for (int x = 0; x < width; x++) {
            System.out.printf("%3d", x);  // Affichage des numéros de colonnes
        }
        System.out.println();

        // Affiche la grille de la carte
        for (int y = 0; y < height; y++) {
            // Affiche l'indice de la ligne à gauche
            System.out.printf("%3d", y);
            for (int x = 0; x < width; x++) {
                // Utilise un formatage pour afficher un point ou un B dans une cellule de taille fixe
                Building building = grid[x][y];
                String cell;
                if (building == null) {
                    cell = ".";
                } else if (building.isUnderConstruction()) {
                    cell = " \u001B[33m" + building.getSymbol() + "\u001B[0m"; // Jaune pour en construction
                } else {
                    cell = " \u001B[32m" + building.getSymbol() + "\u001B[0m"; // Vert pour construit
                }
                System.out.printf("%3s", cell);  // Affichage avec largeur fixe de 3 caractères
            }
            System.out.println();  // Nouvelle ligne après chaque ligne de la carte
        }
    }

    // Retire un bâtiment de la carte
    public boolean removeBuilding(Building building) {
        boolean found = false;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] == building) {
                    grid[x][y] = null;
                    found = true;
                }
            }
        }
        return found;
    }

    // Retourne les dimensions de la carte
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Building[][] getGrid() {
        return grid;
    }
}
