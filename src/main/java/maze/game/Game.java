package maze.game;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/4/2017
 * maze.game
 * Game.java
 * Final - The game class, runs the loop and manages state
 */

import maze.entity.Enemy;
import maze.entity.Living;
import maze.entity.Player;
import maze.io.ConsoleIO;
import maze.map.Map;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    private static final int MIN_MAP_SIZE = 5;
    private static final int MAX_MAP_SIZE = 10;
    private boolean running = true;
    private boolean quit = false;
    private int depth = 1;

    private Player player;
    private List<Enemy> enemies;
    private Map currentMap;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public Game() {
        init();
        run();
        save();
        System.exit(0);
    }

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
        while (running) {
            boolean doTimelyAction = true;
            // draw mini-map
            ConsoleIO.println(currentMap.miniMap());
            while (doTimelyAction) {
                switch (ConsoleIO.nextLine("What action do you want to take")) {
                    case "quit": // Quit, saves the game
                        quit = true;
                        break;
                    case "save": // Save the current game state
                        save();
                        break;
                    case "map": // Draw explored map
                        ConsoleIO.println(currentMap.map(true));
                        ConsoleIO.waitForEnter();
                        break;
                    case "debug map": // Draw full map
                        ConsoleIO.println(currentMap.map(false));
                        ConsoleIO.waitForEnter();
                        break;
                    case "down":
                        doTimelyAction = action(currentMap.movePlayer(Player.Move.DOWN));
                        break;
                    case "up":
                        doTimelyAction = action(currentMap.movePlayer(Player.Move.UP));
                        break;
                    case "left":
                        doTimelyAction = action(currentMap.movePlayer(Player.Move.LEFT)) ;
                        break;
                    case "right":
                        doTimelyAction = action(currentMap.movePlayer(Player.Move.RIGHT));
                        break;
                    case "help":
                        showHelp();
                        break;
                    default:
                        ConsoleIO.println("Please enter a valid command");
                }
                if (quit) { // quit out before other actions
                    running = false;
                    break;
                }
            }
            // Player checks
            if (currentMap.isOnExit()) {
                ConsoleIO.println(currentMap.miniMap(), "Floor complete!");
                currentMap = new Map(random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE), random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE), ++depth);
                currentMap.setPlayer(player);
                enemies = currentMap.createMonsters();
            }

            // Enemy actions
            enemies.forEach(enemy -> currentMap.moveEnemy(enemy, Enemy.Move.values()[random.nextInt(Enemy.Move.values().length)]));

//            if (random.nextInt(100) > 75) {
//                // random event
//                ConsoleIO.println("===== Event =====", player.getName() + " trips on a spike.", "=================");
//            }
        }
    }

    private boolean action(Living.MoveResult result){
        switch (result) {
            case WALL:
                ConsoleIO.println("You can't go that way.");
                break;
            case ENEMY:
                removeEnemy(player.getX(), player.getY());
                return false;
            case SUCCESS:
                return false;
            case EXIT:
                return false;
        }
        return true;
    }

    private boolean removeEnemy(int x, int y) {
        for (Enemy enemy : enemies) {
            if (enemy.getX() == x && enemy.getY() == y) {
                enemies.remove(enemy);
                return true;
            }
        }
        return false;
    }

    private void showHelp() {
        ConsoleIO.println(
                "===== ===== ===== Help ===== ===== =====",
                "Commands:",
                ">>> Movement <<<",
                "up - to move up if possible",
                "down - to move down if possible",
                "left - to move left if possible",
                "right - to move right if possible",
                ">>> System <<<",
                "quit - save and quits the game",
                "save - saves the game",
                "map - shows the full map, that has been explored",
                "");
    }

    /**
     * Save the games state
     */
    private void save() {
        ConsoleIO.println("Saving...", "Done");
    }
}
