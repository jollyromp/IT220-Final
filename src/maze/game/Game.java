package maze.game;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/4/2017
 * maze.game
 * Game.java
 * Final - The game class, runs the loop and manages state
 */

import maze.entity.Enemy;
import maze.entity.Item;
import maze.entity.Living;
import maze.entity.Player;
import maze.io.ConsoleIO;
import maze.map.Map;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The game class with logic and looping
 */
public class Game {
    private static final int MIN_MAP_SIZE = 5;
    private static final int MAX_MAP_SIZE = 10;
    private boolean running = true;
    private boolean quit = false;
    private boolean lose = false;
    private int depth = 1;

    // Game objects
    private Player player;
    private List<Enemy> enemies;
    private Map currentMap;

    // Random implementation with pre made lower and higher bounds random
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    /**
     * The game object constructor
     */
    public Game() {
        init();
        run();
        System.exit(0);
    }

    /**
     * Create the initial player and map
     */
    private void init() {
        player = new Player(ConsoleIO.nextLine("What is your hero's name", ConsoleIO.NOT_EMPTY, s -> s));
        currentMap = new Map(random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE), random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE), depth);
        currentMap.setPlayer(player);
        enemies = currentMap.createMonsters();
    }

    /**
     * The main game loop. Everything should be loaded before hand as this is a looping method
     */
    private void run() {
        showHelp();
        while (running) {
            boolean doTimelyAction = true;
            // draw mini-map
            ConsoleIO.println(currentMap.miniMap());
            while (doTimelyAction) {
                switch (ConsoleIO.nextLine("What action do you want to take")) {
                    case "quit": // Quit, saves the game
                        quit = true;
                        break;
                    case "map": // Draw explored map
                        ConsoleIO.println(currentMap.map(true));
                        ConsoleIO.waitForEnter();
                        break;
                    case "debug map": // Draw full map
                        ConsoleIO.println(currentMap.map(false));
                        ConsoleIO.waitForEnter();
                        break;
                    case "down": // Move down
                        doTimelyAction = action(currentMap.movePlayer(Player.Move.DOWN));
                        break;
                    case "up": // Move up
                        doTimelyAction = action(currentMap.movePlayer(Player.Move.UP));
                        break;
                    case "left": // Move left
                        doTimelyAction = action(currentMap.movePlayer(Player.Move.LEFT));
                        break;
                    case "right": // Move right
                        doTimelyAction = action(currentMap.movePlayer(Player.Move.RIGHT));
                        break;
                    case "help": // Show help
                        showHelp();
                        break;
                    default: // invalid command
                        ConsoleIO.println("Please enter a valid command");
                }
                if (quit) { // quit out before other actions
                    running = false;
                    break;
                }
            }
            // Enemy movement
            enemies.forEach(enemy -> currentMap.moveEnemy(enemy, Enemy.Move.values()[random.nextInt(Enemy.Move.values().length)]));
        }
    }

    /**
     * @param result What happened when the player moved
     * @return false if the action should progress the game loop or request a new prompt
     */
    private boolean action(Living.MoveResult result) {
        switch (result) {
            case WALL: // The player tried to move into a wall
                ConsoleIO.println("You can't go that way.");
                break;
            case ENEMY: // The player fought and won against an enemy
                removeEnemy(player.getX(), player.getY()); // remove the enemy
                return false;
            case SUCCESS: // The movement was a success
                return false;
            case EXIT: // The player found the exit
                ConsoleIO.println(currentMap.miniMap(), "===== ===== =====", "Floor complete!", "===== ===== =====");
                currentMap = new Map(random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE), random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE), ++depth);
                currentMap.setPlayer(player);
                enemies = currentMap.createMonsters();
                return false;
            case LOSE: // The player lost their life
                running = false;
                lose = true;
                return false;
        }
        return true;
    }

    /**
     * @param x Grid x Position
     * @param y Grid y Position
     * @return true if the enemy was found and removed
     */
    private boolean removeEnemy(int x, int y) {
        Enemy toRemove = null;
        for (Enemy enemy : enemies) {
            if (enemy.getX() == x && enemy.getY() == y)
                toRemove = enemy;
        }
        return enemies.remove(toRemove);
    }

    /**
     * printout the command help lines
     */
    private void showHelp() {
        ConsoleIO.println(
                "===== ===== ===== Help ===== ===== =====",
                "Commands:",
                ">>> Movement <<<",
                "up - to move up if possible",
                "down - to move down if possible",
                "left - to move left if possible",
                "right - to move right if possible",
                "",
                ">>> System <<<",
                "quit - quit the game",
                "map - show the full map that has been explored",
                "help = show the help screen",
                "",
                "Map Key:",
                player.getIcon() + ": Player",
                currentMap.getExit().getIcon() + ": Exit",
                enemies.get(0).getIcon() + ": Enemy",
                "\uD83D\uDFA7: Item",
                ""
        );
    }
}
