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
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[x][y] == null) {
                    System.out.print(".");
                } else {
                    System.out.print("B");
                }
            }
            System.out.println();
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
}
