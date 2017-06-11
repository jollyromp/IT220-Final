package maze.game;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/4/2017
 * maze.game
 * Game.java
 * Final - The game class, runs the loop and manages state
 */

import maze.entity.Player;
import maze.io.ConsoleIO;
import maze.map.Map;

import java.util.concurrent.ThreadLocalRandom;

public class Game {
    private static final int MIN_MAP_SIZE = 5;
    private static final int MAX_MAP_SIZE = 10;
    private boolean running = true;
    private boolean quit = false;

    private Player player;
    private Map currentMap;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public Game() {
        init();
        run();
        save();
        System.exit(0);
    }

    private void init() {
        player = new Player(ConsoleIO.nextLine("What is your hero's name", ConsoleIO.NOT_EMPTY, s -> s), Player.ICON);
        currentMap = new Map(random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE), random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE));
        currentMap.setPlayer(player);
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
                        if (currentMap.movePlayer(Player.Move.DOWN))
                            doTimelyAction = false;
                        else
                            ConsoleIO.println("You can't go that way");
                        break;
                    case "up":
                        if (currentMap.movePlayer(Player.Move.UP))
                            doTimelyAction = false;
                        else
                            ConsoleIO.println("You can't go that way");
                        break;
                    case "left":
                        if (currentMap.movePlayer(Player.Move.LEFT))
                            doTimelyAction = false;
                        else
                            ConsoleIO.println("You can't go that way");
                        break;
                    case "right":
                        if (currentMap.movePlayer(Player.Move.RIGHT))
                            doTimelyAction = false;
                        else
                            ConsoleIO.println("You can't go that way");
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
                currentMap = new Map(random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE), random.nextInt(MIN_MAP_SIZE, MAX_MAP_SIZE));
                currentMap.setPlayer(player);
            }

            // Enemy actions

            if (random.nextInt(100) > 75){
                // random event
                ConsoleIO.println("===== Event =====", player.getName() + " trips on a spike.", "=================");
            }
        }
    }

    /**
     * Save the games state
     */
    private void save() {
        ConsoleIO.println("Saving...", "Done");
    }
}
