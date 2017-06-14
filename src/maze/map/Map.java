package maze.map;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.map
 * Map.java
 * IT220-Final - The map object
 */

import maze.Driver;
import maze.combat.CombatEvent;
import maze.entity.*;
import maze.entity.abilities.Ability;
import maze.io.ConsoleIO;

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

    public static final int ENEMY_ABILITY_LIMIT = 6;
    public static final int ENEMY_HEALTH_LIMIT = 10;
    public static final int ENEMY_DAMAGE_LIMIT = 4;
    public static final int ITEM_AMOUNT = 6;
    public static final String RUNE_NAME = "Rune";
    public static final String RUNE_DESCRIPTION = "This small stone contains an ability";

    private final char HIDDEN_TILE = '▒';

    private boolean[][] north, east, south, west, visited;
    private int width, height, depth, longestPath, exitX, exitY, startX, startY;
    private Exit exit = new Exit();
    private Tile[][] map;
    private Random random = new Random();
    private Player player;
    private boolean win;

    /**
     * @param width map width
     * @param height map height
     * @param depth current depth, for enemy scaling
     */
    public Map(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;

        startX = random.nextInt(width - 1);
        startY = random.nextInt(height - 1);

        map = new Tile[height][width];
        generateTiles();

        // Add items to the map
        for (int i = 0; i < ITEM_AMOUNT; i++) {
            int randX = random.nextInt(width);
            int randY = random.nextInt(height);
            if (map[randY][randX].getMember() == null) {
                map[randY][randX].setMember(new Item(RUNE_NAME, RUNE_DESCRIPTION));
            }
        }
    }

    /**
     * @param player add the player to the map, used to keep the player persistent
     */
    public void setPlayer(Player player) {
        this.player = player;
        this.player.setX(startX);
        this.player.setY(startY);
        map[startY][startX].setMember(player);
        updateHidden();
    }

    /**
     * @return the built minimap string to be printed out
     */
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

    /**
     * @param move Direction to move the player
     * @return what did the player encounter
     */
    public Living.MoveResult movePlayer(Player.Move move) {
        Living.MoveResult result = Living.MoveResult.WALL;
        map[player.getY()][player.getX()].setMember(null);
        if (move.equals(Player.Move.DOWN) && map[player.getY()][player.getX()].canMoveDown()) { // Move down
            result = moveCheck(player.getX(), player.getY() + 1);
        } else if (move.equals(Player.Move.UP) && map[player.getY()][player.getX()].canMoveUp()) { // Move up
            result = moveCheck(player.getX(), player.getY() - 1);
        } else if (move.equals(Player.Move.RIGHT) && map[player.getY()][player.getX()].canMoveRight()) { // Move right
            result = moveCheck(player.getX() + 1, player.getY());
        } else if (move.equals(Player.Move.LEFT) && map[player.getY()][player.getX()].canMoveLeft()) { // Move left
            result = moveCheck(player.getX() - 1, player.getY());
        }

        map[player.getY()][player.getX()].setMember(player);
        updateHidden();

        return result;
    }

    /**
     * @param x change in X
     * @param y change in Y
     * @return what did the player encounter
     */
    private Living.MoveResult moveCheck(int x, int y) {
        Living.MoveResult result = Living.MoveResult.WALL;
        if (map[y][x].hasMember()) {
            if (map[y][x].getMember() instanceof Exit) {
                result = Living.MoveResult.EXIT;
            } else if (map[y][x].getMember() instanceof Enemy) {
                ConsoleIO.println("You have encountered " + map[y][x].getMember().getName() + "!");
                CombatEvent combat = new CombatEvent(player, (Enemy) map[y][x].getMember());
                if (combat.combatLoop()) {
                    player.setX(x);
                    player.setY(y);
                    ConsoleIO.println("You have defeated: " + map[y][x].getMember().getName());
                    result = Living.MoveResult.ENEMY;
                } else {
                    result = Living.MoveResult.LOSE;
                }
            } else if (map[y][x].getMember() instanceof Item) {
                pickupItem((Item) map[y][x].getMember());
                player.levelUp();
                map[y][x].setMember(null);
                player.setX(x);
                player.setY(y);
                result = Living.MoveResult.SUCCESS;
            }
        } else {
            player.setX(x);
            player.setY(y);
            result = Living.MoveResult.SUCCESS;
        }
        return result;
    }

    /**
     * @param item Item that was found
     */
    private void pickupItem(Item item) {
        ConsoleIO.println("You found a " + item.getName(),
                item.getDescription());
        Ability newAbility = Driver.getAbilities().get(random.nextInt(Driver.getAbilities().size()));
        if (player.hasAbility(newAbility.getName())) {
            ConsoleIO.println("You already had the ability " + newAbility.getName());
        } else {
            ConsoleIO.println("You obtained the ability " + newAbility.getName());
        }
        player.addAbility(newAbility);
    }

    /**
     * Reveals tiles that the player has visited and can see
     */
    private void updateHidden() {
        Tile currentTile = map[player.getY()][player.getX()];
        currentTile.setHidden(false);
        if (currentTile.canMoveUp()) {
            map[player.getY() - 1][player.getX()].setHidden(false);
        }
        if (currentTile.canMoveDown()) {
            map[player.getY() + 1][player.getX()].setHidden(false);
        }
        if (currentTile.canMoveLeft()) {
            map[player.getY()][player.getX() - 1].setHidden(false);
        }
        if (currentTile.canMoveRight()) {
            map[player.getY()][player.getX() + 1].setHidden(false);
        }
    }

    /**
     * @param enemy Enemy to move
     * @param move Where they want to moved to
     * @return What did they encounter
     */
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

        if (enemy.getHealth() > 0) {
            map[enemy.getY()][enemy.getX()].setMember(enemy);
        }

        return result;
    }

    /**
     * Draw the full map
     * @param drawHidden if false, draw all tiles
     * @return the built string
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

    /**
     * @return Create the monsters randomly on the map
     */
    public List<Enemy> createMonsters() {
        List<Enemy> enemies = new ArrayList<>();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (!(h < startY + 1 && h > startY - 1 && w < startX + 1 && w > startX - 1) && random.nextInt(100) > 80 && (h != exitY && w != exitX)) {
                    Enemy enemy = new Enemy(Driver.getEnemyNames().get(random.nextInt(Driver.getEnemyNames().size())),
                            depth,
                            random.nextInt(ENEMY_HEALTH_LIMIT) + 1 + depth,
                            random.nextInt(ENEMY_DAMAGE_LIMIT) + 1 + depth);
                    enemy.setX(w);
                    enemy.setY(h);
                    for (int i = 0; i < random.nextInt(ENEMY_ABILITY_LIMIT); i++) {
                        enemy.addAbility(Driver.getAbilities().get(random.nextInt(Driver.getAbilities().size())));
                    }
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
        generate(startX + 1, startY + 1, 0);

        // Build the tileset from the generated wall layouts,
        // starts at 1, 1, and ends 1 early due to padding in the generation
        for (int h = 1; h <= height; h++) {
            for (int w = 1; w <= width; w++) {
                map[h - 1][w - 1] = new Tile(!north[h][w], !south[h][w], !west[h][w], !east[h][w]);
            }
        }

        // Add exit member
        map[exitY][exitX].setMember(exit);
    }

    /**
     * @param x current X
     * @param y current Y
     * @param lengthFromStart depth of the generation
     */
    private void generate(int x, int y, int lengthFromStart) {
        visited[y][x] = true;
        if (lengthFromStart > longestPath) {
            longestPath = lengthFromStart;
            exitX = x - 1;
            exitY = y - 1;
        }

        // if the current tile has a non visited neighbor
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

    /**
     * @return Get the exit object
     */
    public Exit getExit() {
        return exit;
    }
}
