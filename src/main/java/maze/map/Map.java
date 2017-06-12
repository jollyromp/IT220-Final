package maze.map;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.map
 * Map.java
 * IT220-Final - description
 */

import maze.entity.Enemy;
import maze.entity.Exit;
import maze.entity.Living;
import maze.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private final char HIDDEN_TILE = '▒';

    private boolean[][] north, east, south, west, visited;
    private int width, height, depth, longestPath, exitX, exitY;
    private Tile[][] map;
    private Random random = new Random();
    private Player player;

    public Map(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;

        map = new Tile[height][width];
        generateTiles();
        map[0][0].setHidden(false);
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.player.setX(0);
        this.player.setY(0);
        map[0][0].setMember(player);
    }

    public String miniMap() {
        StringBuilder drawnMap = new StringBuilder();
        // For each row of tile around the player
        for (int h = player.getY() == 0 ? 0 : player.getY() - 1; h < (player.getY() == height - 1 ? height : player.getY() + 2); h++) {
            // for each row of the tile
            for (int row = 0; row < Tile.miniHeight; row++) {
                // for each column of the current row
                for (int w = player.getX() == 0 ? 0 : player.getX() - 1; w < (player.getX() == width - 1 ? width : player.getX() + 2); w++) {
                    // get the current tile
                    Tile tile = map[h][w];
                    // get the current layout
                    String layout = tile.getMiniLayout()[row];
                    // If it's the center row and the tile has an entity on it, replace the center with the entity icon
                    if (tile.isHidden()) {
                        for (int i = 0; i < Tile.miniWidth; i++) {
                            drawnMap.append(HIDDEN_TILE);
                        }
                    } else if (row == Tile.miniVerticalMid && tile.getMember() != null) {
                        drawnMap.append(layout.substring(0, Tile.miniHorizontalMid))
                                .append(tile.getMember().getIcon())
                                .append(layout.substring(Tile.miniHorizontalMid + 1));
                    } else { // draw the layout normally
                        drawnMap.append(layout);
                    }
                }
                drawnMap.append('\n');
            }
        }
        return drawnMap.toString();
    }

    public Living.MoveResult movePlayer(Player.Move move) {
        Living.MoveResult result = Living.MoveResult.WALL;
        map[player.getY()][player.getX()].setMember(null);
        if (move.equals(Player.Move.DOWN) && map[player.getY()][player.getX()].canMoveDown()) { // Move down
            if (map[player.getY() + 1][player.getX()].hasMember())
                result = Living.MoveResult.ENEMY;
            else {
                player.setY(player.getY() + 1);
                result = Living.MoveResult.SUCCESS;
            }
        } else if (move.equals(Player.Move.UP) && map[player.getY()][player.getX()].canMoveUp()) { // Move up
            if (map[player.getY() - 1][player.getX()].hasMember())
                result = Living.MoveResult.ENEMY;
            else {
                player.setY(player.getY() - 1);
                result = Living.MoveResult.SUCCESS;
            }
        } else if (move.equals(Player.Move.RIGHT) && map[player.getY()][player.getX()].canMoveRight()) { // Move right
            if (map[player.getY()][player.getX() + 1].hasMember())
                result = Living.MoveResult.ENEMY;
            else {
                player.setX(player.getX() + 1);
                result = Living.MoveResult.SUCCESS;
            }
        } else if (move.equals(Player.Move.LEFT) && map[player.getY()][player.getX()].canMoveLeft()) { // Move left
            if (map[player.getY()][player.getX() - 1].hasMember())
                result = Living.MoveResult.ENEMY;
            else {
                player.setX(player.getX() - 1);
                result = Living.MoveResult.SUCCESS;
            }
        }

        map[player.getY()][player.getX()].setMember(player);
        map[player.getY()][player.getX()].setHidden(false);

        return result;
    }

    public Living.MoveResult moveEnemy(Enemy enemy, Enemy.Move move) {
        Living.MoveResult result = Living.MoveResult.WALL;
        map[enemy.getY()][enemy.getX()].setMember(null);
        if (move.equals(Enemy.Move.DOWN) && map[enemy.getY()][enemy.getX()].canMoveDown() && !map[enemy.getY() + 1][enemy.getX()].hasMember()) { // Move down
            enemy.setY(enemy.getY() + 1);
            result = Living.MoveResult.SUCCESS;
        } else if (move.equals(Enemy.Move.UP) && map[enemy.getY()][enemy.getX()].canMoveUp() && !map[enemy.getY() - 1][enemy.getX()].hasMember()) { // Move up
            enemy.setY(enemy.getY() - 1);
            result = Living.MoveResult.SUCCESS;
        } else if (move.equals(Enemy.Move.RIGHT) && map[enemy.getY()][enemy.getX()].canMoveRight() && !map[enemy.getY()][enemy.getX() + 1].hasMember()) { // Move right
            enemy.setX(enemy.getX() + 1);
            result = Living.MoveResult.SUCCESS;
        } else if (move.equals(Enemy.Move.LEFT) && map[enemy.getY()][enemy.getX()].canMoveLeft() && !map[enemy.getY()][enemy.getX() - 1].hasMember()) { // Move left
            enemy.setX(enemy.getX() - 1);
            result = Living.MoveResult.SUCCESS;
        }

        map[enemy.getY()][enemy.getX()].setMember(enemy);

        return result;
    }

    /**
     * Draw the full map
     */
    public String map(boolean drawHidden) {
        StringBuilder drawnMap = new StringBuilder();
        // For each row of tile
        for (int h = 0; h < height; h++) {
            // for each row of the tile
            for (int row = 0; row < Tile.tileHeight; row++) {
                // for each column of the current row
                for (int w = 0; w < width; w++) {
                    // get the current tile
                    Tile tile = map[h][w];
                    // get the current layout
                    String layout = tile.getLayout()[row];
                    // If it's the center row and the tile has an entity on it, replace the center with the entity icon
                    if (drawHidden && tile.isHidden()) {
                        for (int i = 0; i < Tile.tileWidth; i++) {
                            drawnMap.append(HIDDEN_TILE);
                        }
                    } else if (row == Tile.tileVerticalMid && tile.getMember() != null) {
                        drawnMap.append(layout.substring(0, Tile.tileHorizontalMid))
                                .append(tile.getMember().getIcon())
                                .append(layout.substring(Tile.tileHorizontalMid + 1));
                    } else { // draw the layout normally
                        drawnMap.append(layout);
                    }
                }
                drawnMap.append('\n');
            }
        }
        return drawnMap.toString();
    }

    public boolean isOnExit() {
        return player.getX() == exitX && player.getY() == exitY;
    }

    public List<Enemy> createMonsters() {
        List<Enemy> enemies = new ArrayList<>();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if ((h > 1 && w > 1) && random.nextInt(100) > 65 && (h != exitY && w != exitX)) {
                    Enemy enemy = new Enemy("NEEDS A NAME", Enemy.getRandomIcon(), depth, 10 * depth, 2 * depth);
                    enemy.setX(w);
                    enemy.setY(h);
                    enemies.add(enemy);
                    map[h][w].setMember(enemy);
                }
            }
        }

        return enemies;
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
                map[h - 1][w - 1] = new Tile(!north[h][w], !south[h][w], !west[h][w], !east[h][w]);
            }
        }

        // Add exit member
        map[exitY][exitX].setMember(new Exit());
    }

    private void generate(int x, int y, int lengthFromStart) {
        visited[y][x] = true;
        if (lengthFromStart > longestPath) {
            longestPath = lengthFromStart;
            exitX = x - 1;
            exitY = y - 1;
        }
        while (!visited[y][x + 1] || !visited[y + 1][x] || !visited[y][x - 1] || !visited[y - 1][x])
            while (true) {
                int r = random.nextInt(4);
                if (r == 0 && !visited[y - 1][x]) {
                    north[y][x] = south[y - 1][x] = false;
                    generate(x, y - 1, lengthFromStart + 1);
                    break;
                } else if (r == 1 && !visited[y][x + 1]) {
                    east[y][x] = west[y][x + 1] = false;
                    generate(x + 1, y, lengthFromStart + 1);
                    break;
                } else if (r == 2 && !visited[y + 1][x]) {
                    south[y][x] = north[y + 1][x] = false;
                    generate(x, y + 1, lengthFromStart + 1);
                    break;
                } else if (r == 3 && !visited[y][x - 1]) {
                    west[y][x] = east[y][x - 1] = false;
                    generate(x - 1, y, lengthFromStart + 1);
                    break;
                }
            }
    }
}
