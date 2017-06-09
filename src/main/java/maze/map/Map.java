package maze.map;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.map
 * Map.java
 * IT220-Final - description
 */

import maze.entity.Exit;
import maze.entity.Player;

import java.util.Arrays;
import java.util.Random;

/**
 * Generates a maze and does rendering.
 * <p>
 * Maze generation algorithm is based of the Perfect Maze found here:
 * http://algs4.cs.princeton.edu/41graph/Maze.java.html,
 * http://algs4.cs.princeton.edu/41graph/
 * <p>
 * Modified to work with our output model and resizability.
 */
public class Map {

    private final String HIDDEN_TILE = "▒";
    private boolean[][] north, east, south, west, visited;

    private int width, height;
    // First square is height, second is width
    private Tile[][] map;
    private Random random = new Random();
    private Player player;
    private int playerX, playerY;

    private int longestPath, exitX, exitY;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        map = new Tile[height][width];
        generateTiles();
        playerX = 0;
        playerY = 0;
        map[playerY][playerX].setHidden(false);
    }

    public void setPlayer(Player player) {
        this.player = player;
        map[playerY][playerX].setMember(player);
    }

    public String miniMap() {
        StringBuilder drawnMap = new StringBuilder();
        // For each row of tile
        for (int h = playerY == 0 ? 0 : playerY - 1; h < (playerY == height - 1 ? height : playerY + 2); h++) {
            // for each row of the tile
            for (int row = 0; row < Tile.getTileHeight(); row++) {
                // for each column of the current row
                for (int w = playerX == 0 ? 0 : playerX - 1; w < (playerX == width - 1 ? width : playerX + 2); w++) {
                    // get the current tile
                    Tile tile = map[h][w];
                    // get the current layout
                    String layout = tile.getLayout()[row];
                    // If it's the center row and the tile has an entity on it, replace the center with the entity icon
                    if (tile.isHidden()) {
                        for (int i = 0; i < Tile.getTileWidth(); i++) {
                            drawnMap.append(HIDDEN_TILE);
                        }
                    } else if (row == Tile.getTileVerticalMid() && tile.getMember() != null) {
                        drawnMap.append(layout.substring(0, Tile.getTileHorizontalMid()))
                                .append(tile.getMember().getIcon())
                                .append(layout.substring(Tile.getTileHorizontalMid() + 1));
                    } else { // draw the layout normally
                        drawnMap.append(layout);
                    }
                }
                drawnMap.append('\n');
            }
        }
        return drawnMap.toString();
    }

    public boolean movePlayer(int x, int y) {
        boolean didMove = false;
        map[playerY][playerX].setMember(null);
        if (y == 1) { // Move down
            if (map[playerY][playerX].canMoveDown()) {
                playerY++;
                didMove = true;
            }
        } else if (y == -1) { // Move up
            if (map[playerY][playerX].canMoveUp()) {
                playerY--;
                didMove = true;
            }
        } else if (x == 1) { // Move right
            if (map[playerY][playerX].canMoveRight()) {
                playerX++;
                didMove = true;
            }
        } else if (x == -1) { // Move left
            if (map[playerY][playerX].canMoveLeft()) {
                playerX--;
                didMove = true;
            }
        }

        map[playerY][playerX].setMember(player);
        map[playerY][playerX].setHidden(false);

        return didMove;
    }

    /**
     * Draw the exposed map
     */
    public String toString() {
        StringBuilder drawnMap = new StringBuilder();
        // For each row of tile
        for (int h = 0; h < height; h++) {
            // for each row of the tile
            for (int row = 0; row < Tile.getTileHeight(); row++) {
                // for each column of the current row
                for (int w = 0; w < width; w++) {
                    // get the current tile
                    Tile tile = map[h][w];
                    // get the current layout
                    String layout = tile.getLayout()[row];
                    // If it's the center row and the tile has an entity on it, replace the center with the entity icon
                    if (false && tile.isHidden()) { //TODO(): Remove false after testing!!!
                        for (int i = 0; i < Tile.getTileWidth(); i++) {
                            drawnMap.append(HIDDEN_TILE);
                        }
                    } else if (row == Tile.getTileVerticalMid() && tile.getMember() != null) {
                        drawnMap.append(layout.substring(0, Tile.getTileHorizontalMid()))
                                .append(tile.getMember().getIcon())
                                .append(layout.substring(Tile.getTileHorizontalMid() + 1));
                    } else { // draw the layout normally
                        drawnMap.append(layout);
                    }
                }
                drawnMap.append('\n');
            }
        }
        return drawnMap.toString();
    }

    public boolean isOnExit(){
        return playerX == exitX && playerY == exitY;
    }

    /**
     * Generate a map given the provided dimensions
     */
    private void generateTiles() {
        // Setup
        visited = new boolean[height + 2][width + 2];
        Arrays.fill(visited[0], true);
        Arrays.fill(visited[height + 1], true);
        for (int h = 0; h < height + 2; h++) {
            visited[h][0] = true;
            visited[h][width + 1] = true;
        }

        north = new boolean[height + 2][width + 2];
        east = new boolean[height + 2][width + 2];
        south = new boolean[height + 2][width + 2];
        west = new boolean[height + 2][width + 2];
        for (int h = 0; h < height + 2; h++) {
            for (int w = 0; w < width + 2; w++) {
                north[h][w] = true;
                east[h][w] = true;
                south[h][w] = true;
                west[h][w] = true;
            }
        }

        // Generate the maze, recursively
        generate(1, 1, 0);

        // Build the tileset from the generated wall layouts,
        // starts at 1, 1, and ends 1 early due to padding in the generation
        for (int h = 1; h <= height; h++) {
            for (int w = 1; w <= width; w++) {
                map[h - 1][w - 1] = new Tile(new Tile.Corridor(!north[h][w], !south[h][w], !west[h][w], !east[h][w]));
            }
        }

        // Add exit member
        map[exitY][exitX].setMember(new Exit());
    }

    private void generate(int x, int y, int lengthFromStart) {
        visited[y][x] = true;
        if (lengthFromStart > longestPath){
            longestPath = lengthFromStart;
            exitX = x-1;
            exitY = y-1;
        }
        while (!visited[y][x + 1] || !visited[y + 1][x] || !visited[y][x - 1] || !visited[y - 1][x])
            while (true) {
                int r = random.nextInt(4);
                if (r == 0 && !visited[y - 1][x]) {
                    north[y][x] = south[y - 1][x] = false;
                    generate(x, y - 1, lengthFromStart+1);
                    break;
                } else if (r == 1 && !visited[y][x + 1]) {
                    east[y][x] = west[y][x + 1] = false;
                    generate(x + 1, y, lengthFromStart+1);
                    break;
                } else if (r == 2 && !visited[y + 1][x]) {
                    south[y][x] = north[y + 1][x] = false;
                    generate(x, y + 1, lengthFromStart+1);
                    break;
                } else if (r == 3 && !visited[y][x - 1]) {
                    west[y][x] = east[y][x - 1] = false;
                    generate(x - 1, y, lengthFromStart+1);
                    break;
                }
            }
    }
}
